package com.znblog.controller;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.log4j.Logger;

/**
 * Created by hzqixm on 2015/10/29.
 */
public class GlobaInterceptor implements Interceptor {
    private static final Logger log = Logger.getLogger(GlobaInterceptor.class);
    @Override
    public void intercept(Invocation inv) {
        log.info("调用了拦截器GlobalInterceptor");
        inv.invoke();
        log.info("调用了拦截器GlobalInterceptor完成");
    }
}
