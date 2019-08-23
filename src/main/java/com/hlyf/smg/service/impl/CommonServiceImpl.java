package com.hlyf.smg.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.CommMapper;
import com.hlyf.smg.dao.SMGDao.PosMainMapper;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoMapper;
import com.hlyf.smg.domin.*;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.exception.ErrorEnum;
import com.hlyf.smg.result.ResultMsg;
import com.hlyf.smg.tool.RequestFacotry;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019-07-19.
 */
public class CommonServiceImpl {
    private static final Logger log= LoggerFactory.getLogger(CommonServiceImpl.class);

    /**
     * <pre>
     *     判断是否是生鲜商品
     * </pre>
     * @param request
     * @param posMain
     * @return
     * @throws ApiSysException
     */
    public static FrushGood getIsFrushGood(Request request, PosMainMapper posMain) throws ApiSysException {
        FrushGood frushGood=new FrushGood(false);
        frushGood.setBarcode(request.getBarcode());
        frushGood.setReceivingCode(request.getBarcode());
        posConfig posConfig=posMain.getposConfig(request.getPosName()+".dbo.Pos_Config","条码秤");
        if(posConfig==null){
            log.info("读取配置信息失败,请先配置");
            log.error("读取配置信息失败,请先配置");
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }else {
            log.info("我是获取到的posConfig结果集 {}", JSONObject.toJSONString(posConfig));
        }
        if(!request.getBarcode().startsWith(posConfig.getCCharID()) || request.getBarcode().length()<13){
            //不是生鲜直接返回
            return frushGood;
        }else {
            if(request.getBarcode().startsWith(posConfig.getCCharID()) && request.getBarcode().length()==13){
                String barcode=request.getBarcode().substring(posConfig.getIGoodsNoStart()-1,posConfig.getIGoodsNoEnd());
                String money=request.getBarcode().substring(posConfig.getIMoneyStart()-1,posConfig.getIMoneyEnd());
                //单位  元
                double money2 = Double.valueOf(money) / posConfig.getIRatio();
                //重新赋值
                frushGood.setWeight(true);
                frushGood.setAllMoney(money2);
                frushGood.setBarcode(barcode);
            }else
            if(request.getBarcode().startsWith(posConfig.getCCharID()) && request.getBarcode().length()==18){
                String barcode=request.getBarcode().substring(posConfig.getIGoodsNoStart()-1,posConfig.getIGoodsNoEnd());
                String money=request.getBarcode().substring(posConfig.getIMoneyStart18()-1,posConfig.getIMoneyEnd18());
                String wight=request.getBarcode().substring(posConfig.getIWeightStart18()-1,posConfig.getIWeightEnd18());
                //重量 kg
                double wight2 = new Double(wight) / 1000;
                //单位  元
                double money2 = (new Double(money) ) / posConfig.getIRatio();
                //重新赋值
                frushGood.setBarcode(barcode);
                frushGood.setWeight(true);
                frushGood.setWeightwight(wight2);
                frushGood.setAllMoney(money2);

            }else {
                frushGood.setWeight(false);
                return frushGood;
            }
        }
        return frushGood;
    }

