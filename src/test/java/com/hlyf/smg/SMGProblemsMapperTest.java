package com.hlyf.smg;

import com.alibaba.fastjson.JSON;
import com.hlyf.smg.dao.SMGDao.SMGProblemsMapper;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.domin.SMGProblems;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.tool.String_Tool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019-08-26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGProblemsMapperTest {

    private static final Logger log= LoggerFactory.getLogger(SMGProblemsMapperTest.class);


    @Autowired
    private SMGProblemsMapper smgProblemsMapper;

    @Test
    public void contextLoads() throws ApiSysException {
        SMGProblems smgProblems=smgProblemsMapper.selectByPrimaryKey(
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"123");
        if(smgProblems!=null){
            log.info("我是获取到的数据 {}", JSON.toJSONString(smgProblems));
        }else {
            log.info("获取到的数据为空 {}");
        }
    }

    @Test
    public void contextInsert() throws ApiSysException {
        List<String> list=new ArrayList<>();
        list.add("1.jpg");
        list.add("2.jpg");
        list.add("3.jpg");
        list.add("4.jpg");

        String imageUrls= String_Tool.listToString(list);
        log.info("我是转化出来的图片地址 {}",imageUrls);
        //SMGProblems(String openId, String unionId, String userTel, String problemType, String description, String imageUrls)
        SMGProblems smgProblems=new SMGProblems("123","123",
                "13628672210","其他","老子不喜欢用这个app",imageUrls);
        int i=smgProblemsMapper.insert(smgProblems);
        log.info("我是影响行数 {}",i);

    }
}
