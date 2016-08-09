package com.znblog.controller;

import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/7/27.
 */
public class WeekReportControllers extends Controller {

    public void index(){

        render("week-report.html");
    }

    public void Login(){
        renderJson("pwd=null");

//        String id="";
//        String pwd=getPara("pwd");
//
//        if(pwd==null){
//            renderJson("pwd=null");
//        }else{
//            renderJson("pwd!=null");
//        }


//        if(false){//如果有传id和pwd
//            id=getPara("id");
//            pwd=getPara("pwd");
//            //设置cookie为新的id和pwd
//            setCookie("id", id, 3600);
//            setCookie("pwd", pwd, 3600);
//        }else{//如果没有传
////            if(getCookieObject("id")!=null&&getCookieObject("pwd")!=null){
////                //id=getCookieObject("id");
////                //pwd=getCookieObject("pwd");
////            }
//        }
//
//
//        List<UserBase> userBases= UserBase.dao.find("select user_pwd from user_base where  user_name = ?",id);
//
//        if (userBases.size()< 1 || userBases == null){
//            renderJson("用户名或密码错误！");
//        } else {
//            pwd = userBases.get(0).get("user_pwd");
//            if(pwd.equals(getPara("pwd"))){
//                renderJson("登录成功");
//
//            }else{
//                renderJson("用户名或密码错误！");
//            }
//        }
//
//
       }

}
