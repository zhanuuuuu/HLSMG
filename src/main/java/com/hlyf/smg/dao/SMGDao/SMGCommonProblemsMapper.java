package com.hlyf.smg.dao.SMGDao;



import com.hlyf.smg.domin.SMGCommonProblems;

import java.util.List;

public interface SMGCommonProblemsMapper {
    int deleteByPrimaryKey(Long lineId);

    int insert(SMGCommonProblems record);

    SMGCommonProblems selectByPrimaryKey(Long lineId);

    List<SMGCommonProblems> selectAll();

    int updateByPrimaryKey(SMGCommonProblems record);
}