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

    @Value("${dlb.isdandian}")
    private Boolean isdandian;

    @Value("${dlb.calprice}")
    private Boolean calprice;





}
