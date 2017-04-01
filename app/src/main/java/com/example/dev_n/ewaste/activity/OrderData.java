package com.example.dev_n.ewaste.activity;

/**
 * Created by Dev_N on 25-03-2017.
 */

public class OrderData {

    private String orderId;
    private String orderName;
    private String orderCount;
    private String orderType;


    public OrderData(String orderid, String ordercount, String ordername,String ordertype){
        this.orderId = orderid;
        this.orderCount = ordercount;
        this.orderName = ordername;
        this.orderType = ordertype;

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
