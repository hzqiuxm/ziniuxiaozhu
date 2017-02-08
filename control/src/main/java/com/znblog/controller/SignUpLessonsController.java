package com.znblog.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

/**
 * Copyright © 2016年 author. All rights reserved.
 *
 * @Author 临江仙 hxqiuxm@163.com
 * @Date 2017/2/7 0007 13:52
 */
public class SignUpLessonsController extends Controller {

    private static final Logger log = Logger.getLogger(SignUpLessonsController.class);

    @Clear(SessionInterceptor.class)
    public void index(){

        render("signup-lessons.html");

    }

}
