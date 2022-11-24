package com.example.test2;

import android.graphics.Bitmap;

public class TicketData {
    private String image1;
    private Bitmap image2;

    public TicketData(String image1, Bitmap image2) {
        this.image1 = image1;
        this.image2 = image2;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public Bitmap getImage2() {
        return image2;
    }

    public void setImage2(Bitmap image2) {
        this.image2 = image2;
    }
}
