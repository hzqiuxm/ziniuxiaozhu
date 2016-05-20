package com.znblog.controller;

import com.jfinal.core.Controller;

/**
 * Created by hzqiuxm on 2016/3/8 0008.
 */
public class IndexController extends Controller {
    public void index(){
        System.out.println("begin ... ... ");
        render("index.html");

        System.out.println("end !");
    }
}
