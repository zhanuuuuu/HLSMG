package com.hlyf.smg;

import com.alibaba.fastjson.JSONObject;
import com.hlyf.smg.dao.SMGDao.OfflineStoreDao;
import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.domin.Request;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.service.SmgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private static final Logger log= LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	private OfflineStoreDao offlineStoreDao;

	@Autowired
	private SmgService smgService;

	@Test
	public void testPushOrder() throws ApiSysException {

//
	}

	@Test
	public void contextLoads() throws ApiSysException {

		List<OfflineStore> offlineStores=offlineStoreDao.getAllOfflineStore();

		log.info("获取到的门店信息 {}", JSONObject.toJSON(offlineStores).toString());
	}

//	Request(String storeId, String unionId, String openId,
//			String userlineId, String posName, String posId, String barcode, String merchantOrderId)
    @Test
	public void testRequestJson(){
		Request request=new Request( "0002", "omLZQ0ZRIBpbYMkYJhyDy1w3xWyA", "o_gYO5JnA-34K65x_kcxi25j4gMc",
				3, "posstation101", "88", "6928804011128", "");
		request.setNum(1);
		request.setGoodlineId(1);
		log.info("我是请求参数集合 {}", JSONObject.toJSON(request).toString());
	}

}
