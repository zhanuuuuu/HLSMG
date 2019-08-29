package com.hlyf.smg.Singleton;

import com.alibaba.fastjson.JSON;
import com.hlyf.smg.service.impl.MiniServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018-05-31.
 */
public class MyTocken {
    private static final Logger log= LoggerFactory.getLogger(MyTocken.class);

    private static MyTocken instance;
    private String Tocken;
    private Long second;
    public MyTocken(String tocken, Long second) {
        System.out.println("MyTocken has loaded");
        Tocken = tocken;
        this.second = second;
    }

    public static MyTocken getInstance(){
        if(instance==null){
            synchronized (MyTocken.class){
                if(instance==null){
                    instance=new MyTocken("",System.currentTimeMillis());
                }
            }
        }
        return instance;
    }

    public String getTocken() {
        return Tocken;
    }

    public void setTocken(String tocken) {
        Tocken = tocken;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "MyTocken{" +
                "Tocken='" + Tocken + '\'' +
                ", second=" + second +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {

        getMyTocken();

    }

    public static String getMyTocken(){
        MyTocken myTocken=  MyTocken.getInstance();
        long startTime = System.currentTimeMillis();
        double timeDouble= Double.parseDouble(Long.toString(System.currentTimeMillis()-myTocken.getSecond()));
        if(timeDouble>7000){
            System.out.println(myTocken.getSecond()+"我是大于7000秒获取来的");
            //这里 写你记得代码
            myTocken.setSecond(startTime);
        }else{
            myTocken.setSecond(startTime);
            //这里 写你记得代码
            System.out.println(myTocken.getSecond()+"我是小于7000秒获取来的");
        }

        return ""+timeDouble;
    }

    //这个用到了单利
    public static String  getSingletonTocken(RestTemplate restTemplate, String appid, String appsecret){
        MyTocken myTocken=  MyTocken.getInstance();
        String result ="";
        if(System.currentTimeMillis()>(myTocken.getSecond()+5000000)|| myTocken.getTocken().trim().equals("")){
            try{
                log.info("我是2个小时以内的数据");
                result= restTemplate.getForObject(
                        String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                                appid,appsecret),
                        String.class);
                log.info("我是获取到的数据 {}",result);
                JSONObject jsonObject=new JSONObject(result);
                myTocken.setTocken(jsonObject.getString("access_token"));
                myTocken.setSecond(System.currentTimeMillis());
            }catch (Exception e) {
                try{
                    log.info("我是2个小时以内输出的数据  这里出错了 重新获取一次");
                    result = restTemplate.getForObject(
                            String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                                    appid,appsecret),
                            String.class);
                    log.info("我是获取到的数据 {}",result);
                    JSONObject jsonObject=new JSONObject(result);
                    myTocken.setTocken(jsonObject.getString("access_token"));
                    myTocken.setSecond(System.currentTimeMillis());
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return myTocken.getTocken();
    }
}
