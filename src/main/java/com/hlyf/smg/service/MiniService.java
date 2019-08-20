package com.hlyf.smg.service;

/**
 * Created by Administrator on 2019-08-14.
 */
public interface MiniService {

    String GetOpenIOrUniuiddByCode(String code,String appid,String appsecret);
    String Wxdecrypt(String openId,String encryptedData, String sessionKey, String iv);


}
