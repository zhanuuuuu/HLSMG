package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.WxEntity.SMGFormIds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019-08-27.
 */
@Mapper
public interface SMGFormIdsMapper {

    int deleteByPrimaryKey(@Param("lineId") Long lineId);

    int insert(SMGFormIds record);

    SMGFormIds selectByPrimaryKey(@Param("openId") String openId);

    List<SMGFormIds> selectAll();

    int updateByPrimaryKey(SMGFormIds record);
}
