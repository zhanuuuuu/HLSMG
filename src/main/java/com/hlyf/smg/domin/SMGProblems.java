package com.hlyf.smg.domin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2019-08-26.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SMGProblems {
    private Long lineId;

    private String openId;

    private String unionId;

    private String userTel;

    private String problemType;

    private String description;

    private String imageUrls;

    private Date createTime;

    public SMGProblems(String openId, String unionId, String userTel, String problemType, String description, String imageUrls) {
        this.openId = openId;
        this.unionId = unionId;
        this.userTel = userTel;
        this.problemType = problemType;
        this.description = description;
        this.imageUrls = imageUrls;
    }
}
