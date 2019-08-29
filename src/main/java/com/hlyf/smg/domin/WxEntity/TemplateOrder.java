package com.hlyf.smg.domin.WxEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Administrator on 2019-08-27.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
public class TemplateOrder {

    //关键词
//    订单号
//    {{keyword1.DATA}}
//
//    交易单号
//    {{keyword2.DATA}}
//
//    店铺名称
//    {{keyword3.DATA}}
//
//    订单总价
//    {{keyword4.DATA}}
//
//    客服电话
//    {{keyword5.DATA}}
//
//    购买时间
//    {{keyword6.DATA}}
    private String value;//,,依次排下去
//    private String color;//字段颜色（微信官方已废弃，设置没有效果）


}
