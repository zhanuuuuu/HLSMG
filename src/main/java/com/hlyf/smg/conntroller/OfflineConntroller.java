package com.hlyf.smg.conntroller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.service.SmgService;
import com.hlyf.smg.tool.LocationUtils;
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
 * Created by Administrator on 2019-08-29.
 */
@RestController
@RequestMapping("/offline")
@Api(value = "API - OfflineConntroller", description = "线下查询订单核销订单 ")
public class OfflineConntroller {

    private static final Logger log= LoggerFactory.getLogger(OfflineConntroller.class);

    @Autowired
    private SmgService smgService;

    @ApiOperation(value="根据opendid和单号查询订单详情", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "merchantOrderId", value = "merchantOrderId",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "orderStatus", value = "orderStatus: 0 未支付  1 待出场  2 已完成  ",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "extraInfo", value = "扩展字段 可以不上传",
                    paramType ="query" ,required = false,dataType = "string",defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/getOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public  String getOrderDetail(
                                   @RequestParam(value = "openId",required = true) String openId,
                                   @RequestParam(value = "merchantOrderId",required = true) String merchantOrderId,
                                   @RequestParam(value = "orderStatus",required = true) String orderStatus,
                                   @RequestParam(value = "extraInfo",required = false) String extraInfo

    ){
        log.info("线下查询订单详情上传的数据 openId:{}, merchantOrderId:{}, extraInfo:{},orderStatus:{} "
                ,openId,merchantOrderId,extraInfo,orderStatus);
        return this.smgService.getselectOrdersDetail(openId, merchantOrderId,Integer.valueOf(orderStatus));
    }

    @ApiOperation(value="支付完成后核销订单", notes="根据opendid和单号,收银员编号,收银员名称 核销该订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "merchantOrderId", value = "订单号",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "payOrderId", value = "支付号",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "checkUpNo", value = "核销人员编号",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "checkUpName", value = "核销人员姓名",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "amount", value = "实际付款金额",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "storeId", value = "门店编号",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "extraInfo", value = "扩展字段 可以不上传",
                    paramType ="query" ,required = false,dataType = "string",defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/confirmOrder", method = RequestMethod.POST)
    @ResponseBody
    public  String confirmOrder(
            @RequestParam(value = "openId",required = true) String openId,
            @RequestParam(value = "merchantOrderId",required = true) String merchantOrderId,
            @RequestParam(value = "payOrderId",required = true) String payOrderId,
            @RequestParam(value = "checkUpNo",required = true) String checkUpNo,
            @RequestParam(value = "checkUpName",required = true) String checkUpName,
            @RequestParam(value = "amount",required = true) String amount,
            @RequestParam(value = "storeId",required = true) String storeId,
            @RequestParam(value = "extraInfo",required = false) String extraInfo
    ){
        log.info("线下核销上传的数据 openId:{}, merchantOrderId:{},checkUpNo:{},checkUpName:{},amount:{}, extraInfo:{} "
                ,openId,merchantOrderId,checkUpNo,checkUpName,amount,extraInfo);
        return this.smgService.confirmOrderS(openId,merchantOrderId,checkUpNo,checkUpName,amount,extraInfo,payOrderId,storeId);
    }
}
