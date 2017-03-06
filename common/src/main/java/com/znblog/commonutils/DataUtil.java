package com.znblog.commonutils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hzqiuxm on 2016/8/29 0029.
 */
public class DataUtil {

    public static final String YMD = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyy-MM-dd HH-mm-ss";

    public static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String format(Date date, String pattern){

        if( null==date ){
            return "";
        }
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    public static String format(Date date){
        if(null == null){
            return "";
        }
        sdf.applyPattern(YMDHMS);
        return sdf.format(date);
    }
}
