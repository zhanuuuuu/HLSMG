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






}
