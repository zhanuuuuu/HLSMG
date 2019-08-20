package com.hlyf.smg.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.config.SMGConnfig;
import com.hlyf.smg.config.SMGUrlConfig;
import com.hlyf.smg.dao.SMGDao.OfflineStoreDao;
import com.hlyf.smg.dao.SMGDao.PosMainMapper;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoMapper;
import com.hlyf.smg.domin.*;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.exception.ErrorEnum;
import com.hlyf.smg.result.ResultMsg;
import com.hlyf.smg.service.SmgService;
import com.hlyf.smg.tool.RequestFacotry;
import com.hlyf.smg.tool.String_Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hlyf.smg.result.ResultMsg.ResultMsgSeriousError;

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
    private void SaveGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException{
        try{
            //TODO 得到单号
            String merchantOrderId=CommonServiceImpl.getMerchantOrderId(request, smgGoodsInfoMapper);
            //得到商品信息
            cStoreGoods storeGoods=cStoreGoodsList.get(0);
            Double NomalPrice=storeGoods.getFNormalPrice();
            log.info("查询到的商品信息  {}", JSON.toJSON(storeGoods).toString());
            //如果是生鲜 直接保存到购物车
            if(frushGood.isWeight()){
                //如果是13码  计算出来重量
                if(request.getBarcode().length()==13){
                    if(NomalPrice==0){
                        log.error("查询商品的时间出错了: 查询出来的单价是 0");
                        throw  new ApiSysException(ErrorEnum.SSCO001001);
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

            }else {
                //检测购物车是否存在改商品



                //如果没有改商品保存的购物车   如果存在就更新该购物车
                if(""!=null){


                }else{


                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询商品的时间出错了:  {}",e.getMessage());
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
            case selectGoods:
                try {
                    FrushGood frushGood=CommonServiceImpl.getIsFrushGood(request, this.posMain);
                    List<String> list=new ArrayList<>();
                    list.add(frushGood.getBarcode());
                    List<cStoreGoods> storeGoodsList=this.GetcStoreGoodsS(request.getStoreId(),list);
                    RequestFacotry.GoodListIsEmpty(storeGoodsList);
                    log.info("获取出来的商品是 {}",JSONObject.toJSON(storeGoodsList).toString());
                    //TODO 这里写业务逻辑  查询 或者更改
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

                break;
            case deleteGoods:

                break;
            case clearCartInfo:

                break;
            case commitCartInfo:

                break;
            case cancleOrder:

                break;
            case orderSysn:
                try{

                }catch (Exception e){
                    e.printStackTrace();
                    log.error("同步订单完成出错了 {}",e.getMessage());
                }

                break;
            default:
                response="地址错误";
                log.info("请求地址出错了");
                break;
        }
        return response;
    }



}
