package com.hlyf.smg.service;

import com.alibaba.fastjson.JSONObject;
import com.hlyf.smg.dao.SMGDao.SMGGoodsInfoMapper;
import com.hlyf.smg.domin.FrushGood;
import com.hlyf.smg.domin.Request;
import com.hlyf.smg.domin.cStoreGoods;
import com.hlyf.smg.exception.ApiSysException;

import java.util.List;

/**
 * Created by Administrator on 2019-08-15.
 */
public interface SmgService {

    String CommUrlFun(String urlType,JSONObject jsonParam);
    List<cStoreGoods> GetcStoreGoodsS(String cStoreNo, List<String> barcodeList) throws ApiSysException;

    //查询购物车的商品  每次都返回
    String SelectCart (Request request, String code, SMGGoodsInfoMapper smgGoodsInfoMapper);

    void SaveGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException;

    void UpdateGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException;

    void DeleteGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException;

    void ClearGoodsToCartInfo(Request request, List<cStoreGoods> cStoreGoodsList, FrushGood frushGood) throws ApiSysException;

    void pushOrder(String openid, String formid,String appid, String appsecret,String merchantOrderId);

}
