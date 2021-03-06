package com.hlyf.smg.exception;

/**
 * Created by Administrator on 2019-07-15.
 */
public enum  ErrorEnum implements ExceptionEnum {

    //系统相关(SSCO001)
    SUCCESS("000000", "请求成功"),
    SSCO001001("SSCO001001", "上传的参数有误"),
    //严重错误
    SSCO001002("SSCO001002", "系统处理错误"),
    //门店相关
    SSCO002001("SSCO002001", "该门店信息已经登记过了"),


    //会员相关(SSCO008)
    SSCO008001("SSCO008001", "会员登录失败,请重新输入"),
    SSCO008002("SSCO008002", "会员注册失败,请稍后重试"),
    SSCO008003("SSCO008003", "未注册会员"),
    SSCO008004("SSCO001001", "查询会员失败"),

    //收银机相关(SSCO010)
    SSCO010001("SSCO010001", "收银机不在线"),
    SSCO010002("SSCO010002", "设备名称不能为空"),
    SSCO010003("SSCO010003", "设备配置错误"),
    SSCO010004("SSCO010004", "无此商品信息"),
    SSCO010005("SSCO010005", "商品库存不足"),
    SSCO010006("SSCO010006", "门店信息有误"),
    SSCO010007("SSCO010007", "购物车信息不能为空"),
    SSCO010008("SSCO010008", "购物车中没有购买商品"),
    SSCO010009("SSCO010009", "生成二维码异常"),
    SSCO010010("SSCO010010", "二维码已过期"),
    SSCO010011("SSCO010011", "付款日限额超限"),
    SSCO010012("SSCO010012", "付款月额度超限"),
    SSCO010013("SSCO010013", "商品非售中"),
    SSCO010014("SSCO010014", "商品暂不支持自助结算，请到人工收银台结算"),
    SSCO010015("SSCO010015", "未定位到门店相关信息"),
    SSCO010016("SSCO010016", "商品已过保不可销售"),
    //自己定义
    SSCO006000("SSCO006000", "未找到相关数据"),

    //支付失败
    SSCO006001("SSCO006001", "支付遇到网络故障,稍后......"),
    SSCO006002("SSCO006002", "该订单已经支付或该订单不存在......"),
    SSCO006003("SSCO006003", "后台支付配置出错了,请联系管理员......"),

    SSCO006004("SSCO006004", "订单核销失败"),
    //订单相关(SSCO003)
    SSCO003000("SSCO003000", "订单创建失败"),
    SSCO005009("SSCO005009", "交易不存在");

    private String code;

    private String message;


    ErrorEnum(String code, String message) {
        this.code=code;
        this.message=message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMesssage() {
        return this.message;
    }
}
