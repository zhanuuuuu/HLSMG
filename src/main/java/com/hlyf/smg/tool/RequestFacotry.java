package com.hlyf.smg.tool;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

import com.hlyf.smg.domin.Request;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.exception.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;



/**
 * Created by Administrator on 2019-07-15.
 * <pre>
 *     封装的方法类
 *     不要随意改动  OK?
 * </pre>
 */
public class RequestFacotry {

    private static final Logger log= LoggerFactory.getLogger(RequestFacotry.class);


    private static final Integer randomlimit=8;

    //int randNumber =rand.nextInt(MAX - MIN + 1) + MIN
    public static void main(String[] args) {

        String str = null;
        try {
            str = getOrderId("81",145678);
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        System.out.println(str.length());
        System.out.println(new SimpleDateFormat("yyyyMMddHH:mm:ss").format(new Date()));
    }

    /**
     * <pre>
     *     得到32位不重复的单号
     * </pre>
     * @param posId
     * @param userLineId   用户编号
     * @return
     * @throws ApiSysException
     */
    public static String getOrderId(String posId,Integer userLineId)  throws ApiSysException {
        try{
            int randNumber =new Random().nextInt(900000) + 100000;
            return posId
                    +new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    +String.format("%010d", userLineId)
                    +randNumber;
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取单号失败 {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001001);
        }
    }

    /**
     * <pre>
     *     根据参数规范  通过算法计算出来单号  保证不重复
     *     目前最大支持 10亿人同时操作是生成不重复的单号
     * </pre>
     * @param request
     * @return
     * @throws ApiSysException
     */
    public static String getOrderIdByRequest(Request request)  throws ApiSysException {
        try{
            int randNumber =new Random().nextInt(900000) + 100000;
            return request.getPosId()
                    +new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    +String.format("%010d", request.getUserlineId())
                    +randNumber;
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取单号失败 {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001001);
        }
    }

    /**
     *
     * @param code
     * @return
     */
    public static ErrorEnum getErrorEnumByCode(String code){
        for(ErrorEnum sexEnum : ErrorEnum.values()){
            if(StringUtils.equals(code, sexEnum.getCode())){
                return sexEnum;
            }
        }
        return ErrorEnum.SSCO001001;
    }
    /**
     *  <pre>
     *      获取UUID
     *  </pre>
     * @throws ApiSysException
     */
    public static String getUUID()  throws ApiSysException {
        try{
            return UUID.randomUUID().toString().replaceAll("-", "");
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取UUID失败 {}",e.getMessage());
            throw  new ApiSysException(ErrorEnum.SSCO001001);
        }
    }
    /**
     *  <pre>
     *      检验商品是否为空
     *  </pre>
     * @param list
     * @throws ApiSysException
     */
    public static void GoodListIsEmpty(List list)  throws ApiSysException {
        if(list==null || list.size()==0){
            throw  new ApiSysException(ErrorEnum.SSCO010004);
        }
    }

    /**
     * <pre>
     *     解析上传的参数
     * </pre>
     * @param desKey
     * @param jsonObject
     * @param errorEnum    不可以写null
     * @return
     * @throws ApiSysException
     */
    public static Request decryptCipherJsonToRequest(String desKey, JSONObject jsonObject, ErrorEnum errorEnum) throws ApiSysException {
        try{
            if(errorEnum==null){
                //throw new ApiSysException("方法的错误类型不可以写null,请填写错误类型");
                throw new ApiSysException("方法的错误类型不可以写null,请填写错误类型",new Throwable("方法的错误类型不可以写null,请填写错误类型"));
            }
            Request request=new Request();
            request.setStoreId(jsonObject.getString("storeId"));
            request.setUnionId(jsonObject.getString("unionId"));
            request.setOpenId(jsonObject.getString("openId"));
            if(jsonObject.containsKey("userlineId")){
                request.setUserlineId(jsonObject.getInteger("userlineId"));
            }
            request.setPosName(jsonObject.getString("posName"));
            request.setPosId(jsonObject.getString("posId"));
            request.setBarcode(jsonObject.getString("barcode"));
            request.setMerchantOrderId(jsonObject.getString("merchantOrderId"));
            request.setPayOrderId(jsonObject.getString("payOrderId"));
            if(jsonObject.containsKey("goodlineId")){
                request.setGoodlineId(jsonObject.getInteger("goodlineId"));
            }
            if(jsonObject.containsKey("num")){
                request.setNum(jsonObject.getInteger("num"));
            }
            if(jsonObject.containsKey("pageNum")){
                request.setPageNum(jsonObject.getInteger("pageNum"));
            }
            if(jsonObject.containsKey("number")){
                request.setNumber(jsonObject.getInteger("number"));
            }
            if(jsonObject.containsKey("iFlag")){
                request.setIFlag(jsonObject.getInteger("iFlag"));
            }
            request.setAmount(jsonObject.getString("amount"));
            request.setFormId(jsonObject.getString("formId"));

            //
            request.setStoreName(jsonObject.getString("storeName"));

            //storeTel
            request.setStoreTel(jsonObject.getString("storeTel"));
            request.setAppid(jsonObject.getString("appid"));
            request.setAppsecret(jsonObject.getString("appsecret"));

            if(request!=null){
                return request;
            }else {
                throw new ApiSysException(errorEnum);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[0].getMethodName()+" 解析上传的参数 ："+e.getMessage());
            throw new ApiSysException(errorEnum);
        }

    }


}
