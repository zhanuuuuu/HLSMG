package com.hlyf.smg.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.dao.SMGDao.SMGUserMapper;
import com.hlyf.smg.domin.SMGUser;
import com.hlyf.smg.service.MiniService;
import com.hlyf.smg.tool.AesCbcUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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

    @Override
    public String GetOpenIOrUniuiddByCode(String code, String appid, String appsecret) {
        String resultString=ResultMsgError();
        try{
            if(StringUtils.isEmpty(code) || StringUtils.isEmpty(appid) || StringUtils.isEmpty(appsecret)){
                return resultString;
            }
            resultString=restTemplate.getForObject(
                    String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                            appid,appsecret,code),
                    String.class);
             JSONObject jsonObject=JSONObject.parseObject(resultString);
            if(jsonObject.containsKey("openid")){
                String openid=jsonObject.getString("openid");
                String unionid=jsonObject.getString("unionid");
                String session_key=jsonObject.getString("session_key");
                SMGUser user=this.userMapper.selectByPrimaryKey(openid);
                if(user!=null){
                    user.setSession_key(session_key);
                    String userJson=JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue);
                    resultString= ResultMsgSuccess(userJson);
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
}
