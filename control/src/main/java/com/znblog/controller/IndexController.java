package com.znblog.controller;

import com.znblog.model2json.Contact;

/**
 * Created by hzqiuxm on 2016/3/8 0008.
 */
public class IndexController extends BaseController {
    public void index(){
        System.out.println("begin ... ... ");
        render("index.html");

        System.out.println("end !");
    }


    public void getinfo() {
        System.out.println("Begin getinfo() ....");

        Contact contact = new Contact();
        contact.setEmail("hzqiuxm@163.com");
        contact.setPhone("13989461462");
        setSessionAttr("qxm","qiuxiaomin");
        setAttr("contact",contact);
//        System.out.println(getSession().toString());
        renderJson();

        System.out.println("End getinfo() !");
    }
}
