package com.hlyf.smg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGCommonProblemsMapper;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoLogMapper;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoMapper;
import com.hlyf.smg.domin.Request;
import com.hlyf.smg.domin.SMGCommonProblems;
import com.hlyf.smg.domin.SaleLogs;
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
 * Created by Administrator on 2019-08-26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGCommonProblemsMapperTest {

    private static final Logger log= LoggerFactory.getLogger(SMGCommonProblemsMapperTest.class);

    @Autowired
    private SMGCommonProblemsMapper smgCommonProblemsMapper;

    @Test
    public void test() throws ApiSysException {
        List<SMGCommonProblems> smgGoodsInfo=smgCommonProblemsMapper.selectAll();
        if(smgGoodsInfo!=null && smgGoodsInfo.size()>0){
            log.info("我是获取到的数据 {}", JSONObject.toJSONString(smgGoodsInfo, SerializerFeature.WriteMapNullValue));
        }else {
            log.info("我是获取到的数据为空 {}","");
        }
    }
}
