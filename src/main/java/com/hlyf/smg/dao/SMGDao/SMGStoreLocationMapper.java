package com.hlyf.smg.dao.SMGDao;



import com.hlyf.smg.domin.SMGStoreLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SMGStoreLocationMapper {
    int deleteByPrimaryKey(Long lineId);

    int insert(SMGStoreLocation record);

    SMGStoreLocation selectByPrimaryKey(Long lineId);

    SMGStoreLocation selectByStoreId(@Param("storeId") String storeId);

    List<SMGStoreLocation> selectAll(@Param("lineId") Long lineId);

    int updateByPrimaryKey(SMGStoreLocation record);
}