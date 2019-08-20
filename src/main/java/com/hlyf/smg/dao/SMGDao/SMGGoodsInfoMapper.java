package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.SMGGoodsInfo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019-08-16.
 */
@Mapper
public interface SMGGoodsInfoMapper {

    int deleteByPrimaryKey(Long lineId);

    int insert(SMGGoodsInfo record);

    SMGGoodsInfo selectByPrimaryKey(Long lineId);

    List<SMGGoodsInfo> selectAll();

    int updateByPrimaryKey(SMGGoodsInfo record);

    //这个主要是获取单号的
    SMGGoodsInfo selectOneByOpendIdAndOrderStatus(@Param("openId") String openId);

    List<SMGGoodsInfo> selectAllByOpendIdAndOrderStatus(@Param("openId") String openId);

    int updateByBarcodeAndopenIdAndOrderStatus(SMGGoodsInfo record);

    int deleteSMGGoodsInfo(SMGGoodsInfo record);

    int deleteSMGGoodsInfoBybarcodeAndopenId(SMGGoodsInfo record);

}
