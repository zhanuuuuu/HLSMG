package com.hlyf.smg.domin.WxEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Administrator on 2019-08-27.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
public class WxMssVo {

    private String touser;//用户openid
    private String template_id;//模版id
    private String page = "pages/order/orderDetail";//默认跳到小程序首页
    private String form_id;//收集到的用户formid
    private String emphasis_keyword = "keyword4.DATA";//放大那个推送字段
    private Map<String, TemplateOrder> data;
}
