package com.znblog.model2json;

import com.google.gson.annotations.Since;

/**
 * Created by hzqiuxm on 2016/2/25 0025.
 */
public class Contact {
    @Since(1.1)
    private String email;

    @Since(1.1)
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
