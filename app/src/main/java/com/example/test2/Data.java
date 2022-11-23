package com.example.test2;

public class Data {
    private String nickname;
    private int code;
    private String msg;

    public Data(String nickname, int code, String msg) {
        this.nickname = nickname;
        this.code = code;
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
