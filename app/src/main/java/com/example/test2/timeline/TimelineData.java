package com.example.test2.timeline;

import android.net.Uri;

public class TimelineData {

    private int image;
    private String info1;
    private String info2;
    private String price;

    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }

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

    public TimelineData(int image, String info1, String info2, String price) {
        this.image = image;
        this.info1 = info1;
        this.info2 = info2;
        this.price = price;
    }
}
