package com.hlyf.smg.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGProblemsMapper;
import com.hlyf.smg.dao.SMGDao.SMGUserMapper;
import com.hlyf.smg.domin.SMGProblems;
import com.hlyf.smg.domin.SMGUser;
import com.hlyf.smg.service.MiniService;
import com.hlyf.smg.tool.AesCbcUtil;
import com.hlyf.smg.tool.String_Tool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.hlyf.smg.Singleton.MyTocken.getSingletonTocken;
import static com.hlyf.smg.result.ResultMsg.ResultMsgError;
import static com.hlyf.smg.result.ResultMsg.ResultMsgSuccess;

/**
 * Created by Administrator on 2019-08-14.
 */
@Service
public class MiniServiceImpl implements MiniService {

    private static final Logger log= LoggerFactory.getLogger(MiniServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SMGUserMapper userMapper;

    @Autowired
    private SMGProblemsMapper smgProblemsMapper;


    @Override
    public String GetOpenIOrUniuiddByCode(String code, String appid, String appsecret) {
        String resultString=ResultMsgError();
        try{
            if(StringUtils.isEmpty(code) || StringUtils.isEmpty(appid) || StringUtils.isEmpty(appsecret)){
                return resultString;
            }

//            String resultTocken=getSingletonTocken(restTemplate,appid,appsecret);
//            log.info("我是拿到的tocken {}",resultTocken);
            resultString=restTemplate.getForObject(
                    String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                            appid,appsecret,code),
                    String.class);
            log.info("我是访问微信拿到的数据 {} ",resultString);
             JSONObject jsonObject=JSONObject.parseObject(resultString);
            if(jsonObject.containsKey("openid")){
                String openid=jsonObject.getString("openid");
                String unionid=jsonObject.getString("unionid");
                String session_key=jsonObject.getString("session_key");
                SMGUser user=this.userMapper.selectByPrimaryKey(openid);
                if(user!=null){
                    user.setSession_key(session_key);
                    user.setUnionId(unionid);
                    String userJson=JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue);
                    resultString= ResultMsgSuccess(userJson);
                    //这里是更改unionid的
                    user=new SMGUser(openid,unionid);
                    this.userMapper.updateUnionIdByOpenId(user);
                    log.info("我是获取的数据 {}", userJson);
                }else {
                    try{
                        user=new SMGUser(openid,unionid);
                        this.userMapper.insert(user);
                        user=this.userMapper.selectByPrimaryKey(openid);
                        user.setSession_key(session_key);
                        String userJson=JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue);
                        resultString= ResultMsgSuccess(userJson);
                        log.info("我是获取的数据 {}", userJson);
                    }catch (Exception e){
                        e.printStackTrace();
                        log.error("GetOpenIOrUniuiddByCode 插入数据的时间 出错了 {}",e.getMessage());
                        resultString= ResultMsgError();
                    }
                }

            }
        }catch (Exception e){
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName()+"出错了 {}",e.getMessage());
            e.printStackTrace();
            resultString= ResultMsgError();
        }

        return resultString;
    }

    @Override
    public String Wxdecrypt(String openId,String encryptedData, String sessionKey, String iv) {
        String resultString= ResultMsgError();
        try {
            resultString = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            if (null != resultString && resultString.length() > 0) {
                if(JSONObject.parseObject(resultString).containsKey("phoneNumber")){
                    SMGUser  user=new SMGUser(openId,null,JSONObject.parseObject(resultString).getString("phoneNumber"));
                    this.userMapper.updateByPrimaryKey(user);
                    user=this.userMapper.selectByPrimaryKey(openId);
                    String userJson=JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue);
                    resultString= ResultMsgSuccess(userJson);
                }
            }
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName()+"出错了 {}",e.getMessage());
            e.printStackTrace();
            resultString= ResultMsgError();
        }
        return resultString;
    }

    @Override
    public void insertProblems(String openId, String unionId, String userTel, String problemType, String description, List<String> urlImages) {
        try{
            SMGProblems smgProblems=smgProblemsMapper.selectByPrimaryKey(
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()),openId);
            if(smgProblems==null){
                String imageUrls= String_Tool.listToString(urlImages);
                 smgProblems=new SMGProblems(openId,unionId,
                         userTel,problemType,description,imageUrls);
                int i=smgProblemsMapper.insert(smgProblems);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("insertProblems 出错了 {}",e.getMessage());
        }
    }
}
