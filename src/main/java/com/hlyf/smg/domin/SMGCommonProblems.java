package com.hlyf.smg.domin;

import com.alibaba.fastjson.annotation.JSONField;
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
public class SMGCommonProblems {

    private Long lineId;

    private String problemTitle;

    private String description;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
