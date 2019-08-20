package com.hlyf.smg.conntroller;

import com.alibaba.fastjson.JSONObject;
import com.hlyf.smg.service.OfflineStoreService;
import com.hlyf.smg.service.SmgService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2019-08-15.
 */
@RestController
@RequestMapping("/good")
@Api(value = "API - SmgConntroller", description = "商品购物车控制类 ")
public class SmgConntroller {
    private static final Logger log= LoggerFactory.getLogger(SmgConntroller.class);

    @Autowired
    private SmgService smgService;

    /**
     * <pre>
     *      请求统一处理配置
     * </pre>
     * @param urlType    路径
     * @param jsonParam  参数json数据
     * @return
     */
    @RequestMapping(value = "/api/{urlType}", method = {RequestMethod.POST}) //, produces = "application/json;charset=UTF-8"
    public String getUrlType(@PathVariable String urlType,
                             @RequestBody JSONObject jsonParam) {
        log.info("我是请求路径 getUrlType urlType {}",urlType);
        log.info("我是请求路径 jsonParam urlType {}",JSONObject.toJSONString(jsonParam));

        return this.smgService.CommUrlFun(urlType,jsonParam);
    }
}
