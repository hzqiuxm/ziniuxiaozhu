package com.znblog.controller;

import org.apache.log4j.Logger;

/**
 * Created by hzqixm on 2015/10/19.
 */
public class BlogController extends BaseController {
    private static Logger log = Logger.getLogger(BlogController.class);
    public void index(){
        System.out.println("welcome to 紫牛小筑");
        render("index.jsp");

        System.out.println("wecome visited 紫牛小筑");
    }

    public void myblogs(){

//        DebugInfo.log("BlogController","myblogs()");
        log.info("myblogs() ---------------------");
        System.out.println("=======myblogs(========");

        render("my-blogs.html");
    }

    public void allblogs(){

//        DebugInfo.log("BlogController","allblogs()");
        log.info("allblogs() ---------------------");
        System.out.println("=======myblogs(========");

        render("all-blogs.html");
    }


}
