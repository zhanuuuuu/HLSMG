package com.hlyf.smg.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019-07-11.
 */
@Configuration
@Data
public class SMGConnfig {

    @Value("${smg.isdandian}")
    private Boolean isdandian;

    @Value("${smg.calprice}")
    private Boolean calprice;

    @Value("${smg.callbackurl}")
    private String callbackurl;

    @Value("${smg.isacceptpayresult}")
    private Boolean isacceptpayresult;

    @Value("${smg.istest}")
    private Boolean istest;

    @Value("${smg.testmoney}")
    private String testmoney;


    @Value("${smg.isuseimageurl}")
    private Boolean isuseimageurl;

    @Value("${smg.imageurl}")
    private String imageurl;

    @Value("${smg.appid}")
    private String appid;

    @Value("${smg.appsecret}")
    private String appsecret;









}
