package com.hlyf.smg.domin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.util.Date;

/**
 * Created by Administrator on 2019-08-16.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SMGGoodsInfo {

    private Long lineId;

    private String openId;

    private String unionId;

    private String storeId;

    private String merchantOrderId;

    private String payOrderId;

    private Integer orderStatus;

    private Integer orderType;

    private String cGoodsNo;

    private String cGoodsName;

    private Double amount;

    private Double discountAmount;

    private Double basePrice;

    private Double price;

    private Integer qty;

    private Double dweight;

    private Boolean isWeight;

    private String barcode;

    private String unit;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date payedTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date showTime;

    private String checkUpNo;

    private String checkUpName;

    private String receivingCode;

    private Double actualAmount;

    public SMGGoodsInfo(String openId, String merchantOrderId,
                        String payOrderId, Integer orderType,Integer orderStatus, Double actualAmount) {
        this.openId = openId;
        this.merchantOrderId = merchantOrderId;
        this.payOrderId = payOrderId;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.actualAmount = actualAmount;
    }

    public SMGGoodsInfo(String openId, String unionId) {
        this.openId = openId;
        this.unionId = unionId;
    }

    /**
     *  该构造方法有用
     */
    public SMGGoodsInfo(String openId, String unionId, String storeId,
                        String merchantOrderId, String payOrderId, String cGoodsNo,
                        String cGoodsName, Double amount, Double discountAmount,
                        Double basePrice, Double price,
                        Integer qty, Double dweight,
                        Boolean isWeight, String barcode,
                        String unit, String receivingCode) {
        this.openId = openId;
        this.unionId = unionId;
        this.storeId = storeId;
        this.merchantOrderId = merchantOrderId;
        this.payOrderId = payOrderId;
        this.cGoodsNo = cGoodsNo;
        this.cGoodsName = cGoodsName;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.basePrice = basePrice;
        this.price = price;
        this.qty = qty;
        this.dweight = dweight;
        this.isWeight = isWeight;
        this.barcode = barcode;
        this.unit = unit;
        this.receivingCode = receivingCode;
    }

    public SMGGoodsInfo(String openId, String unionId, String storeId,
                        String merchantOrderId, String payOrderId, String cGoodsNo,
                        String cGoodsName, Double amount, Double discountAmount,
                        Double basePrice, Double price,
                        Integer qty, Double dweight,
                        Boolean isWeight, String barcode,
                        String unit, String receivingCode,Date showTime) {
        this.openId = openId;
        this.unionId = unionId;
        this.storeId = storeId;
        this.merchantOrderId = merchantOrderId;
        this.payOrderId = payOrderId;
        this.cGoodsNo = cGoodsNo;
        this.cGoodsName = cGoodsName;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.basePrice = basePrice;
        this.price = price;
        this.qty = qty;
        this.dweight = dweight;
        this.isWeight = isWeight;
        this.barcode = barcode;
        this.unit = unit;
        this.receivingCode = receivingCode;
        this.showTime=showTime;
    }
}
