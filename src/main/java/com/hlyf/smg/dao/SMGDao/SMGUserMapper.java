package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.SMGUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019-08-15.
 */
@Mapper
public interface SMGUserMapper {

    int deleteByPrimaryKey(@Param("lineId") Long lineId, @Param("openId") String openId);

    int insert(SMGUser record);

    SMGUser selectByPrimaryKey(@Param("openId") String openId);

    List<SMGUser> selectAll();

    int updateByPrimaryKey(SMGUser record);
}
