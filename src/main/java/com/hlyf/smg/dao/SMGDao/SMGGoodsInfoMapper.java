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

    //判断商品是否存在
    SMGGoodsInfo selectByOpendIdAndOrderStatusAndBarcode(@Param("openId") String openId,@Param("barcode") String barcode);

    List<SMGGoodsInfo> selectAllByOpendIdAndOrderStatus(@Param("openId") String openId);

    int updateByBarcodeAndopenIdAndOrderStatus(SMGGoodsInfo record);

    int deleteSMGGoodsInfo(SMGGoodsInfo record);

    int deleteSMGGoodsInfoBybarcodeAndopenId(SMGGoodsInfo record);

    //更改支付状态和支付方式和支付单号
    int updateOrderStatus(SMGGoodsInfo record);

    //查询待出厂的订单
    List<SMGGoodsInfo> selectLeaveSuperOrders(SMGGoodsInfo record);

    //方法封装
    List<SMGGoodsInfo> getSMGGoodsInfoBySMGGoodsInfo(SMGGoodsInfo smgGoodsInfo);

}
