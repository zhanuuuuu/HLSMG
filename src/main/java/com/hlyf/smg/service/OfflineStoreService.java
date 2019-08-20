package com.hlyf.smg.service;

import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.exception.ApiSysException;

import java.util.List;

/**
 * Created by Administrator on 2019-08-07.
 */
public interface OfflineStoreService {

    List<OfflineStore> getAllOfflineStoreS() throws ApiSysException;

    List<SMGStoreLocation> selectAllS(Long lineId);

    String addSMGStoreLocation(SMGStoreLocation smgStoreLocation);

    String updateSMGStoreLocation(SMGStoreLocation smgStoreLocation);
}
