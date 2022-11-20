package com.example.test2;

import com.google.gson.annotations.SerializedName;

public class Json_Test {
    private String email;

    @Override
    public String toString(){
        return "Json_Test{" +
                "email = " + email + "}";
    }

    public Json_Test(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
