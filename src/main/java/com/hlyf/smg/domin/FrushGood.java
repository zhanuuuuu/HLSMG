package com.hlyf.smg.domin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-07-19.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class FrushGood {

    /**
     * 是否生鲜
     */
    private boolean isWeight;
    /**
     *金额 单位元
     */
    private Double allMoney;
    /**
     *单价
     */
    private Double price;
    /**
     *重量
     */
    private double weightwight;

    /**
     *生鲜码
     */
    private String barcode;

    /**
     * 接收到的原始码
     */
    private String receivingCode;

    public FrushGood(boolean isWeight) {
        this.isWeight = isWeight;
    }

    public FrushGood(boolean isWeight, Double allMoney, Double price, Long weight) {
        this.isWeight = isWeight;
        this.allMoney = allMoney;
        this.price = price;
        this.weightwight = weight;
    }


}
