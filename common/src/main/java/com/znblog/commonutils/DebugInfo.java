package com.znblog.commonutils;

import com.znblog.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * Created by hzqiuxm on 2016/8/29 0029.
 */
public class DebugInfo {

    private static Log log = LogFactory.getLog(DebugInfo.class);

    public static void log(String className, String msg){
        log.info("["+className+"]:"+"["+msg+"]"+ DataUtil.format(new Date()));
    }

    public static void println(String info){
        if(Constant.devMode){
            System.out.println(info);
        }
    }

    public static void println(Object info){
        DebugInfo.println(info.toString());
    }

}
