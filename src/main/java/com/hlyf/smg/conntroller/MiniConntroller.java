package com.hlyf.smg.conntroller;

import com.hlyf.smg.config.SMGConnfig;
import com.hlyf.smg.service.MiniService;
import com.hlyf.smg.service.OfflineStoreService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.hlyf.smg.result.ResultMsg.ResultMsgError;
import static com.hlyf.smg.result.ResultMsg.ResultMsgSuccess;

/**
 * Created by Administrator on 2019-08-14.
 */
@RestController
@RequestMapping("/mini")
@Api(value = "API - OfflineStoreConntroller", description = "线下线上门店类Api ")
public class MiniConntroller {

    private static final Logger log= LoggerFactory.getLogger(MiniConntroller.class);

    @Autowired
    private MiniService miniService;

    @Autowired
    private SMGConnfig smgConnfig;

    @ApiOperation(value="根据code,appid，appsecret 获取openid", notes="根据code,appid，appsecret获取openid,或者获取 unionid（如果用户有关注了该小程序同主体的公众号 ）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",
                    value = "code",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "appid",
                    value = "appid",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = ""),
            @ApiImplicitParam(name = "appsecret",
                    value = "appsecret",
                    paramType ="query" ,required = true,dataType = "string",defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "api/wxuser/getOpenidOrUnionid", method = RequestMethod.POST)
    @ResponseBody
    public  String getOpenidOrUnionid(@RequestParam(value = "code",required = true) String code,
                                      @RequestParam(value = "appid",required = true) String appid,
                                      @RequestParam(value = "appsecret",required = true) String appsecret){
        String result="";
        try {
            result=this.miniService.GetOpenIOrUniuiddByCode(code,appid,appsecret);
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName()+"出错了 api/sns/jscode2session :{}",e.getMessage());
            e.printStackTrace();
            return ResultMsgError();
        }
        return result;
    }

    @ApiOperation(value="解密 根据session_key(通过上一步获取openid得到的,微信文档说五分钟过期),vi,encryptedData解密出来unionid或者用户手机号", notes="根据session_key,vi,encryptedData解密出来unionid或者用户手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId", paramType ="query" ,required = true,dataType = "string"),
            @ApiImplicitParam(name = "encryptedData", value = "encryptedData", paramType ="query" ,required = true,dataType = "string"),
            @ApiImplicitParam(name = "sessionKey", value = "sessionKey", paramType ="query" ,required = true,dataType = "string"),
            @ApiImplicitParam(name = "iv", value = "iv", paramType ="query" ,required = true,dataType = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})
    @RequestMapping(value = "api/wxuser/decrypt", method = RequestMethod.POST)
    @ResponseBody
    public  String decrypt(@RequestParam(value = "openId",required = true) String openId,
                           @RequestParam(value = "encryptedData",required = true) String encryptedData,
                           @RequestParam(value = "sessionKey",required = true) String sessionKey,
                           @RequestParam(value = "iv",required = true) String iv){
        String result="";
        try {
            result=this.miniService.Wxdecrypt(openId,encryptedData,sessionKey,iv);
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName()+"出错了 pi/wxuser/decrypt :{}",e.getMessage());
            e.printStackTrace();
            return ResultMsgError();
        }
        return result;
    }

    @ApiOperation(value="问题反馈接口", notes="问题反馈接口 " +
            " 备注 ： 隐含的上传条件 这里文件的名为 fileName")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成",reference="77777",responseContainer="8888888"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 403, message = "服务器拒绝请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")})

    @RequestMapping(value = "/api/commitproblem", method = {RequestMethod.POST})
    @ResponseBody
    public  String commitproblem(
            @RequestParam(value = "openId",required = true) String openId,
            @RequestParam(value = "unionId",required = true) String unionId,
            @RequestParam(value = "userTel",required = true) String userTel,
            @RequestParam(value = "problemType",required = true) String problemType,
            @RequestParam(value = "description",required = true) String description,
            HttpServletRequest request){

        log.info("获取到的数据  opendId:{},unionId:{},userTel:{},problemType:{},description",
                openId,unionId,userTel,problemType,description);
        List<MultipartFile> files = null;
        try{
            files =((MultipartHttpServletRequest)request).getFiles("fileName");
//TODO 判断是否上传图片文件
//            if(files.isEmpty()){
//                return ResultMsgError();
//            }
        }catch (Exception e){
            log.info("解析出错了",e.getMessage());
            e.printStackTrace();
            return ResultMsgError();
        }


        List<String> urlImages=new ArrayList<>();
        String imageUrl=request.getSession().getServletContext().getRealPath("")
                .replace("HLSMG","SMGImages");

        if(smgConnfig.getIsuseimageurl()){
            imageUrl=smgConnfig.getImageurl();
        }
        File file1 = new File(request.getSession().getServletContext().getRealPath("")
                .replace("HLSMG","SMGImages"));
        if(!file1.exists()){
            file1.mkdirs();
        }
        String urlTrue="";
        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);
            if(!file.isEmpty()){
                //从命名
                String imageType=fileName.substring(fileName.indexOf("."),fileName.length());
                String fileNameTrue=System.currentTimeMillis()+imageType;
                fileNameTrue=fileNameTrue.replaceAll(",","");
                urlTrue=imageUrl + "/" + fileNameTrue;
                System.out.println(urlTrue);
                File dest = new File(urlTrue);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                    urlImages.add(fileNameTrue);
                }catch (Exception e) {
                    e.printStackTrace();
                    return ResultMsgError();
                }
            }
        }

        this.miniService.insertProblems(openId,unionId,userTel,problemType,description,urlImages);
        return ResultMsgSuccess("");
    }




}
