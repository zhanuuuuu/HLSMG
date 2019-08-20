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
     *     取消订单
     * </pre>
     */
    String cancleOrder="cancleOrder";
    /**
     * <pre>
     *     支付下单
     * </pre>
     */
    String payOrder="payOrder";

    /**
     * <pre>
     *     支付下单
     * </pre>
     */
    String queryPayOrder="queryPayOrder";

    /**
     * <pre>
     *     支付下单
     * </pre>
     */
    String canclePayOrder="canclePayOrder";

    /**
     * <pre>
     *     订单同步
     * </pre>
     */
    String orderSysn="orderSysn";
}
