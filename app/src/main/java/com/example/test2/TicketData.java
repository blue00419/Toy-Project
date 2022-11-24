package com.example.test2;

import android.graphics.Bitmap;

import okhttp3.MultipartBody;

public class TicketData {
    private String image1;
    private byte[] image2;
    private MultipartBody.Part imageData;

    public TicketData(String image1, byte[] image2, MultipartBody.Part imageData) {
        this.image1 = image1;
        this.image2 = image2;
        this.imageData = imageData;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }
}
