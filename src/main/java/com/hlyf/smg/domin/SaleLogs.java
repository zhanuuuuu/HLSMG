package com.hlyf.smg.domin;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-23.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SaleLogs {

    private Integer number;
    private Integer rowNumber;
    private String openId;
    private String merchantOrderId;
    private Double amount;
    private String storeId;
    private Integer orderStatus;
    private Integer orderType;
    private String storeName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date showTime;
}
