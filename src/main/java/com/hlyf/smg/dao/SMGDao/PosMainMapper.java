package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.cStoreGoods;
import com.hlyf.smg.domin.posConfig;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019-08-15.
 */
@Mapper
public interface PosMainMapper {

    //获取商品基本信息
    List<cStoreGoods> GetcStoreGoods(@Param("cStoreNo") String cStoreNo, @Param("barcodeList")List<String> barcodeList);

    //获取商品基本信息 单店
    List<cStoreGoods>  GetcStoreGoodsDanDian(@Param("cStoreNo") String cStoreNo,@Param("barcodeList")List<String> barcodeList);

    posConfig getposConfig(@Param("tableName")String tableName, @Param("cID")String cID);

}
