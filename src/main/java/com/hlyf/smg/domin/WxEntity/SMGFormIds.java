package com.hlyf.smg.domin.WxEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-27.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
public class SMGFormIds {
    private Long lineId;

    private String openId;

    private String formId;

    private Date createTime;

}
