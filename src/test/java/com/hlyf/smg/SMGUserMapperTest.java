package com.hlyf.smg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.dao.SMGDao.SMGUserMapper;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.domin.SMGUser;
import com.hlyf.smg.exception.ApiSysException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2019-08-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGUserMapperTest {

    private static final Logger log= LoggerFactory.getLogger(SMGUserMapperTest.class);

    @Autowired
    private SMGUserMapper userMapper;

    @Test
    public void contextLoads() {
        int i=this.userMapper.insert(new SMGUser("1",null));
        log.info("我是影响行数 {}",i);
    }

    @Test
    public void testSelectSMGUser() {
        SMGUser user=this.userMapper.selectByPrimaryKey("1");
        if(user!=null){
            log.info("我是获取的数据 {}", JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue));
        }else {
            log.info("我是获取的数据 {}", " 空的 ");
        }
    }

    @Test
    public void testupdateSMGUser() {
        int i=this.userMapper.updateByPrimaryKey(new SMGUser("1",null,"123"));
        log.info("我是影响行数 {}",i);
    }
    @Test
    public void testupdateUnionIdByOpenIdSMGUser() {
        int i=this.userMapper.updateUnionIdByOpenId(
                new SMGUser("o_gYO5JnA-34K65x_kcxi25j4gMc","","123")
                .setFormId("123456789"));
        log.info("我是影响行数 {}",i);
    }

}
