package com.hlyf.smg;

import com.alibaba.fastjson.JSON;
import com.hlyf.smg.dao.SMGDao.SMGMoneySaleLogMapper;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.domin.payentity.SMGMoneySaleLog;
import com.hlyf.smg.exception.ApiSysException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGMoneySaleLogMapperTest {

    private static final Logger log= LoggerFactory.getLogger(SMGMoneySaleLogMapperTest.class);


    @Autowired
    private SMGMoneySaleLogMapper smgMoneySaleLogMapper;

    @Test
    public void contextLoads() throws ApiSysException {
//        SMGMoneySaleLog(Long lineId, String merchantOrderId, String openId, String unionId, String storeId,
//                String payOrderId, Double amount, Integer saleStatus, String payType, Date saleTime)
        SMGMoneySaleLog smgMoneySaleLog=new SMGMoneySaleLog(null,"123","123","123",null,"123",
                20.36,0,"WX",null);

        Integer i=smgMoneySaleLogMapper.insert(smgMoneySaleLog);
        log.info("我是影响行数 {}",i);

    }

    @Test
    public void testSelect() throws ApiSysException {

        SMGMoneySaleLog smgMoneySaleLog=smgMoneySaleLogMapper.selectByPrimaryKey("123");
        if(smgMoneySaleLog!=null){
            log.info("我是获取到的数据 {}", JSON.toJSONString(smgMoneySaleLog));
        }else{
            log.info("获取到的数据为空");
        }
    }
}
