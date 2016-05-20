package com.znblog.model2json;

import com.google.gson.annotations.Since;

/**
 * Created by hzqiuxm on 2016/2/25 0025.
 */
public class User {
    @Since(1.0) //版本号
    private String name ;
    @Since(1.1)
    private int age;
    @Since(1.1)
    private boolean admin;
    @Since(1.2)
    private Contact contact;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", admin=" + admin +
                ", contact=" + contact +
                '}';
    }
}
