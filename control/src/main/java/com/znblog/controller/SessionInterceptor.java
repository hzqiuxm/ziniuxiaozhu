package com.znblog.controller;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2016/8/29.
 */
public class SessionInterceptor  implements Interceptor {
    private static final Logger log = Logger.getLogger(GlobaInterceptor.class);

    public void intercept(Invocation ai) {
        log.info("调用了拦截器SessionInterceptor");

        Controller controller = ai.getController();
        log.info("触发函数:"+ai. getMethodName());
        log.info(ai. getMethodName());
        log.info("name:"+controller.getSessionAttr("name")+"   pwd:"+controller.getSessionAttr("pwd"));
        if ((controller.getSessionAttr("name")!=null&&controller.getSessionAttr("pwd")!=null)||ai. getMethodName().equals("login")||ai. getMethodName().equals("checksession")||ai. getMethodName().equals("index"))
            ai.invoke();
        else
            controller.redirect("/views/week-report.html");
        log.info("调用拦截器SessionInterceptor完成");

    }
}
