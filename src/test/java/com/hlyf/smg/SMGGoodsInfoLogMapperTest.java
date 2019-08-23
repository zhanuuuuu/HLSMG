package com.hlyf.smg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoLogMapper;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoMapper;
import com.hlyf.smg.domin.Request;
import com.hlyf.smg.domin.SMGGoodsInfo;
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
 * Created by Administrator on 2019-08-23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGGoodsInfoLogMapperTest {

    private static final Logger log= LoggerFactory.getLogger(SMGGoodsInfoMapperTest.class);

    @Autowired
    private SMGGoodsInfoMapper smgGoodsInfoMapper;

    @Autowired
    private SMGGoodsInfoLogMapper smgGoodsInfoLogMapper;

    @Test
    public void testSelectMGGoodsInfo2() throws ApiSysException {
        List<SaleLogs> smgGoodsInfo= smgGoodsInfoLogMapper.p_getSmgOrdersLog(
                new Request("o_gYO5JnA-34K65x_kcxi25j4gMc",1,10,3)
        );
        if(smgGoodsInfo!=null && smgGoodsInfo.size()>0){
            log.info("我是获取到的数据 {}", JSONObject.toJSONString(smgGoodsInfo, SerializerFeature.WriteMapNullValue));
        }else {
            log.info("我是获取到的数据为空 {}","");
        }
    }

    @Test
    public void testSelectMGGoodsInfo() throws ApiSysException {
        List<SMGGoodsInfo> smgGoodsInfo =smgGoodsInfoMapper.selectAll();
        if(smgGoodsInfo!=null && smgGoodsInfo.size()>0){
            log.info("我是获取到的数据 {}", JSONObject.toJSONString(smgGoodsInfo, SerializerFeature.WriteMapNullValue));
        }else {
            log.info("我是获取到的数据为空 {}","");
        }

    }
}
