package com.hlyf.smg.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.OfflineStoreDao;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.exception.ErrorEnum;
import com.hlyf.smg.result.ResultMsg;
import com.hlyf.smg.service.OfflineStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hlyf.smg.result.ResultMsg.ResultMsgEmpty;
import static com.hlyf.smg.result.ResultMsg.ResultMsgError;
import static com.hlyf.smg.result.ResultMsg.ResultMsgSuccess;

/**
 * Created by Administrator on 2019-08-07.
 */
@Service
public class OfflineStoreServiceImpl implements OfflineStoreService {

    private static final Logger log= LoggerFactory.getLogger(OfflineStoreServiceImpl.class);

    @Autowired
    private OfflineStoreDao offlineStoreDao;

    /**
     * 线上的操作类
     */
    @Autowired
    private SMGStoreLocationMapper storeLocationMapper;

    @Override
    public List<OfflineStore> getAllOfflineStoreS()  throws ApiSysException {
        return offlineStoreDao.getAllOfflineStore();
    }

    @Override
    public List<SMGStoreLocation> selectAllS(Long lineId) {
        return storeLocationMapper.selectAll(lineId);
    }

    @Override
    public String addSMGStoreLocation(SMGStoreLocation smgStoreLocation) {
        String response="";
        SMGStoreLocation storeLocation=null;
        try{
            storeLocation=this.storeLocationMapper.selectByStoreId(smgStoreLocation.getStoreId());
            if(storeLocation!=null){
                response= JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO002001.getCode(),
                                ErrorEnum.SSCO002001.getMesssage(),(String) ""),
                        SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty);
            }else{
                storeLocationMapper.insert(smgStoreLocation);
                List<SMGStoreLocation> Stores=this.storeLocationMapper.selectAll(null);
                if(Stores!=null && Stores.size()>0){
                    response=ResultMsgSuccess(JSONObject.toJSONString(Stores,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.WriteNullListAsEmpty));
                }else {
                    response=ResultMsgEmpty();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("新增门店信息失败了 {}",e.getMessage());
            response=ResultMsgError();
        }
        return response;
    }

    @Override
    public String updateSMGStoreLocation(SMGStoreLocation smgStoreLocation) {
        String response="";
        SMGStoreLocation storeLocation=null;
        try{
            storeLocationMapper.updateByPrimaryKey(smgStoreLocation);
            response=ResultMsgSuccess("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("更改门店失败了 {}",e.getMessage());
            response=ResultMsgError();
        }
        return response;
    }

}
