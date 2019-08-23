package com.hlyf.smg.dao.SMGDao;


import com.hlyf.smg.domin.payentity.SMGMoneySaleLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SMGMoneySaleLogMapper {
    int deleteByPrimaryKey(@Param("lineId") Long lineId, @Param("merchantOrderId") String merchantOrderId);

    int insert(SMGMoneySaleLog record);

    SMGMoneySaleLog selectByPrimaryKey(@Param("merchantOrderId") String merchantOrderId);

    List<SMGMoneySaleLog> selectAll();

    int updateByPrimaryKey(SMGMoneySaleLog record);
}