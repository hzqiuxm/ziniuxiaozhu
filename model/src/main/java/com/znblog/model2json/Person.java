package com.znblog.model2json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzqiuxm on 2016/2/25 0025.
 */
public class Person {

    //假设接口参数要的是username
    @SerializedName("username")
    @Expose
    private String name;
    //假设年龄参数接口中不需要
    //序列化的时候不需要，反序列化的时候需要
    @Expose(serialize = false,deserialize = true)
    private int age;
    @Expose
    private Map<String,Object> contact;

    public Person(){
        contact = new HashMap<String, Object>();
    }

    public void  putValue(String key, Object value){
        contact.put(key, value);
    }

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

    public Map<String, Object> getContact() {
        return contact;
    }

    public void setContact(Map<String, Object> contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", contact=" + contact +
                '}';
    }
}
