package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.preferentialGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019-08-21.
 */
@Mapper
public interface CommMapper {

    /**
     * <pre>
     *     把数据插入到临时表的过程  提交购物车  计算整单优惠信息以前
     * </pre>
     */
    Integer p_smgdatatoTemp(@Param("merchantOrderId")String merchantOrderId, @Param("openId")String openId,
                               @Param("cVipNo")String cVipNo, @Param("cPosID")String cPosID,
                               @Param("tableName")String tableName);



    List<preferentialGoods> get_preferentialGoods(@Param("cStoreNo")String cStoreNo, @Param("machineId")String machineId,
                                                  @Param("cSheetNo")String cSheetNo, @Param("vipNo")String vipNo,
                                                  @Param("fVipScoreRatio")String fVipScoreRatio, @Param("bDiscount")String bDiscount,
                                                  @Param("callName")String callName);
}
