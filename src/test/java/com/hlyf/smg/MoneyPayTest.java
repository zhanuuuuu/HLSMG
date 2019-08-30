package com.hlyf.smg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlyf.smg.config.DlbPayConnfig;
import com.hlyf.smg.dao.SMGDao.PayMaper;
import com.hlyf.smg.dao.SMGDao.PosMainMapper;
import com.hlyf.smg.domin.Request;
import com.hlyf.smg.domin.payentity.OrderPay;
import com.hlyf.smg.domin.payentity.SMGPayConfig;
import com.hlyf.smg.domin.payentity.SweepOrder;
import com.hlyf.smg.exception.ApiSysException;
import com.hlyf.smg.tool.RequestFacotry;
import com.hlyf.smg.tool.SHA1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.hlyf.smg.tool.String_Tool.getTimeUnix;

/**
 * Created by Administrator on 2019-08-21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneyPayTest {

    private static final Logger log= LoggerFactory.getLogger(PosMainMapperTest.class);

    @Autowired
    private DlbPayConnfig dlbPayConnfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PayMaper payMaper;

    @Test
    public void testSMGPayConfig(){
        SMGPayConfig smgPayConfig=this.payMaper.selectByPrimaryKey("0002");
        if(smgPayConfig!=null){
            log.info("我是获取到的数据  {}", JSON.toJSONString(smgPayConfig));
        }else {
            log.info("我是获取到的数据  空的");
        }

    }
    @Test
    public void testPay(){


        SMGPayConfig dlpPayConfigEntity=this.payMaper.selectByPrimaryKey("0002");
        if(dlpPayConfigEntity==null){
            log.info("dlpPayConfigEntity {}","查询出来的dlb支付配置为空");
            log.error("dlpPayConfigEntity {}","查询出来的dlb支付配置为空");
            return;
        }
        //重新赋值
        dlbPayConnfig.setAccesskey(dlpPayConfigEntity.getAccesskey());
        dlbPayConnfig.setSecretkey(dlpPayConfigEntity.getSecretkey());
        dlbPayConnfig.setAgentnum(dlpPayConfigEntity.getAgentnum());
        dlbPayConnfig.setCustomernum(dlpPayConfigEntity.getCustomernum());
        dlbPayConnfig.setMachinenum(dlpPayConfigEntity.getMachinenum());
        dlbPayConnfig.setShopnum(dlpPayConfigEntity.getShopnum());

        String timeUnix=getTimeUnix();

        String authCode="134644544715709674";
        String requestNum="13";
//        SweepOrder(String agentNum, String customerNum, String authCode, String machineNum, String shopNum,
//                String requestNum, String amount, String source, String tableNum)
        SweepOrder sweepOrder=new SweepOrder(dlbPayConnfig.getAgentnum(),dlbPayConnfig.getCustomernum(),authCode,
                null,dlbPayConnfig.getShopnum(),requestNum,"0.01","API",null);
//        OrderPay(String agentNum, String customerNum, String shopNum, String requestNum, String amount, String bankType,
//                String authId, String callbackUrl,  String extraInfo, String subscribeRoute)
        Request request=new Request();
        request.setUserlineId(3);
        request.setOpenId("o_gYO5JnA-34K65x_kcxi25j4gMc");
        request.setAmount("0.01");
        request.setPosId("88");

        try {
            requestNum= RequestFacotry.getOrderId(request.getPosId(),request.getUserlineId());
            log.info("单号是 {}",requestNum);
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        OrderPay orderPay=new OrderPay(dlbPayConnfig.getAgentnum(),dlbPayConnfig.getCustomernum(),dlbPayConnfig.getShopnum(),
                requestNum,request.getAmount(),"WX",request.getOpenId(),"https://www.duolabao.com",null,null);
        String body= JSONObject.toJSONString(orderPay);
        log.info("我是请求体携带的数据 {}",body);
        String url = "https://openapi.duolabao.com/v1/agent/order/pay/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accessKey",dlbPayConnfig.getAccesskey());
        headers.set("timestamp",timeUnix);
        String sign="secretKey="+dlbPayConnfig.getSecretkey()+"&timestamp="+timeUnix +
                "&path=/v1/agent/order/pay/create&body="+body;
        log.info("带签名的字符串 {}",sign);
        sign= SHA1.encode(sign);
        log.info("签名后的字符串 {}",sign);
        headers.set("token",sign.toUpperCase());

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        String result = responseEntity.getBody();

        log.info("我是拿到的返回结果 {}",result);

    }

    @Test
    public void testqueryPay(){
//        DlpPayConfigEntity dlpPayConfigEntity=this.dlpPayConfigEntityMapper.selectByPrimaryKey("0002",null,null);
//        if(dlpPayConfigEntity==null){
//            log.info("dlpPayConfigEntity {}","查询出来的dlb支付配置为空");
//            log.error("dlpPayConfigEntity {}","查询出来的dlb支付配置为空");
//            return;
//        }
//        //重新赋值
//        dlbPayConnfig.setAccesskey(dlpPayConfigEntity.getAccesskey());
//        dlbPayConnfig.setSecretkey(dlpPayConfigEntity.getSecretkey());
//        dlbPayConnfig.setAgentnum(dlpPayConfigEntity.getAgentnum());
//        dlbPayConnfig.setCustomernum(dlpPayConfigEntity.getCustomernum());
//        dlbPayConnfig.setMachinenum(dlpPayConfigEntity.getMachinenum());
//        dlbPayConnfig.setShopnum(dlpPayConfigEntity.getShopnum());
        String timeUnix=getTimeUnix();
        String requestNum="201908091125514010010654469531";
        String urlAfter="/v1/agent/order/payresult/"
                +dlbPayConnfig.getAgentnum()+"/"
                +dlbPayConnfig.getCustomernum()+"/"
                +dlbPayConnfig.getShopnum()+"/"+requestNum;
        String url = "https://openapi.duolabao.com"+urlAfter;
        HttpHeaders headers = new HttpHeaders();
        headers.set("accessKey",dlbPayConnfig.getAccesskey());
        headers.set("timestamp",timeUnix);

        String sign="secretKey="+dlbPayConnfig.getSecretkey()+"&timestamp="+timeUnix +
                "&path="+urlAfter;
        log.info("带签名的字符串 {}",sign);
        sign= SHA1.encode(sign).toUpperCase();
        log.info("签名后的字符串 {}",sign);
        headers.set("token",sign);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("agentNum", dlbPayConnfig.getAgentnum());
        requestBody.add("customerNum", dlbPayConnfig.getCustomernum());
        requestBody.add("shopNum", dlbPayConnfig.getShopnum());
        requestBody.add("requestNum", requestNum);

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, headers);

        ResponseEntity<String> responseEntity = null;

        //responseEntity =  restTemplate.getForEntity(url, String.class,requestEntity);

        responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class,
                requestEntity);

        String result = responseEntity.getBody();
        log.info("我是拿到的返回结果 {}",result);

    }

    @Test
    public void testReturnPay(){

        String string = "88201908290913140000000003604803,\n" +
                "88201908291529550000000003471159,\n" +
                "88201908291809060000000003260509,\n" +
                "88201908291812440000000003740428,\n" +
                "88201908291814560000000003277146,\n" +
                "88201908301028040000000003920926,\n" +
                "88201908301031360000000003323136,\n" +
                "88201908301127130000000003585267,\n" +
                "88201908301133360000000003542805,\n" +
                "88201908301134570000000003528976,\n" +
                "88201908301135490000000003582620,\n" +
                "88201908301606170000000006948096,\n" +
                "88201908301609430000000006533750,\n" +
                "88201908301616580000000006302528,\n" +
                "88201908301617090000000007426407,\n" +
                "88201908301640050000000006830081,\n";
        String timeUnix=getTimeUnix();
        String authCode=null;
        String requestNum="88201908301640270000000007325064";
//        SweepOrder(String agentNum, String customerNum, String authCode, String machineNum, String shopNum,
//                String requestNum, String amount, String source, String tableNum)
        SweepOrder sweepOrder=new SweepOrder(dlbPayConnfig.getAgentnum(),dlbPayConnfig.getCustomernum(),authCode,
                null,dlbPayConnfig.getShopnum(),requestNum,null,null,null);
        String body=JSONObject.toJSONString(sweepOrder);
        log.info("我是请求体携带的数据 {}",body);
        String url = "https://openapi.duolabao.com/v1/agent/order/refund";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accessKey",dlbPayConnfig.getAccesskey());
        headers.set("timestamp",timeUnix);
        String sign="secretKey="+dlbPayConnfig.getSecretkey()+"&timestamp="+timeUnix +
                "&path=/v1/agent/order/refund&body="+body;
        log.info("带签名的字符串 {}",sign);
        sign= SHA1.encode(sign).toUpperCase();
        log.info("签名后的字符串 {}",sign);
        headers.set("token",sign);

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        String result = responseEntity.getBody();

        log.info("我是拿到的返回结果 {}",result);

    }


}
