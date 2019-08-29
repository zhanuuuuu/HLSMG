package com.hlyf.smg.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.config.DlbPayConnfig;
import com.hlyf.smg.config.SMGConnfig;
import com.hlyf.smg.config.SMGUrlConfig;
import com.hlyf.smg.dao.SMGDao.*;
import com.hlyf.smg.domin.*;
import com.hlyf.smg.domin.WxEntity.TemplateOrder;
import com.hlyf.smg.domin.WxEntity.WxMssVo;
import com.hlyf.smg.domin.payentity.*;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.exception.ErrorEnum;
import com.hlyf.smg.result.ResultMsg;
import com.hlyf.smg.service.SmgService;
import com.hlyf.smg.tool.RequestFacotry;
import com.hlyf.smg.tool.SHA1;
import com.hlyf.smg.tool.String_Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.hlyf.smg.Singleton.MyTocken.getSingletonTocken;
import static com.hlyf.smg.result.ResultMsg.*;
import static com.hlyf.smg.tool.String_Tool.getTimeUnix;

/**
 * Created by Administrator on 2019-08-15.
 */
@Service
public class SmgServiceImpl implements SmgService ,SMGUrlConfig {

    private static final Logger log= LoggerFactory.getLogger(SmgServiceImpl.class);

    @Autowired
    private PosMainMapper posMain;

    @Autowired
    private SMGConnfig smgConnfig;

    @Autowired
    private SMGGoodsInfoMapper smgGoodsInfoMapper;

    @Autowired
    private CommMapper commMapper;

    @Autowired
    private DlbPayConnfig dlbPayConnfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PayMaper payMaper;

    @Autowired
    private SMGMoneySaleLogMapper smgMoneySaleLogMapper;


    @Autowired
    private SMGGoodsInfoLogMapper smgGoodsInfoLogMapper;

    @Autowired
    private SMGCommonProblemsMapper smgCommonProblemsMapper;

