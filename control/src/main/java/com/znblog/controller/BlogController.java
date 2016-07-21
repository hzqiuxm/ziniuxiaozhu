package com.znblog.controller;

import com.jfinal.core.Controller;

/**
 * Created by hzqixm on 2015/10/19.
 */
public class BlogController extends Controller {

    public void index(){
        System.out.println("welcome to 紫牛小筑");
        render("index.jsp");

        System.out.println("wecome visited 紫牛小筑");
    }

    public void about_me(){

//        render("about_me.jsp");
        renderText("xxxxxxxx");
//        render("test");
    }


}
