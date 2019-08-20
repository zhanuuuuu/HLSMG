package com.hlyf.smg.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-08-15.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class posConfig {

    private String cID;

    private Integer iGoodsNoStart;

    private Integer iGoodsNoEnd;

    private Integer iMoneyStart;

    private Integer iMoneyEnd;

    private Double iRatio;

    private Boolean bMlt;

    private Boolean bDazhe;

    private String cCharID;

    private Integer iMoneyStart18;

    private Integer iMoneyEnd18;

    private Integer iWeightStart;

    private Integer iWeightEnd;

    private Integer iWeightStart18;

    private Integer iWeightEnd18;
}