    @Autowired
    private SMGUserMapper userMapper;
    @Override
    public List<cStoreGoods> GetcStoreGoodsS(String cStoreNo, List<String> barcodeList) throws ApiSysException {

        List<cStoreGoods> list=null;
        try {
            if(!this.smgConnfig.getIsdandian()){
                //单店的走这里
                list=this.posMain.GetcStoreGoods(cStoreNo, barcodeList);
            }else {
                //连锁的走这里
                list=this.posMain.GetcStoreGoodsDanDian(cStoreNo, barcodeList);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName()+"获取本地商品数据出错了 {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001001);
        }
        return list;
    }
    @Override
    public void SaveGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException{
        try{
            //TODO 得到单号
            OrderComm orderComm=CommonServiceImpl.getMerchantOrderIdTrue(request, smgGoodsInfoMapper);
            //得到商品信息
            cStoreGoods storeGoods=cStoreGoodsList.get(0);
            Double NomalPrice=storeGoods.getFNormalPrice();

            SMGGoodsInfo smgGoodsInfo=null;
            log.info("查询到的商品信息  {}", JSON.toJSON(storeGoods).toString());
            //如果是生鲜 直接保存到购物车
            if(frushGood.isWeight()){
                //如果是13码  计算出来重量
                if(request.getBarcode().length()==13){
                    if(NomalPrice==0){
                        log.error("查询商品的时间出错了: 查询出来的单价是 0");
                        throw  new ApiSysException(ErrorEnum.SSCO001002);
                    }
                    double wight=frushGood.getAllMoney()/NomalPrice;
                    wight=new Double(String_Tool.String_IS_Two(wight)) ;
                    //重新赋值
                    frushGood.setWeightwight(wight);
                }
                //TODO 是否计算18位码的显示单价
                if(smgConnfig.getCalprice() && request.getBarcode().length()==18){
                    NomalPrice=frushGood.getAllMoney()/frushGood.getWeightwight();
                }
                //TODO 如果是称重商品   直接插入数据库
                //        SMGGoodsInfo(String openId, String unionId, String storeId,
//                String merchantOrderId, String payOrderId, String cGoodsNo,
//                String cGoodsName, Double amount, Double discountAmount,
//                Double basePrice, Double price,
//                Integer qty, Double dweight,
//                Boolean isWeight, String barcode,
//                String unit, String receivingCode)
                  smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getUnionId(),request.getStoreId(),orderComm.getMerchantOrderId(),
                          orderComm.getMerchantOrderId(),storeGoods.getCGoodsNo(),storeGoods.getCGoodsName(),frushGood.getAllMoney(),0.0,
                        NomalPrice,NomalPrice,
                        0,frushGood.getWeightwight(),true,frushGood.getBarcode(),"kg",frushGood.getReceivingCode(),orderComm.getShowTime());
                //直接插入
                smgGoodsInfoMapper.insert(smgGoodsInfo);
            }else {
                //TODO 判断是否是跳过称码直接输入的称重条码
                if(storeGoods.getBWeight()){
                    throw  new ApiSysException(ErrorEnum.SSCO010004);
                }
                //检测购物车是否存在改商品
                 smgGoodsInfo =smgGoodsInfoMapper
                        .selectByOpendIdAndOrderStatusAndBarcode(request.getOpenId(),frushGood.getBarcode());
                //如果没有改商品保存的购物车   如果存在就更新该购物车
                 if(smgGoodsInfo!=null){
                   int num=smgGoodsInfo.getQty()+1;
                   Double allMoney=num*NomalPrice;
                     smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getUnionId(),request.getStoreId(),orderComm.getMerchantOrderId(),
                             orderComm.getMerchantOrderId(),storeGoods.getCGoodsNo(),storeGoods.getCGoodsName(),allMoney,0.0,
                             NomalPrice,NomalPrice,
                             num,0.0,false,frushGood.getBarcode(),storeGoods.getCUnit(),frushGood.getReceivingCode(),orderComm.getShowTime());
                     smgGoodsInfoMapper.updateByBarcodeAndopenIdAndOrderStatus(smgGoodsInfo);
                }else {
                     smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getUnionId(),request.getStoreId(),orderComm.getMerchantOrderId(),
                             orderComm.getMerchantOrderId(),storeGoods.getCGoodsNo(),storeGoods.getCGoodsName(),NomalPrice,0.0,
                             NomalPrice,NomalPrice,
                             1,0.0,false,frushGood.getBarcode(),storeGoods.getCUnit(),frushGood.getReceivingCode(),orderComm.getShowTime());
                     smgGoodsInfoMapper.insert(smgGoodsInfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询商品的时间出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }

    @Override
    public void UpdateGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException{
        try{
            //TODO 得到单号
            String merchantOrderId=CommonServiceImpl.getMerchantOrderId(request, smgGoodsInfoMapper);
            //得到商品信息
            cStoreGoods storeGoods=cStoreGoodsList.get(0);
            Double NomalPrice=storeGoods.getFNormalPrice();
            SMGGoodsInfo smgGoodsInfo=null;
            log.info("查询到的商品信息UpdateGoodsToCartInfo  {}", JSON.toJSON(storeGoods).toString());
            //如果没有改商品保存的购物车   如果存在就更新该购物车
            if(request.getNum()!=null && request.getNum()!=0){
                int num=request.getNum();
                Double allMoney=num*NomalPrice;
                smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getUnionId(),request.getStoreId(),merchantOrderId,
                        merchantOrderId,storeGoods.getCGoodsNo(),storeGoods.getCGoodsName(),allMoney,0.0,
                        NomalPrice,NomalPrice,
                        num,0.0,false,frushGood.getBarcode(),storeGoods.getCUnit(),frushGood.getReceivingCode());
                smgGoodsInfoMapper.updateByBarcodeAndopenIdAndOrderStatus(smgGoodsInfo);
            }else {
                //如果上传0 则删除
                smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getUnionId(),request.getStoreId(),merchantOrderId,
                        merchantOrderId,storeGoods.getCGoodsNo(),storeGoods.getCGoodsName(),20.0,0.0,
                        NomalPrice,NomalPrice,
                        1,0.0,false,frushGood.getBarcode(),storeGoods.getCUnit(),frushGood.getReceivingCode());
                smgGoodsInfoMapper.deleteSMGGoodsInfoBybarcodeAndopenId(smgGoodsInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改商品的时间出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }

    @Override
    public void DeleteGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException{
        try{
            smgGoodsInfoMapper.deleteByPrimaryKey(request.getGoodlineId().longValue());
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除商品的时间出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }



    @Override
    public void ClearGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException{
        try{
            SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getUnionId());
            smgGoodsInfoMapper.deleteSMGGoodsInfo(smgGoodsInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("清除购物车的时间出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }
    @Override
    public String SelectCart (Request request,String code,SMGGoodsInfoMapper smgGoodsInfoMapper) {
        try {
            return CommonServiceImpl.SelectCartInfo(request,RequestFacotry.getErrorEnumByCode(code),smgGoodsInfoMapper);
        } catch (ApiSysException e1) {
            e1.printStackTrace();
            log.error("系统出错了 严重 {} ",e1.getExceptionEnum().toString());
            return ResultMsgSeriousError();
        }
    }

    @Override
    public  String CommUrlFun(String urlType,JSONObject jsonParam){
        String response="";
        log.info(urlType+" "+" 获取的参数: {}",jsonParam.toJSONString());
        Request request=null;
        try{
                request= RequestFacotry.decryptCipherJsonToRequest("",jsonParam,ErrorEnum.SSCO001001);
            if(request==null){
                log.info("解析出来的 数据 {}","解析出来的失败了");

            }else {
                log.info("解析出来的 数据 {}",JSONObject.toJSONString(request, SerializerFeature.WriteMapNullValue));
            }
        }catch (Exception e){
            e.printStackTrace();

        }

        switch (urlType){
            case selectMemberInfo://会员查询

                break;
            case modifyFormId:   //保存向程序客户端的formId 方便后期推送
                try {
                    this.userMapper.updateUnionIdByOpenId(
                            new SMGUser(request.getOpenId(),request.getUnionId(),null)
                                    .setFormId(request.getFormId()));
                    response=ResultMsgSuccess("");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("保存向程序客户端的formId出现问题 ",e.getMessage());
                    response=ResultMsgSeriousError();
                }
                break;
            case getCommonProblems:
                try {
                    List<SMGCommonProblems> SMGCommonProblems= smgCommonProblemsMapper.selectAll();
                    if(SMGCommonProblems!=null && SMGCommonProblems.size()>0){
                        response=ResultMsgSure(SMGCommonProblems);
                    }else {
                        response=ResultMsgEmpty();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("查询插件问题解决出现问题 ",e.getMessage());
                    response=ResultMsgSeriousError();
                }
                break;
            case selectGoods:
                try {
                    FrushGood frushGood=CommonServiceImpl.getIsFrushGood(request, this.posMain);
                    List<String> list=new ArrayList<>();
                    list.add(frushGood.getBarcode());
                    List<cStoreGoods> storeGoodsList=this.GetcStoreGoodsS(request.getStoreId(),list);
                    RequestFacotry.GoodListIsEmpty(storeGoodsList);
                    log.info("获取出来的商品是 {}",JSONObject.toJSON(storeGoodsList).toString());
                    this.SaveGoodsToCartInfo(
                            request,
                            storeGoodsList,frushGood);
                    response=CommonServiceImpl.SelectCartInfo(request,ErrorEnum.SUCCESS,smgGoodsInfoMapper);
                } catch (ApiSysException e) {
                    e.printStackTrace();
                    log.error("获取商品出错了 ",e.getExceptionEnum().toString());
                    log.error("获取商品出错了 ",e.getMessage());
                    response=this.SelectCart(request,e.getExceptionEnum().getCode(),this.smgGoodsInfoMapper);
                }
                break;
            case updateGoods:
                try {
                    FrushGood frushGood=CommonServiceImpl.getIsFrushGood(request, this.posMain);
                    List<String> list=new ArrayList<>();
                    list.add(frushGood.getBarcode());
                    List<cStoreGoods> storeGoodsList=this.GetcStoreGoodsS(request.getStoreId(),list);
                    RequestFacotry.GoodListIsEmpty(storeGoodsList);
                    log.info("获取出来的商品是 {}",JSONObject.toJSON(storeGoodsList).toString());
                    this.UpdateGoodsToCartInfo(
                            request,
                            storeGoodsList,frushGood);
                    response=CommonServiceImpl.SelectCartInfo(request,ErrorEnum.SUCCESS,smgGoodsInfoMapper);
                } catch (ApiSysException e) {
                    e.printStackTrace();
                    log.error("更改商品出错了 ",e.getExceptionEnum().toString());
                    log.error("更改商品出错了 ",e.getMessage());
                    response=this.SelectCart(request,e.getExceptionEnum().getCode(),this.smgGoodsInfoMapper);
                }
                break;
            case deleteGoods:
                try {
                    this.DeleteGoodsToCartInfo(
                            request,
                            null,null);
                    response=CommonServiceImpl.SelectCartInfo(request,ErrorEnum.SUCCESS,smgGoodsInfoMapper);
                } catch (ApiSysException e) {
                    e.printStackTrace();
                    log.error("删除商品出错了 ",e.getExceptionEnum().toString());
                    log.error("删除商品出错了 ",e.getMessage());
                    response=this.SelectCart(request,e.getExceptionEnum().getCode(),this.smgGoodsInfoMapper);
                }
                break;
            case clearCartInfo:
                try {
                    this.ClearGoodsToCartInfo(
                            request,
                            null,null);
                    response=CommonServiceImpl.SelectCartInfo(request,ErrorEnum.SUCCESS,smgGoodsInfoMapper);
                } catch (ApiSysException e) {
                    e.printStackTrace();
                    log.error("删除商品出错了 ",e.getExceptionEnum().toString());
                    log.error("删除商品出错了 ",e.getMessage());
                    response=this.SelectCart(request,e.getExceptionEnum().getCode(),this.smgGoodsInfoMapper);
                }
                break;
            case commitCartInfo:
                try {
                    this.commitCartInfo(request,ErrorEnum.SUCCESS);
                    response=CommonServiceImpl.SelectCartInfo(request,ErrorEnum.SUCCESS,smgGoodsInfoMapper);
                } catch (ApiSysException e) {
                    e.printStackTrace();
                    log.error("提交购物车出错了 ",e.getExceptionEnum().toString());
                    log.error("提交购物车出错了 ",e.getMessage());
                    response=this.SelectCart(request,e.getExceptionEnum().getCode(),this.smgGoodsInfoMapper);
                }
                break;
                //查询消费历史记录
            case selectOrders:
                try {
                    List<SaleLogs> smgGoodsInfo= smgGoodsInfoLogMapper.p_getSmgOrdersLog(request);
                    if(smgGoodsInfo!=null && smgGoodsInfo.size()>0){
                        response=ResultMsgSure(smgGoodsInfo);
                    }else {
                        response=ResultMsgEmpty();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("查询消费记录出现问题 ",e.getMessage());
                    response=ResultMsgSeriousError();
                }
                break;
            case selectOrdersDetail:
                try {
                    response=CommonServiceImpl.SelectCartInfoDeatil(request,ErrorEnum.SUCCESS,smgGoodsInfoMapper);
                } catch (ApiSysException e) {
                    e.printStackTrace();
                    log.error("查询订详情出错了 ",e.getExceptionEnum().toString());
                    log.error("查询订详情出错了 ",e.getMessage());
                    response=ResultMsgSeriousError();
                }
                break;
            case payOrder:   //支付下单接口
                try{
                    //TODO 第一步 判断前段上传的单号是否已经支付过了
//                     public SMGGoodsInfo(String openId, String merchantOrderId,
//                            String payOrderId, Integer orderType,Integer orderStatus, Double actualAmount)
                    SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),request.getMerchantOrderId(),
                            null,null,0,null);
                    List<SMGGoodsInfo> list=this.smgGoodsInfoMapper.getSMGGoodsInfoBySMGGoodsInfo(smgGoodsInfo);
                    if(list==null || list.size()==0){
                        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO006002.getCode(),
                                ErrorEnum.SSCO006002.getMesssage(),(String) ""));
                    }
                    //TODO 第二步 请求支付接口生成支付订单
                    SMGPayConfig dlpPayConfigEntity=this.payMaper.selectByPrimaryKey(request.getStoreId());
                    if(dlpPayConfigEntity==null){
                        log.error("dlpPayConfigEntity {}","查询出来的dlb支付配置为空");
                        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO006003.getCode(),
                                ErrorEnum.SSCO006003.getMesssage(),(String) ""));
                    }
                    //重新赋值
                    dlbPayConnfig.setAccesskey(dlpPayConfigEntity.getAccesskey());
                    dlbPayConnfig.setSecretkey(dlpPayConfigEntity.getSecretkey());
                    dlbPayConnfig.setAgentnum(dlpPayConfigEntity.getAgentnum());
                    dlbPayConnfig.setCustomernum(dlpPayConfigEntity.getCustomernum());
                    dlbPayConnfig.setMachinenum(dlpPayConfigEntity.getMachinenum());
                    dlbPayConnfig.setShopnum(dlpPayConfigEntity.getShopnum());
                    String timeUnix=getTimeUnix();
                    String requestNum="";
                    try {
                        requestNum= RequestFacotry.getOrderId(request.getPosId(),request.getUserlineId());
                        log.info("获取到的支付单号是 {}  openId是 {} ",requestNum,request.getOpenId());
                    } catch (ApiSysException e) {
                        e.printStackTrace();
                    }

                    JSONObject extraInfo=new JSONObject();
                    extraInfo.put("payOrderId",requestNum);
                    extraInfo.put("opendId",request.getOpenId());
                    extraInfo.put("merchantOrderId",request.getMerchantOrderId());
                    OrderPay orderPay=new OrderPay(dlbPayConnfig.getAgentnum(),dlbPayConnfig.getCustomernum(),
                            dlbPayConnfig.getShopnum(),
                            requestNum,smgConnfig.getIstest()==true ? smgConnfig.getTestmoney():request.getAmount(),"WX",request.getOpenId(),
                            smgConnfig.getCallbackurl(),JSON.toJSONString(extraInfo),null);
                    String body= JSONObject.toJSONString(orderPay);
                    log.info("我是请求体携带的数据 {}",body);
                    String url = "https://openapi.duolabao.com/v1/agent/order/pay/create";
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("accessKey",dlbPayConnfig.getAccesskey());
                    headers.set("timestamp",timeUnix);
                    String sign="secretKey="+dlbPayConnfig.getSecretkey()+"&timestamp="+timeUnix +
                            "&path=/v1/agent/order/pay/create&body="+body;
                    log.info("带签名的字符串 {}",sign);
                    sign= SHA1.encode(sign);
                    log.info("签名后的字符串 {}",sign);
                    headers.set("token",sign.toUpperCase());
                    HttpEntity<String> entity = new HttpEntity<String>(body, headers);
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
                    String result = responseEntity.getBody();
                    log.info("我是拿到的返回结果 {}",result);
                    //TODO 第三步 返回支付相关信息进行支付
                    JSONObject jsonObject=JSON.parseObject(result);
                    if(jsonObject.containsKey("data") && jsonObject.containsKey("result")
                            && jsonObject.getString("result").equals("success")){
                        //TODO 下单成功
                        JSONObject json1=JSON.parseObject(jsonObject.getString("data"));
                        JSONObject json2=JSON.parseObject(json1.getString("bankRequest"));
//                        (String merchantOrderId, String payOrderId, String noncestr, String appid,
//                                String packagetype, String timestamp,
//                                String paysign, String sibgtype, String amount, String openId, String storeId)
                        PayBack payBack=new PayBack(request.getMerchantOrderId(),json1.getString("requestNum"),
                                json2.getString("NONCESTR"),json2.getString("APPID"),json2.getString("PACKAGE"),
                                json2.getString("TIMESTAMP"),json2.getString("PAYSIGN"),json2.getString("SIBGTYPE"),
                                request.getAmount(),request.getOpenId(),request.getStoreId());

                        response=ResultMsgSure(payBack);
                    }else {
                        //TODO 下单失败
                        log.error("请求支付接口生成订单内失败了");
                        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO006003.getCode(),
                                ErrorEnum.SSCO006003.getMesssage(),(String) ""));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("支付下单出错了 {}",e.getMessage());
                    response=ResultMsgSeriousError();
                }
                break;
            case orderSysn:
                //订单同步  不需要回传
                try{
                    SMGPayConfig dlpPayConfigEntity=this.payMaper.selectByPrimaryKey(request.getStoreId());
                    if(dlpPayConfigEntity==null){
                        log.error("dlpPayConfigEntity {}","查询出来的dlb支付配置为空");
                        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO006003.getCode(),
                                ErrorEnum.SSCO006003.getMesssage(),(String) ""));
                    }
                    //重新赋值
                    dlbPayConnfig.setAccesskey(dlpPayConfigEntity.getAccesskey());
                    dlbPayConnfig.setSecretkey(dlpPayConfigEntity.getSecretkey());
                    dlbPayConnfig.setAgentnum(dlpPayConfigEntity.getAgentnum());
                    dlbPayConnfig.setCustomernum(dlpPayConfigEntity.getCustomernum());
                    dlbPayConnfig.setMachinenum(dlpPayConfigEntity.getMachinenum());
                    dlbPayConnfig.setShopnum(dlpPayConfigEntity.getShopnum());
                    String timeUnix=getTimeUnix();
                    String requestNum=request.getPayOrderId();
                    String urlAfter="/v1/agent/order/payresult/"
                            +dlbPayConnfig.getAgentnum()+"/"
                            +dlbPayConnfig.getCustomernum()+"/"
                            +dlbPayConnfig.getShopnum()+"/"+requestNum;
                    String url = "https://openapi.duolabao.com"+urlAfter;
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("accessKey",dlbPayConnfig.getAccesskey());
                    headers.set("timestamp",timeUnix);
                    String sign="secretKey="+dlbPayConnfig.getSecretkey()+"&timestamp="+timeUnix +
                            "&path="+urlAfter;
                    sign= SHA1.encode(sign).toUpperCase();
                    headers.set("token",sign);
                    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                    requestBody.add("agentNum", dlbPayConnfig.getAgentnum());
                    requestBody.add("customerNum", dlbPayConnfig.getCustomernum());
                    requestBody.add("shopNum", dlbPayConnfig.getShopnum());
                    requestBody.add("requestNum", requestNum);
                    HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, headers);
                    ResponseEntity<String> responseEntity =responseEntity = restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            new HttpEntity<String>(headers),
                            String.class,
                            requestEntity);
                    String result = responseEntity.getBody();
                    log.info("queryPayOrder 拿到的结果 {}",result);
                    JSONObject jsonObject=JSON.parseObject(result);
                    if(jsonObject.containsKey("data") && jsonObject.containsKey("result")
                            && jsonObject.getString("result").equals("success")){
                        //TODO 查询支付状态
                        jsonObject=JSON.parseObject(jsonObject.getString("data"));
                        if(jsonObject.getString("status").equals("SUCCESS")){
                            SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo(request.getOpenId(),
                                    request.getMerchantOrderId(),request.getPayOrderId(),
                                    0,1, Double.valueOf(jsonObject.getString("orderAmount")));
                            smgGoodsInfo.setPayedTime(new Date());
                            int i=smgGoodsInfoMapper.updateOrderStatus(smgGoodsInfo);
                            SMGMoneySaleLog smgMoneySaleLog=smgMoneySaleLogMapper.selectByPrimaryKey(request.getMerchantOrderId());
                            if(smgMoneySaleLog==null){
                                //支付记录
                                 smgMoneySaleLog=new SMGMoneySaleLog(null,request.getMerchantOrderId(),request.getOpenId(),request.getUnionId(),
                                         request.getStoreId(),request.getPayOrderId(),
                                         Double.valueOf(jsonObject.getString("orderAmount")),0,"WX",null);
                                smgMoneySaleLogMapper.insert(smgMoneySaleLog);
                            }
                            if(i>0){
                                log.info("同步订单成功 OpenId {}, MerchantOrderId {} ",
                                        request.getOpenId(),request.getMerchantOrderId());
                            }else {
                                log.info("同步订单完成出错了 OpenId {}, MerchantOrderId {} ",
                                        request.getOpenId(),request.getMerchantOrderId());
                            }
                        }
                    }
                    response=ResultMsgSuccess("");
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("同步订单完成出错了 OpenId {}, MerchantOrderId {} ",
                            request.getOpenId(),request.getMerchantOrderId());
                    response=ResultMsgSeriousError();
                }
                break;
            default:
                response="地址错误";
                log.info("请求地址出错了");
                break;
        }
        return response;
    }

    public void commitCartInfo(Request request,ErrorEnum errorEnum) throws ApiSysException{
        //TODO 这个是对整个购物车的操作
        CommonServiceImpl.updateCartInfoMerchantOrderId(request, commMapper);
        //TODO 得到会员信息
        String vipNo="";
        String bDiscount="0";
        String fPFrate="100";
        //TODO 计算获取购物车的信息
        CommonServiceImpl.SubmitShoppingCartCalculation(request, vipNo, bDiscount, fPFrate,commMapper);

    }
    /*
    * 微信小程序推送到客户
    * */
    @Override
    public void pushOrder(String openid, String formid,String appid, String appsecret,String merchantOrderId) {

        SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo(openid,merchantOrderId,
                null,null,0,null);
        List<SMGGoodsInfo> list=this.smgGoodsInfoMapper.getSMGGoodsInfoBySMGGoodsInfo(smgGoodsInfo);
        if(list==null || list.size()==0){
           return;
        }
        String  payOrderId ="";
        Double ActualAmount=0.0;
        for(SMGGoodsInfo s:list){
            payOrderId=s.getPayOrderId();
            ActualAmount=s.getActualAmount();
        }

        //获取access_token
        String access_token =getSingletonTocken(restTemplate,appid,appsecret);
        log.info("我是拿到的tocken {}",access_token);
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send" +
                "?access_token=" + access_token;
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openid);//用户openid
        wxMssVo.setTemplate_id("Ai6h0WlVxzPeIy8R8Hafp7nMQdqjqousenkv1HSm-H4");//模版id
        SMGUser user=this.userMapper.selectByPrimaryKey(openid);
        wxMssVo.setForm_id(user.getFormId());//formid
        wxMssVo.setPage("pages/order/orderDetail?opendId="+openid+"&merchantOrderId="+merchantOrderId+"&type=pushOrder");
        Map<String, TemplateOrder> m = new HashMap<>(6);

        TemplateOrder keyword1 = new TemplateOrder();
        keyword1.setValue(merchantOrderId);
        m.put("keyword1", keyword1);

        TemplateOrder keyword2 = new TemplateOrder();
        keyword2.setValue(payOrderId);
        m.put("keyword2", keyword2);
        wxMssVo.setData(m);

        TemplateOrder keyword3 = new TemplateOrder();
        keyword3.setValue("消费门店");
        m.put("keyword3", keyword3);
        wxMssVo.setData(m);

        TemplateOrder keyword4 = new TemplateOrder();
        keyword4.setValue(String.valueOf(ActualAmount));
        m.put("keyword4", keyword4);
        wxMssVo.setData(m);

        TemplateOrder keyword5 = new TemplateOrder();
        keyword5.setValue("13628672210");
        m.put("keyword5", keyword5);
        wxMssVo.setData(m);

        TemplateOrder keyword6 = new TemplateOrder();
        keyword6.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        m.put("keyword6", keyword6);
        wxMssVo.setData(m);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        log.error("小程序推送结果={}", responseEntity.getBody());

    }


}
