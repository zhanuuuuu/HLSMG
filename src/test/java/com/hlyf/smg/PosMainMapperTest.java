package com.hlyf.smg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlyf.smg.config.SMGConnfig;
import com.hlyf.smg.dao.SMGDao.PosMainMapper;
import com.hlyf.smg.domin.cStoreGoods;
import com.hlyf.smg.domin.posConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019-08-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PosMainMapperTest {
    private static final Logger log= LoggerFactory.getLogger(PosMainMapperTest.class);

    @Autowired
    private PosMainMapper posMain;

    @Autowired
    private SMGConnfig dlbConnfig;


    @Test
    public void getGoods() {
        List<String> stringList=new ArrayList<>();

        stringList.add("1010000001");
        List<cStoreGoods> list=null;
        try {
            if(!this.dlbConnfig.getIsdandian()){
                //单店的走这里
                list=this.posMain.GetcStoreGoods("0002", stringList);
            }else {
                //连锁的走这里
                list=this.posMain.GetcStoreGoodsDanDian("0002", stringList);
            }
            if(list!=null){
                log.info("获取到的结果是  {}", JSON.toJSON(list).toString());
            }else {
                log.info("结果集为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName()+"获取本地商品数据出错了 {}",e.getMessage());
        }
    }

    @Test
    public void getposConfig() {
        posConfig posConfig=this.posMain.getposConfig("posstation101"+".dbo.Pos_Config","条码秤");
        if(posConfig==null){
            log.info("读取配置信息失败,请先配置");
            log.error("读取配置信息失败,请先配置");

        }else {
            log.info("我是获取到的posConfig结果集 {}", JSONObject.toJSONString(posConfig));
        }
    }




}
