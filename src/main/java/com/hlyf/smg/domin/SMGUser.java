package com.hlyf.smg.domin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-15.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SMGUser {

    private Long lineId;

    private String openId;

    private String unionId;

    private String userTel;

    private Integer administration;

    private Date createTime;

    private String session_key;

    public SMGUser(String openId, String unionId) {
        this.openId = openId;
        this.unionId = unionId;
    }
    public SMGUser( String openId, String unionId, String userTel) {
        this.openId = openId;
        this.unionId = unionId;
        this.userTel = userTel;
    }
}
