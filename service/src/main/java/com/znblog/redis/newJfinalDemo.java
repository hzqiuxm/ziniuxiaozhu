package com.znblog.redis;

import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;

/**
 * Created by hzqiuxm on 2016/8/23 0023.
 */
public class newJfinalDemo {

    public static void main(String[] args) {
        // 获取名称为rp的Redis Cache对象

        RedisPlugin rp = new RedisPlugin("rp","192.168.10.56");

        rp.start();
        Redis.use().set("key","value");
//        System.out.println(Redis.use().get("key"));
    }


}
