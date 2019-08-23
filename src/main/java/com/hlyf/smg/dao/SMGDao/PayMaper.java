package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.payentity.SMGPayConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019-08-21.
 */
@Mapper
public interface PayMaper {

    SMGPayConfig selectByPrimaryKey(@Param("storeId") String storeId);

}
