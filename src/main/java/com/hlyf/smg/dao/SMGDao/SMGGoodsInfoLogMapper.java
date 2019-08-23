package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.Request;
import com.hlyf.smg.domin.SMGGoodsInfo;
import com.hlyf.smg.domin.SaleLogs;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019-08-16.
 */
@Mapper
public interface SMGGoodsInfoLogMapper {

    //查询待出厂的订单
    List<SaleLogs> p_getSmgOrdersLog(Request request);

    //方法封装
    List<SMGGoodsInfo> getSMGGoodsInfoBySMGGoodsInfo(SMGGoodsInfo smgGoodsInfo);

}
