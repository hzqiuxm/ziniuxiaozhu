package com.znblog.controller;

import com.jfinal.core.Controller;
import com.znblog.model2json.DailyWords;
import com.znblog.redis.DayDayUpService;
import org.apache.log4j.Logger;

/**
 * Copyright © 2016年 author. All rights reserved.
 *
 * @Author 临江仙 hxqiuxm@163.com
 * @Date 2017/1/17 0017 21:43
 */
public class DayDayUpController extends Controller {
    private static final Logger log = Logger.getLogger(BlogController.class);
    private DayDayUpService ddus = new DayDayUpService();

    /**
     * 随机获取一句每日精进
     */
    public void index(){

        if (log.isDebugEnabled()){
            log.debug("Welcome to daydayup!");
        }

        renderJson(getRandomKey());


    }

    public DailyWords getRandomKey(){

        long lnums = 0l;
        //查询redis中目前每日精进有多少个
        lnums = ddus.getKeyNums();

        //随机获取一个
        int ikey = (int) (Math.random()*lnums);

        if (log.isDebugEnabled()){
            log.debug("random num is : " + ikey);
        }
//        System.out.println("random num is : " + ikey);

        return ddus.getDayWords("day"+ikey);

    }

}
