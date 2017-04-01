package com.example.dev_n.ewaste.activity;

import java.util.ArrayList;

/**
 * Created by Dev_N on 25-03-2017.
 */

public class RequestData {

    private String ID;
    private String address;
    private String contactNo;
    private ArrayList<OrderData> orderItems;
    private String approxWeight;
    private String pickupTime;
    private String itemCOunt;

    public String getItemCOunt() {
        return itemCOunt;
    }

    public void setItemCOunt(String itemCOunt) {
        this.itemCOunt = itemCOunt;
    }

    public RequestData(String ID, String address, String approxWeight) {
        this.ID = ID;
        this.address = address;
        this.approxWeight = approxWeight;
    }

    public RequestData(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public ArrayList<OrderData> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderData> orderItems) {
        this.orderItems = orderItems;
    }

    public String getApproxWeight() {
        return approxWeight;
    }

    public void setApproxWeight(String approxWeight) {
        this.approxWeight = approxWeight;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
}
