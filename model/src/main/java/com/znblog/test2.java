package com.znblog;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.znblog.model2json.Contact;
import com.znblog.model2json.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hzqiuxm on 2016/2/25 0025.
 */
public class test2 {

    public static void main(String[] args) {

        //1.嵌套对象转化为json
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        Contact contact3 = new Contact();

        contact1.setEmail("qxmanddjh@163.com");
        contact1.setPhone("13989461462");
        contact2.setEmail("hzqiuxm@163.com");
        contact2.setPhone("13989461462");
        contact3.setEmail("qxmanddzn@163.com");
        contact3.setEmail("13757003521");

        user1.setName("菡萏如佳人");
        user1.setAge(30);
        user1.setContact(contact1);
        user2.setName("临江仙");
        user2.setAge(25);
        user2.setContact(contact2);
        user3.setName("邱枫武");
        user3.setAge(20);
        user3.setContact(contact3);

        String str1 = new Gson().toJson(new User[]{user1,user2,user3});
        System.out.println("str1 = " + str1);

        String str2 = new Gson().toJson(Arrays.asList(user1,user2,user3));
        System.out.println("str2 = " + str2);

        //2.把复杂json转化成嵌套对象
        System.out.println("---------------------数组-----------------------------");
          //2.1 数组
        User[] users = new Gson().fromJson(str1,User[].class);
        for(User user : users){
            System.out.println("user = " + user);
            System.out.println(user.getName());
            System.out.println(user.getContact().getEmail());
        }

        System.out.println("---------------------集合-----------------------------");
          //2.2集合
        List<User> lists2 = new ArrayList<User>();
        lists2 = new Gson().fromJson(str2,new TypeToken<List<User>>(){}.getType());
        for(User user :lists2 ){
            System.out.println("user = " + user);
            System.out.println(user.getName());
            System.out.println(user.getContact().getEmail());
        }
    }
}
