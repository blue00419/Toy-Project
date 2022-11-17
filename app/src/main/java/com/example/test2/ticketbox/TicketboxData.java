package com.example.test2.ticketbox;

public class TicketboxData {
    private int image;
    private String ticketinfo1;
    private String tickectinfo2;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTicketinfo1() {
        return ticketinfo1;
    }

    public void setTicketinfo1(String ticketinfo1) {
        this.ticketinfo1 = ticketinfo1;
    }

    public String getTickectinfo2() {
        return tickectinfo2;
    }

    public void setTickectinfo2(String tickectinfo2) {
        this.tickectinfo2 = tickectinfo2;
    }

    public TicketboxData(int image, String ticketinfo1, String tickectinfo2) {
        this.image = image;
        this.ticketinfo1 = ticketinfo1;
        this.tickectinfo2 = tickectinfo2;
    }
}
