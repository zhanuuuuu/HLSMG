package com.hlyf.smg.domin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-20.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class OrderComm {

    private Date showTime;
    private String merchantOrderId;

    public OrderComm(Date showTime, String merchantOrderId) {
        this.showTime = showTime;
        this.merchantOrderId = merchantOrderId;
    }
}
