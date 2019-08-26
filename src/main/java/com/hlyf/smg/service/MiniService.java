package com.hlyf.smg.service;

import java.util.List;

/**
 * Created by Administrator on 2019-08-14.
 */
public interface MiniService {

    String GetOpenIOrUniuiddByCode(String code,String appid,String appsecret);
    String Wxdecrypt(String openId,String encryptedData, String sessionKey, String iv);


    void insertProblems(String openId, String unionId, String userTel, String problemType, String description, List<String> urlImages);
}
