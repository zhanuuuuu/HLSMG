package com.hlyf.smg.domin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-07-22.
 * <pre>
 *     解密上传的参数
 *     统一接收类
 * </pre>
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class Request {

    //用户信息
    private String storeId;
    private String unionId;
    private String openId;
    private Integer userlineId;
    private String posName;
    private String posId;
    //扫描的商品码
    private String barcode;
    //订单单号
    private String merchantOrderId;

    //支付单号
    private String payOrderId;
    //购物中商品的id
    private Integer goodlineId;

    private Integer num;

    private String amount;//支付金额

    public Request(String storeId, String unionId, String openId,
                   Integer userlineId, String posName, String posId, String barcode, String merchantOrderId) {
        this.storeId = storeId;
        this.unionId = unionId;
        this.openId = openId;
        this.userlineId = userlineId;
        this.posName = posName;
        this.posId = posId;
        this.barcode = barcode;
        this.merchantOrderId = merchantOrderId;
    }
}
