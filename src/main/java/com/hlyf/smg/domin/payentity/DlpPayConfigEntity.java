package com.hlyf.smg.domin.payentity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-08-02.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class DlpPayConfigEntity {

    private String tenant;

    private String accesskey;

    private String secretkey;

    private String agentnum;

    private String customernum;

    private String machinenum;

    private String shopnum;

    private String storeId;

    public DlpPayConfigEntity(String tenant, String accesskey, String secretkey, String agentnum,
                              String customernum, String machinenum, String shopnum, String storeId) {
        this.tenant = tenant;
        this.accesskey = accesskey;
        this.secretkey = secretkey;
        this.agentnum = agentnum;
        this.customernum = customernum;
        this.machinenum = machinenum;
        this.shopnum = shopnum;
        this.storeId = storeId;
    }
}
