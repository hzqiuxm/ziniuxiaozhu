package com.znblog.redis;

import com.google.gson.Gson;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.znblog.Constant;
import com.znblog.model2json.DailyWords;
import redis.clients.jedis.Jedis;

/**
 * Copyright © 2016年 author. All rights reserved.
 *
 * @Author 临江仙 hxqiuxm@163.com
 * @Date 2017/1/17 0017 22:08
 */
public class DayDayUpService {
    private DailyWords dailyWords = new DailyWords();

    public DailyWords getDayWords(String value){

        System.out.println("value = " +value);

        String se = null;
        String sc = null;
        Cache daydayupcache = null;
        Jedis jedis = null;

        try {

            daydayupcache = Redis.use("daydayup");
            //为了避免序列化问题折中写法
            jedis = daydayupcache.getJedis();

            se = jedis.hget(Constant.DAYDAYUP_ENGLISH_KEY,value);
            sc = jedis.hget(Constant.DAYDAYUP_CHINESE_KEY,value);

        } finally {
            daydayupcache.close(jedis);
        }

//        System.out.println("---------------" +se + sc);

        dailyWords.setEnglish(se);
        dailyWords.setChinese(sc);

        return  dailyWords;

    }

    /**
     *
     *
     * @return 目前有的每日精进个数
     */
    public long getKeyNums(){

        Cache daydayupcache = Redis.use("daydayup");

        return daydayupcache.hlen(Constant.DAYDAYUP_ENGLISH_KEY);
    }

}
