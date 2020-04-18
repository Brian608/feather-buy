package org.feather.common.enums;


public enum TradeStatus {

    WAITING_PAY("等待支付",(byte)1),
    TRADE_CANCEL("订单取消",(byte)2),
    TRADE_PAIED("订单已支付",(byte)3),
    TRADE_CLOSE("订单关闭",(byte)4),
    ;

    TradeStatus(String desc, byte value) {
        this.desc = desc;
        this.value = value;
    }

    private String desc;

    private  byte value;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }




}
