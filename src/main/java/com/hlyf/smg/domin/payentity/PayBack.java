package com.hlyf.smg.domin.payentity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-08-21.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class PayBack {

    private String merchantOrderId;
    private String payOrderId;
    private String noncestr;
    private String appid;
    private String packagetype;
    private String timestamp;
    private String paysign;
    private String sibgtype;
    private String amount;
    private String openId;
    private String storeId;

    public PayBack(String merchantOrderId, String payOrderId, String noncestr, String appid, String packagetype, String timestamp,
                   String paysign, String sibgtype, String amount, String openId, String storeId) {

        this.merchantOrderId = merchantOrderId;
        this.payOrderId = payOrderId;
        this.noncestr = noncestr;
        this.appid = appid;
        this.packagetype = packagetype;
        this.timestamp = timestamp;
        this.paysign = paysign;
        this.sibgtype = sibgtype;
        this.amount = amount;
        this.openId = openId;
        this.storeId = storeId;
    }
}
