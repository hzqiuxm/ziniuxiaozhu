package com.znblog.controller;

import com.jfinal.core.Controller;

/**
 * Created by hzqixm on 2015/10/19.
 */
public class BlogController extends Controller {

    public void index(){
        System.out.println("11111111111111111111111111111111");
        render("index.jsp");

        System.out.println("22222222222222222222222222222222");
    }

    public void about_me(){

//        render("about_me.jsp");
        renderText("xxxxxxxx");
//        render("test");
    }


}
