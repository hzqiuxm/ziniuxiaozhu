package com.znblog.redis;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import redis.clients.jedis.Jedis;

/**
 * Created by hzqiuxm on 2016/8/23 0023.
 */
public class newJfinalDemo {

    public static void main(String[] args) {
        // 获取名称为rp的Redis Cache对象

        RedisPlugin rp = new RedisPlugin("daydayup","120.26.231.72");

        rp.start();

          //注释掉的写法会存在序列化问题(redis之支持string,插件为了兼容语言用byte)，读取途径一致是没有问题的，但会导致命令行下无法查看数据，不爽啊！
          // 先代码使用折中手段解决下，后续再研究完美解决方案
//        Redis.use().set("key","value");
//        Redis.use().hset("user","sex","1");
//        String s1 = Redis.use().get("key");
//        String s2 = Redis.use("daydayup").hget("user1","age");

        Cache redis = Redis.use("daydayup");
        Jedis jedis = redis.getJedis();

        jedis.hset("user","sex","22");

        String s1 = "";
        String s2 = "";
        s1 = jedis.hget("user","sex");
        s2 = jedis.hget("day:english","day0");

        redis.close(jedis);
        System.out.println("s2 = " + s2);

    }


}
