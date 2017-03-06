package com.znblog.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

/**
 * Created by hzqiuxm on 2016/5/13 0013.
 * Jedis与连接池
 */
public class RedisTestOne {
    public static void main(String[] args) {
        //连接池配置
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(10);
        jpc.setMinIdle(3);

        //创建连接池
        JedisPool jp = new JedisPool(jpc,"120.26.231.72",6379);

        //从连接池获取一个连接
        Jedis jedis = jp.getResource();

        jedis.set("k1","value1");
        String s1 = jedis.get("k1");
        String s2 = jedis.hget("user","name");

        //归还连接池
        jedis.close();

        System.out.println("s1 = "+s1);
        System.out.println("s2 = " +s2);
    }
}
