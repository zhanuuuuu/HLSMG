package com.hlyf.smg.domin.payentity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-08-21.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class OrderPay {

    private String agentNum;
    private String customerNum;
    private String shopNum;
    private String requestNum;
    private String amount;
    private String bankType="WX";//WX
    private String authId;
    private String callbackUrl="https://www.duolabao.com";//
    private String extraInfo;
    private String subscribeRoute;

    public OrderPay(String agentNum, String customerNum, String shopNum, String requestNum, String amount, String bankType,
                    String authId, String callbackUrl,  String extraInfo, String subscribeRoute) {
        this.agentNum = agentNum;
        this.customerNum = customerNum;
        this.shopNum = shopNum;
        this.requestNum = requestNum;
        this.amount = amount;
        if(bankType!=null){
            this.bankType = bankType;
        }
        this.authId = authId;
        if(callbackUrl!=null){
            this.callbackUrl = callbackUrl;
        }
        this.extraInfo = extraInfo;
        this.subscribeRoute = subscribeRoute;
    }
}
