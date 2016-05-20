package com.znblog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.znblog.model2json.Person;

/**
 * Created by hzqiuxm on 2016/2/25 0025.
 */
public class test3 {
    public static void main(String[] args) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Person person1 = new Person();
        Person person2 = new Person();

        person1.setName("菡萏如佳人");
        person1.setAge(30);
        person1.putValue("email","hzqiuxm@163.com");
        person2.setName("临江仙");
        person2.setAge(25);
        person2.putValue("email","qxmanddjh@163.com");

        String strs = gson.toJson(new Person[]{person1,person2});
        System.out.println(strs);

        Person[] persons = gson.fromJson(strs,Person[].class);
        for(Person p :persons){
            System.out.println(p);
        }
    }
}
