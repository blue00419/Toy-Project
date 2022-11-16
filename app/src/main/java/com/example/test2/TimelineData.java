package com.example.test2;

public class TimelineData {

    private String info1;
    private String info2;
    private String price;

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public TimelineData(String info1, String info2, String price) {
        this.info1 = info1;
        this.info2 = info2;
        this.price = price;
    }
}
