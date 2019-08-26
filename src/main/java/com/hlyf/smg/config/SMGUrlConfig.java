package com.hlyf.smg.config;


public interface SMGUrlConfig {

    /**
     * <pre>
     *     会员查询
     * </pre>
     */
    String selectMemberInfo="selectMemberInfo";
    /**
     * <pre>
     *     商品查询
     * </pre>
     */
    String selectGoods="selectGoods";
    /**
     * <pre>
     *     更改商品数量
     * </pre>
     */
    String updateGoods="updateGoods";
    /**
     * <pre>
     *     删除商品
     * </pre>
     */
    String deleteGoods="deleteGoods";
    /**
     * <pre>
     *     清空购物车
     * </pre>
     */
    String clearCartInfo="clearCartInfo";
    /**
     * <pre>
     *     提交购物车
     * </pre>
     */
    String commitCartInfo="commitCartInfo";
    /**
     * <pre>
     *     接收支付结果回传
     * </pre>
     */
    String acceptPayResultNotice="acceptPayResultNotice";
    /**
     * <pre>
     *     支付下单
     * </pre>
     */
    String payOrder="payOrder";


    /**
     * <pre>
     *     订单同步
     * </pre>
     */
    String orderSysn="orderSysn";

    /**
     * <pre>
     *     查询订单记录
     * </pre>
     */
    String selectOrders="selectOrders";

    /**
     * <pre>
     *     查询订单记录
     * </pre>
     */
    String selectOrdersDetail="selectOrdersDetail";

    /**
     * <pre>
     *     得到公共的问题
     * </pre>
     */
    String getCommonProblems="getCommonProblems";

}