    /**
     * <pre>
     *     查询整个购物车
     * </pre>
     * @param request
     * @param errorEnum
     * @param smgGoodsInfoMapper
     * @return
     * @throws ApiSysException
     */
    public static String SelectCartInfo(Request request,ErrorEnum errorEnum,SMGGoodsInfoMapper smgGoodsInfoMapper) throws ApiSysException {
        try{

            List<SMGGoodsInfo> smgGoodsInfos=smgGoodsInfoMapper.selectAllByOpendIdAndOrderStatus(request.getOpenId());
            double totalFee=0.0;
            double discountFee=0.0;
            double actualFee=0.0;
            String merchantOrderId="";
            BigDecimal decimaltotalFee = new BigDecimal("0");
            BigDecimal decimaldiscountFee = new BigDecimal("0");
            if(smgGoodsInfos!=null && smgGoodsInfos.size()>0){
                for(SMGGoodsInfo t:smgGoodsInfos){
                    totalFee=totalFee+t.getAmount();
                    discountFee=discountFee+t.getDiscountAmount();

                    decimaltotalFee=decimaltotalFee.add(new BigDecimal(String.valueOf(t.getAmount())));
                    decimaldiscountFee=decimaldiscountFee.add(new BigDecimal(String.valueOf(t.getDiscountAmount())));
                    merchantOrderId=t.getMerchantOrderId();
                }
                //actualFee=totalFee-discountFee;
                totalFee=decimaltotalFee.doubleValue();
                discountFee=decimaldiscountFee.doubleValue();
                actualFee=decimaltotalFee.subtract(decimaldiscountFee).doubleValue();
            }
            cartInfo cartInfo=new cartInfo(merchantOrderId,totalFee,discountFee,actualFee,smgGoodsInfos);
            boolean returnStatus=errorEnum==ErrorEnum.SUCCESS ? true:false;
            ResultMsg resultMsg= new ResultMsg(returnStatus, errorEnum.getCode(),errorEnum.getMesssage(),cartInfo);
            String s1= JSON.toJSONString(resultMsg, SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero,
                    SerializerFeature.WriteNullBooleanAsFalse);
            return s1;
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询购物车:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001001);
        }
    }

    /**
     * <pre>
     *     得到未结算购物车单号
     * </pre>
     * @param request
     * @param smgGoodsInfoMapper
     * @return
     * @throws ApiSysException
     */
    public static String getMerchantOrderId(Request request, SMGGoodsInfoMapper smgGoodsInfoMapper)  throws ApiSysException{
        String merchantOrderId= RequestFacotry.getOrderIdByRequest(request);
        try{
            SMGGoodsInfo smgGoodsInfo= smgGoodsInfoMapper.selectOneByOpendIdAndOrderStatus(request.getOpenId());
            if(smgGoodsInfo!=null){
                merchantOrderId=smgGoodsInfo.getMerchantOrderId();
            }
            log.info("获取的单号是 {}  openid是 {}  UnionId是{}  ",merchantOrderId,request.getOpenId(),request.getUnionId());
            return merchantOrderId;
        }catch (Exception e){
            e.printStackTrace();
            log.error("生成单号或查询单号的时间出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }


    }

    /**
     * <pre>
     *     得到未结算购物车单号
     * </pre>
     * @param request
     * @param smgGoodsInfoMapper
     * @return
     * @throws ApiSysException
     */
    public static OrderComm getMerchantOrderIdTrue(Request request, SMGGoodsInfoMapper smgGoodsInfoMapper)  throws ApiSysException{
        String merchantOrderId= RequestFacotry.getOrderIdByRequest(request);
        Date date=new Date();
        try{
            SMGGoodsInfo smgGoodsInfo= smgGoodsInfoMapper.selectOneByOpendIdAndOrderStatus(request.getOpenId());
            if(smgGoodsInfo!=null){
                merchantOrderId=smgGoodsInfo.getMerchantOrderId();
                date=smgGoodsInfo.getShowTime();
            }
            log.info("获取的单号是 {}  openid是 {}  UnionId是{}  ",merchantOrderId,request.getOpenId(),request.getUnionId());
            return new OrderComm(date,merchantOrderId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("生成单号或查询单号的时间出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }

    public static void updateCartInfoMerchantOrderId(Request request, CommMapper commMapper)   throws ApiSysException{
        try{
            commMapper.p_smgdatatoTemp(request.getMerchantOrderId(),request.getOpenId(),"",request.getPosId(),
                                      request.getPosName()+".dbo.pos_SaleSheetDetailTemp");
        }catch (Exception e){
            e.printStackTrace();
            log.error("数据插入临时表出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }

    public static void SubmitShoppingCartCalculation(Request request, String vipNo, String bDiscount, String fPFrate, CommMapper commMapper)  throws ApiSysException{
        try{
            List<preferentialGoods> list= commMapper.get_preferentialGoods(request.getStoreId(),request.getPosId(),
                    request.getMerchantOrderId(),vipNo,fPFrate,bDiscount,request.getPosName()+".dbo.p_ProcessPosSheetSMG");
            if(list!=null && list.size()>0){
                log.info("获取到的优惠商品有 {} ",JSON.toJSONString(list));
            }else {
                log.info("获取到的优惠商品为空 ");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取整单优惠信息出错了:  {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001002);
        }
    }
}
