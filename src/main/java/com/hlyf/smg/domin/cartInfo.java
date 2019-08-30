package com.hlyf.smg.domin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019-08-19.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class cartInfo {

    /**
     *商户订单号
     */
    private String merchantOrderId;
    /**
     *合计总金额
     * 单位分
     */
    private double totalFee;
    /**
     *优惠总金额
     * 单位分
     */
    private double discountFee;
    /**
     *优惠后应付总金额
     * 单位分
     */
    private double actualFee;
    /**
     *  商品信息
     */
    private List<SMGGoodsInfo> items=new ArrayList<SMGGoodsInfo>();

    private String storeId;

    private String storeName;

    public cartInfo(String merchantOrderId, double totalFee, double discountFee, double actualFee, List<SMGGoodsInfo> items) {
        this.merchantOrderId = merchantOrderId;
        this.totalFee = totalFee;
        this.discountFee = discountFee;
        this.actualFee = actualFee;
        this.items = items;
    }
}
