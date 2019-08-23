package com.hlyf.smg.domin.payentity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-21.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SMGPayConfig {
    private String storeId;

    private String sn;

    private Long lineId;

    private String accesskey;

    private String secretkey;

    private String agentnum;

    private String customernum;

    private String machinenum;

    private String shopnum;

    private Date createTime;
}
