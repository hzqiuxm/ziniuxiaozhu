package com.znblog.service;


import com.znblog.model.LessonsPlan;
import com.znblog.model.UserBase;

import java.util.List;

/**
 * Created by hzqixm on 2015/10/31.
 */
public class UserService {
    private static final int NO_TEACHER = 99;
    private static final int NO_CHOOSE = 97;

    /**
     * 判断某人是否注册讲师,是否还有选课次数
     * @param name 真实姓名
     * @return 99:不是注册讲师 98：是注册讲师 97:没有选课次数 其他表示剩余选课次数
     */
    public int checkUser(String name){

        List<UserBase> userBases = UserBase.dao.find("select * from user_base where real_name = ?",name);
        int chooseNum = 0;
        if(userBases.size()<1||userBases == null){
            return NO_TEACHER;
        }else {
            chooseNum = userBases.get(0).get("choose_num");
            System.out.println(chooseNum);
            if(chooseNum<1){
                return  NO_CHOOSE;
            }
        }
        return chooseNum;
    }

    /**
     * 判断是否某讲师是否可以选课
     * @param name 真实姓名
     * @return true 可以选课  false不可以选课
     */
    public Boolean checkLessons(String name){

        List<LessonsPlan> lessonsPlans = LessonsPlan.dao.find("select * from lessons_plan where lesson_teacher = ? and state = ? ",name,0);
        if(lessonsPlans.size()>0){
            return false;
        }
        return true;
    }



}
