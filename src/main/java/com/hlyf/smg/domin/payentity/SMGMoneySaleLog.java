package com.hlyf.smg.domin.payentity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019-08-23.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SMGMoneySaleLog {

    private Long lineId;

    private String merchantOrderId;

    private String openId;

    private String unionId;

    private String storeId;

    private String payOrderId;

    private Double amount;

    private Integer saleStatus;

    private String payType;

    private Date saleTime;

    public SMGMoneySaleLog(Long lineId, String merchantOrderId, String openId, String unionId, String storeId,
                           String payOrderId, Double amount, Integer saleStatus, String payType, Date saleTime) {
        this.lineId = lineId;
        this.merchantOrderId = merchantOrderId;
        this.openId = openId;
        this.unionId = unionId;
        this.storeId = storeId;
        this.payOrderId = payOrderId;
        this.amount = amount;
        this.saleStatus = saleStatus;
        this.payType = payType;
        this.saleTime = saleTime;
    }
}
