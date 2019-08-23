package com.hlyf.smg.service;

/**
 * Created by Administrator on 2019-08-23.
 */
public interface PayService {
    void acceptPayResult(String requestNum, String orderNum, String orderAmount, String status,
                         String completeTime, String payOrderId, String opendId, String merchantOrderId);
}
