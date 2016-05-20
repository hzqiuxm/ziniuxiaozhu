package com.znblog.service;

import com.jfinal.plugin.activerecord.Db;
import com.znblog.model.Lessons;
import com.znblog.model.LessonsPlan;
import com.znblog.model.UserBase;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by hzqixm on 2015/10/31.
 */
public class ChooseLesson {
    private static final Logger log = Logger.getLogger(ChooseLesson.class);
    private static final String SJ = "设计";
    private static final String YW = "运维";
    private static final String YD = "移动";
    private static final String CP = "产品";
    private static final String CS = "测试";

    public List<Lessons> getRandomLesson(String name){
        List<Lessons> finalLessons = new ArrayList<Lessons>();
        int randomSeed;
        List<UserBase> userBase = UserBase.dao.find("select * from user_base where real_name = ?",name);
        String strIdentity = userBase.get(0).get("identity");

        List<Lessons> lessonses = null;
        if(SJ.equalsIgnoreCase(strIdentity)){
            lessonses = Lessons.dao.find("select * from lessons where state = ? and lesson_type in('设计','通用')",0);

        }else if (YW.equalsIgnoreCase(strIdentity)){
            lessonses = Lessons.dao.find("select * from lessons where state = ? and lesson_type in('运维','通用','后端')",0);
        }else if (YD.equalsIgnoreCase(strIdentity)){
            lessonses = Lessons.dao.find("select * from lessons where state = ? and lesson_type not in('运维')",0);
        }else if(CP.equalsIgnoreCase(strIdentity)){
            lessonses = Lessons.dao.find("select * from lessons where state = ? and lesson_type in('产品','后端','前端','通用')",0);
        }else if(CS.equalsIgnoreCase(strIdentity)){
            lessonses = Lessons.dao.find("select * from lessons where state = ? and lesson_type in('测试','通用')",0);
        }
        else {
            //后端开发岗位的选题来源
            lessonses = Lessons.dao.find("select * from lessons where state = ? and lesson_type not in('设计','移动','产品')",0);
        }

        HashSet<Integer> setnum = null;
        if(lessonses.size()<2){
            return lessonses;
        }else {
            int totalNum = lessonses.size();
            setnum = new HashSet<Integer>();
            randomSet(0,totalNum-1,1,setnum);
        }

        Integer nums[] = setnum.toArray( new Integer[0]);

        for (int i = 0; i < setnum.size(); i++) {
            Lessons lessons = lessonses.get(nums[i]);
            finalLessons.add(lessons);
        }

        return finalLessons;
    }

    /**
     *
     * @param min 最小值
     * @param max 最大值
     * @param n 随机个数
     * @param set 随机后结果
     */
    public static void randomSet(int min, int max, int n, HashSet<Integer> set){
        Random random = new Random();
        if(n > max - min +1||max<min){
            return;
        }

        for (int i = 0; i <n; i++) {
            int num = random.nextInt(max);
            set.add(num);
        }

        //如果没有达到
        int setsize = set.size();
        if(setsize < n){
            randomSet(min, max, n-setsize, set);
        }
    }

    public Boolean delOldLesson(String name){

        //只删除计划表中没有讲课的记录，已经讲过的需要保留
        List<LessonsPlan> lessonsPlans = LessonsPlan.dao.find("SELECT * FROM lessons_plan where lesson_teacher = ? and state = ? ", name, 0);
        for (int i = 0; i < lessonsPlans.size(); i++) {
            long id = lessonsPlans.get(0).getLong("id");
            String lessonName = lessonsPlans.get(0).get("lesson_name");
            System.out.println("id ======"+id);
            //更新课程表的课程状态
            int result = Db.update("UPDATE lessons SET state = ?  WHERE is_cycle = ? and lesson_name = ? ", 0, "N", lessonName);
            System.out.println("update result ===="+result);
            return LessonsPlan.dao.deleteById(id);
        }
        return true;
    }

    public static void main(String[] args) {
//        Random random = new Random();
//        int a[] = null;
//        for (int i = 0; i < 3; i++) {
//            System.out.println(random.nextInt(20));
//
//        }

        ChooseLesson chosse = new ChooseLesson();
        chosse.getRandomLesson("酉明");

    }
}
