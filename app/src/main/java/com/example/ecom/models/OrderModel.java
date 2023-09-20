package com.example.ecom.models;

public class OrderModel {

    String oID;
    String pName;
    String pType;
    String pPrice;
    String buyerUID;

    public OrderModel() {
    }

    public OrderModel(String oID, String pName, String pType, String pPrice, String buyerUID) {
        this.oID = oID;
        this.pName = pName;
        this.pType = pType;
        this.pPrice = pPrice;
        this.buyerUID = buyerUID;
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getBuyerUID() {
        return buyerUID;
    }

    public void setBuyerUID(String buyerUID) {
        this.buyerUID = buyerUID;
    }
}
