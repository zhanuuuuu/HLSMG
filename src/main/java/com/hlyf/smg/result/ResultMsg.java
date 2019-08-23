package com.hlyf.smg.result;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.smg.exception.ErrorEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019-05-24.
 */

@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
@ToString
public class ResultMsg implements Serializable {

    private boolean success;
    private String retCode;
    private String retMessage;
    private Object data;

    public ResultMsg(boolean success, String retCode, String retMessage, Object data) {
        this.success = success;
        this.retCode = retCode;
        this.retMessage = retMessage;
        this.data = data;
    }

    /**
     * <pre>
     *     这里不能上传null
     * </pre>
     * @param data
     * @return
     */
    public static String ResultMsgSure(Object data){
        data=data==null ?"":data;
        return JSONObject.toJSONString(new ResultMsg(true, ErrorEnum.SUCCESS.getCode(),
                        ErrorEnum.SUCCESS.getMesssage(),data),
                SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty);
    }

    public static String ResultMsgSuccess(String data){

        return JSONObject.toJSONString(new ResultMsg(true, ErrorEnum.SUCCESS.getCode(),
                                                    ErrorEnum.SUCCESS.getMesssage(),(String) data),
                                       SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty);
    }

    public static String ResultMsgError(){
        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO001001.getCode(),
                        ErrorEnum.SSCO001001.getMesssage(),(String) ""));
    }

    public static String ResultMsgEmpty(){
        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO006000.getCode(),
                ErrorEnum.SSCO006000.getMesssage(),(String) ""));
    }

//    严重错误
//     error
    public static String ResultMsgSeriousError(){
        return JSONObject.toJSONString(new ResultMsg(false, ErrorEnum.SSCO001002.getCode(),
                ErrorEnum.SSCO001002.getMesssage(),(String) ""));
    }




    public static <T> String ResultMsgSuccess(T data){

        return "";
    }

    public static void main(String[] args) {
        ResultMsg resultMsg = new ResultMsg(true, ErrorEnum.SSCO001001.getCode(),ErrorEnum.SSCO001001.getMesssage(),(String)null);

        //resultMsg = new ResultMsg(true,"1001","正确",(ResultMsg)resultMsg);

        String jsonString = JSON.toJSONString(resultMsg);
        System.out.println(jsonString);
        resultMsg = new ResultMsg(true,"1001","正确",(ResultMsg)null);

        jsonString = JSONObject.toJSONString(resultMsg,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.PrettyFormat);
        System.out.println(jsonString);



    }

}
