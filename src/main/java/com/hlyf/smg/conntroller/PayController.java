package com.hlyf.smg.conntroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.service.OfflineStoreService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hlyf.smg.result.ResultMsg.ResultMsgEmpty;
import static com.hlyf.smg.result.ResultMsg.ResultMsgError;
import static com.hlyf.smg.result.ResultMsg.ResultMsgSuccess;

/**
 * Created by Administrator on 2019-08-22.
 */
@RestController
@RequestMapping("/pay")
@Api(value = "API - OfflineStoreConntroller", description = "线下线上门店类Api ")
public class PayController {
    private static final Logger logger= LoggerFactory.getLogger(PayController.class);

    @Autowired
    private OfflineStoreService offlineStoreService;


    @ApiOperation(value="接收支付结果通知", notes="接收支付结果通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestNum", value = "单号",
                    paramType ="query" ,required = false,dataType = "string",defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/acceptPayResultNotice", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public  String getAllOfflineStores(
                                       @RequestParam(value = "requestNum",required = true) String requestNum,
                                       @RequestParam(value = "orderNum",required = true) String orderNum,
                                       @RequestParam(value = "orderAmount",required = true) String orderAmount,
                                       @RequestParam(value = "status",required = true) String status,
                                       @RequestParam(value = "completeTime",required = true) String completeTime,
                                       @RequestParam(value = "extraInfo",required = true) String extraInfo
    ){

        String response="success";
        try{
            logger.info("接收得到的数据是  requestNum:{}, orderNum:{}, orderAmount:{}" +
                    " status:{}, completeTime:{}, extraInfo:{} ",requestNum,orderNum,orderAmount,status,completeTime,extraInfo);
//        JSONObject extraInfo=new JSONObject();
//        extraInfo.put("payOrderId",requestNum);
//        extraInfo.put("opendId",request.getOpenId());
//        extraInfo.put("merchantOrderId",request.getMerchantOrderId());
            JSONObject jsonObject= JSON.parseObject(extraInfo);
            logger.info("解析出来的数据 extraInfo : {} ",JSONObject.toJSONString(jsonObject,SerializerFeature.PrettyFormat));
            String payOrderId=requestNum;
            String opendId=jsonObject.getString("opendId");
            String merchantOrderId=jsonObject.getString("merchantOrderId");

        }catch (Exception e){
            e.printStackTrace();
            logger.error("接收支付结果回传出问题了");
            response="success";
        }
        return response;

    }
}
