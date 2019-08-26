package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.SMGProblems;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019-08-26.
 */
@Mapper
public interface SMGProblemsMapper {
    int deleteByPrimaryKey(@Param("lineId") Long lineId, @Param("openId") String openId);

    int insert(SMGProblems record);

    SMGProblems selectByPrimaryKey(@Param("createTime") String createTime, @Param("openId") String openId);

    List<SMGProblems> selectAll();

    int updateByPrimaryKey(SMGProblems record);
}
