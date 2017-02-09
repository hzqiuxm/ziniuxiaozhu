package com.znblog.redis;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.znblog.Constant;
import com.znblog.model2json.DailyWords;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取每日精进句子列表
     * @return
     */
    public List<DailyWords> getDayWordsList(){
        List<DailyWords> wordsList = new ArrayList<>();
        Cache daydayupcache = null;
        Jedis jedis = null;
        Map<String, String> deMap = null;
        Map<String, String> dcMap = null;
        try {

            daydayupcache = Redis.use("daydayup");
            //为了避免序列化问题折中写法
            jedis = daydayupcache.getJedis();

            deMap = jedis.hgetAll(Constant.DAYDAYUP_ENGLISH_KEY);
            dcMap = jedis.hgetAll(Constant.DAYDAYUP_CHINESE_KEY);
        } finally {
            daydayupcache.close(jedis);
        }
        if (deMap != null && deMap.size() > 0){
            Iterator<Map.Entry<String, String>> it = deMap.entrySet().iterator();
            while (it.hasNext()){
                DailyWords dw = new DailyWords();
                Map.Entry<String, String> entry = it.next();
                dw.setEnglish(entry.getValue());
                dw.setChinese(dcMap.get(entry.getKey()));
                wordsList.add(dw);
            }
        }
        return wordsList;
    }

    /**
     * 新增句子
     * @param english
     * @param chinese
     */
    public void addWords(String english, String chinese){
        Cache daydayupcache = null;
        Jedis jedis = null;
        String field = "day" + getKeyNums();
        try {

            daydayupcache = Redis.use("daydayup");
            //为了避免序列化问题折中写法
            jedis = daydayupcache.getJedis();
            jedis.hset(Constant.DAYDAYUP_ENGLISH_KEY, field, english);
            jedis.hset(Constant.DAYDAYUP_CHINESE_KEY, field, chinese);
        } finally {
            daydayupcache.close(jedis);
        }
    }

}
