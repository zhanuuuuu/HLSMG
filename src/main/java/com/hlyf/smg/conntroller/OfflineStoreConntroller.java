package com.hlyf.smg.conntroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGStoreLocationMapper;
import com.hlyf.smg.domin.OfflineStore;
import com.hlyf.smg.domin.SMGStoreLocation;
import com.hlyf.smg.service.OfflineStoreService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.hlyf.smg.result.ResultMsg.ResultMsgEmpty;
import static com.hlyf.smg.result.ResultMsg.ResultMsgError;
import static com.hlyf.smg.result.ResultMsg.ResultMsgSuccess;

/**
 * Created by Administrator on 2019-08-07.
 */
@RestController
@RequestMapping("/hlyf")
@Api(value = "API - OfflineStoreConntroller", description = "线下线上门店类Api ")
public class OfflineStoreConntroller {
    private static final Logger logger= LoggerFactory.getLogger(OfflineStoreConntroller.class);

    @Autowired
    private OfflineStoreService offlineStoreService;


    @ApiOperation(value="扫码购获取线下门店列表", notes="扫码购获取线下门店列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cStoreNo", value = "",
                    paramType ="query" ,required = false,dataType = "string",defaultValue = "改参数可上传也可不上传"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/getAllOfflineStores", method = RequestMethod.POST)
    @ResponseBody
    public  String getAllOfflineStores(@RequestParam(value = "cStoreNo",required = false,defaultValue = "") String cStoreNo
                                      ){

        String response=ResultMsgEmpty();
        try{
            List<OfflineStore> offlineStores=this.offlineStoreService.getAllOfflineStoreS();
            if(offlineStores!=null){
                response=ResultMsgSuccess(JSONObject.toJSONString(offlineStores,
                                        SerializerFeature.WriteMapNullValue,
                                        SerializerFeature.WriteNullListAsEmpty));
            }else {
                response=ResultMsgEmpty();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取线下门店列表出错");
            response=ResultMsgError();
        }
     return response;

    }

    @ApiOperation(value="根据编号得到门店信息", notes="根据编号得到门店信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "lineId",
                    paramType ="query" ,required = false,dataType = "long",defaultValue = "0"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/getOnlineStores", method = RequestMethod.POST)
    @ResponseBody
    public  String getOnlineStores(@RequestParam(value = "lineId",required = false) Long lineId
    ){
        String response=ResultMsgEmpty();
        try{
            List<SMGStoreLocation> Stores=this.offlineStoreService.selectAllS(lineId);
            if(Stores!=null && Stores.size()>0){
                response=ResultMsgSuccess(JSONObject.toJSONString(Stores,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteNullListAsEmpty));
            }else {
                response=ResultMsgEmpty();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取线上门店列表出错");
            response=ResultMsgError();
        }
        return response;

    }

    @ApiOperation(value="增加门店", notes="增加门店")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/addOnlineStore", method = RequestMethod.POST)
    @ResponseBody
    public  String addOnlineStore(@RequestParam(value = "openId",required = true) String openId,
                                  @RequestParam(value = "unionId",required = true) String unionId,
                                  @RequestParam(value = "province",required = true) String province,
                                  @RequestParam(value = "city",required = true) String city,
                                  @RequestParam(value = "area",required = true) String area,
                                  @RequestParam(value = "location",required = true) String location,
                                  @RequestParam(value = "storeId",required = true) String storeId,
                                  @RequestParam(value = "storeName",required = true) String storeName,
                                  @RequestParam(value = "longitude",required = true) String longitude,
                                  @RequestParam(value = "latitude",required = true) String latitude,
                                  @RequestParam(value = "limitNumber",required = true) int limitNumber
    ){

        return this.offlineStoreService.addSMGStoreLocation(
                new SMGStoreLocation( openId,  unionId,  province,  city,  location,
                 storeId,  storeName,  longitude,  latitude,  limitNumber, area));

    }

    @ApiOperation(value="修改门店", notes="修改门店")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "/api/updateOnlineStore", method = RequestMethod.POST)
    @ResponseBody
    public  String updateOnlineStore(@RequestParam(value = "openId",required = true) String openId,
                                  @RequestParam(value = "unionId",required = true) String unionId,
                                  @RequestParam(value = "province",required = true) String province,
                                  @RequestParam(value = "city",required = true) String city,
                                  @RequestParam(value = "area",required = true) String area,
                                  @RequestParam(value = "location",required = true) String location,
                                  @RequestParam(value = "longitude",required = true) String longitude,
                                  @RequestParam(value = "latitude",required = true) String latitude,
                                  @RequestParam(value = "limitNumber",required = true) int limitNumber,
                                     @RequestParam(value = "lineId",required = false) Long lineId
    ){
        SMGStoreLocation smgStoreLocation= new SMGStoreLocation( openId,  unionId,  province,  city,  location,
                null,  null,  longitude,  latitude,  limitNumber, area);
        smgStoreLocation.setLineId(lineId);
        smgStoreLocation.setUpdateTime(new Date());
        return this.offlineStoreService.updateSMGStoreLocation(smgStoreLocation);

    }

}
