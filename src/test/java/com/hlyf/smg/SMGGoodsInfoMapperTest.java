package com.hlyf.smg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoMapper;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.domin.SMGGoodsInfo;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.exception.ApiSysException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019-08-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMGGoodsInfoMapperTest {

    private static final Logger log= LoggerFactory.getLogger(SMGGoodsInfoMapperTest.class);

    @Autowired
    private SMGGoodsInfoMapper smgGoodsInfoMapper;

    @Test
    public void testInsertSMGGoodsInfo() throws ApiSysException {
//        SMGGoodsInfo(String openId, String unionId, String storeId,
//                String merchantOrderId, String payOrderId, String cGoodsNo,
//                String cGoodsName, Double amount, Double discountAmount,
//                Double basePrice, Double price,
//                Integer qty, Double dweight,
//                Boolean isWeight, String barcode,
//                String unit, String receivingCode)
        SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo("o","omLZQ0ZRIBpbYMkYJhyDy1w3xWyA",
                "0002", "12345678998765432123456789987","12345678998765432123456789987",
                "10010","测试商品123",
                20.0,10.0,5.0,5.0,4,0.0,
                false,"123456","个","123456");
        smgGoodsInfo.setShowTime(new Date());
        int i=smgGoodsInfoMapper.insert(smgGoodsInfo);

        log.info("我是影响行数 {}",i);

    }


    @Test
    public void testUpdateSMGGoodsInfo() throws ApiSysException {
//        SMGGoodsInfo(String openId, String unionId, String storeId,
//                String merchantOrderId, String payOrderId, String cGoodsNo,
//                String cGoodsName, Double amount, Double discountAmount,
//                Double basePrice, Double price,
//                Integer qty, Double dweight,
//                Boolean isWeight, String barcode,
//                String unit, String receivingCode)
        SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo("o_gYO5JnA-34K65x_kcxi25j4gMc","omLZQ0ZRIBpbYMkYJhyDy1w3xWyA",
                "00020", "12345678998765432123456789980","12345678998765432123456789980",
                "100100","测试商品12300",
                200.0,100.0,50.0,50.0,40,0.0,
                false,"123456","个","123456");
        int i=smgGoodsInfoMapper.updateByBarcodeAndopenIdAndOrderStatus(smgGoodsInfo);

        log.info("我是影响行数 {}",i);

    }

    @Test
    public void testOrderType() throws ApiSysException {

        SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo("o_gYO5JnA-34K65x_kcxi25j4gMc",
                "12345678998765432123456789987","12",
                0,1, 200.0);
        int i=smgGoodsInfoMapper.updateOrderStatus(smgGoodsInfo);
        log.info("我是影响行数 {}",i);

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

    @Test
    public void testSelecAllByOpendIdAndOrderStatus() throws ApiSysException {
        List<SMGGoodsInfo> smgGoodsInfo =smgGoodsInfoMapper.selectAllByOpendIdAndOrderStatus("o_gYO5JnA-34K65x_kcxi25j4gMc");

        if(smgGoodsInfo!=null && smgGoodsInfo.size()>0){
            log.info("我是获取到的数据 {}", JSONObject.toJSONString(smgGoodsInfo, SerializerFeature.WriteMapNullValue));
        }else {
            log.info("我是获取到的数据为空 {}","");
        }

    }

    @Test
    public void testDelete() throws ApiSysException {
        int i=smgGoodsInfoMapper.deleteByPrimaryKey(1l);

        log.info("我是影响行数 {}",i);

    }


    @Test
    public void testSelecsmgGoodsInfo() throws ApiSysException {
        SMGGoodsInfo smgGoodsInfo =smgGoodsInfoMapper.selectByOpendIdAndOrderStatusAndBarcode("o_gYO5JnA-34K65x_kcxi25j4gMc","123456");

        if(smgGoodsInfo!=null){
            log.info("我是获取到的数据 {}", JSONObject.toJSONString(smgGoodsInfo, SerializerFeature.WriteMapNullValue));
        }else {
            log.info("我是获取到的数据为空 {}","");
        }

    }





}
