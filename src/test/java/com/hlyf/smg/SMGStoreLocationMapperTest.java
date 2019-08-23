package com.hlyf.smg;

import com.alibaba.fastjson.JSONObject;
import com.hlyf.smg.dao.SMGDao.OfflineStoreDao;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.exception.ApiSysException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2019-08-08.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGStoreLocationMapperTest {
    private static final Logger log= LoggerFactory.getLogger(SMGStoreLocationMapperTest.class);

    @Autowired
    private SMGStoreLocationMapper storeLocationMapper;

    @Test
    public void contextLoads() throws ApiSysException {
//        SMGStoreLocation(String openId, String unionId, String province, String city, String location,
//                String storeId, String storeName, String longitude, String latitude, Integer limitNumber)
        SMGStoreLocation smgStoreLocation=new SMGStoreLocation("123", "123", "湖北省",
                "武汉市", "湖北省武汉市",
                "000", "总部000", "34.2675560000", "114.9534750000", 123,"江夏区");
        Integer i=storeLocationMapper.insert(smgStoreLocation);
        log.info("我是影响行数 {}",i);

    }

    @Test
    public void contextInsert() throws ApiSysException {


    }

}
