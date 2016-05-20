package com.znblog;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.znblog.model2json.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzqiuxm on 2016/2/25 0025.
 */
public class test {

    public static void main(String[] args) {

        //1.单个对象转化为Json
        User user1 = new User();
        user1.setName("菡萏如佳人");
        user1.setAge(30);
        user1.setAdmin(true);

        String str1 = new Gson().toJson(user1);
        System.out.println(str1);

        //2.JSON转化为单个对象
        String str2 = "{" +
                "    \"name\": \"username\",\n" +
                "    \"age\": 20,\n" +
                "    \"admin\": true\n" +
                "}";
        User user2 = new Gson().fromJson(str2,User.class);
        System.out.println(user2.getName());

        //3.根据版本号选择需要序列化的字段
        User user3 = new User();
        user3.setAge(25);
        user3.setAdmin(true);
        user3.setName("临江仙");

        Gson gson2 = new GsonBuilder().setVersion(1.0).create();
        String str3 = gson2.toJson(user3);
        System.out.println(str3);

        //4.将集合对象转化为json
        List<User> userList = new ArrayList<User>();
        for(int i = 0; i<10; i++){
            User user = new User();
            user.setName("name"+i);
            user.setAge(i*5);
            user.setAdmin(true);
            userList.add(user);
        }
        String strs = new Gson().toJson(userList);
        System.out.println(strs);

        //5.将json转化成集合
        List<User> userList1 = new ArrayList<User>();

        userList1 = new Gson().fromJson(strs,new TypeToken<List<User>>(){}.getType());
        for(User users : userList1){
            System.out.println(users.getName());
            System.out.println(users.getAge());
        }
    }
}
