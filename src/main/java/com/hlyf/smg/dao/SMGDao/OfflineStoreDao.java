package com.hlyf.smg.dao.SMGDao;

import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.exception.ApiSysException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019-08-07.
 */
@Mapper
public interface OfflineStoreDao {
    List<OfflineStore> getAllOfflineStore() throws ApiSysException;
}
