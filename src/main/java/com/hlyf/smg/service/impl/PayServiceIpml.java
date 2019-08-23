package com.hlyf.smg.service.impl;

import com.hlyf.smg.config.DlbPayConnfig;
import com.hlyf.smg.config.SMGConnfig;
import com.hlyf.smg.dao.SMGDao.*;
import com.hlyf.smg.domin.SMGGoodsInfo;
import com.hlyf.smg.domin.payentity.SMGMoneySaleLog;
import com.hlyf.smg.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-23.
 */
@Service
public class PayServiceIpml implements PayService {
    private static final Logger log= LoggerFactory.getLogger(PayServiceIpml.class);

    @Autowired
    private SMGGoodsInfoMapper smgGoodsInfoMapper;

    @Autowired
    private SMGMoneySaleLogMapper smgMoneySaleLogMapper;

    @Override
    public void acceptPayResult(String requestNum, String orderNum, String orderAmount, String status,
                                String completeTime, String payOrderId, String opendId, String merchantOrderId) {
        try{
            SMGGoodsInfo smgGoodsInfo=new SMGGoodsInfo(opendId,
                    merchantOrderId,payOrderId,
                    0,1, Double.valueOf(orderAmount));
            smgGoodsInfo.setPayedTime(new Date());
            smgGoodsInfoMapper.updateOrderStatus(smgGoodsInfo);
            SMGMoneySaleLog smgMoneySaleLog=smgMoneySaleLogMapper.selectByPrimaryKey(merchantOrderId);
            if(smgMoneySaleLog==null){
                //支付记录
                smgMoneySaleLog=new SMGMoneySaleLog(null,merchantOrderId,opendId,null,
                        null,payOrderId,
                        Double.valueOf(orderAmount),0,"WX",null);
                smgMoneySaleLogMapper.insert(smgMoneySaleLog);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("接收支付结果通知后 操作数据库记录日志失败了 {}",e.getMessage());
        }
    }
}
