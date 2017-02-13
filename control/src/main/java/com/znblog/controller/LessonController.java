package com.znblog.controller;


import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.znblog.model.Lessons;
import com.znblog.model.LessonsPlan;
import com.znblog.model.UserBase;
import com.znblog.service.ChooseLesson;
import com.znblog.service.UserService;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzqixm on 2015/10/19.
 */
public class LessonController extends Controller {
    private static final Logger log = Logger.getLogger(LessonController.class);
    public void index(){
        //使用setAttr来设置视图层需要展现的数据
        setAttr("lessons", Lessons.dao.find("select * from lessons"));
//       List<Lessons> lessonses = Lessons.DAO.find("select * from lessons");
        render("show_lessons.jsp");
    }

    public void getLessons(){
        List<Lessons> lessonses = Lessons.dao.find("select * from lessons");
        renderJson(lessonses);
    }

    public void chooseLessons(){
        UserService userService = new UserService();
        ChooseLesson chooseLesson = new ChooseLesson();

        //获取realname
        List<UserBase> userBase = UserBase.dao.find("select * from user_base where user_name = ?",getSessionAttr("name").toString());
        String userName = userBase.get(0).getRealName();

        //检查讲师是否是紫牛小筑的注册讲师

        System.out.println("------------------------------"+userName);
        int checkuser = userService.checkUser(userName);
        if(checkuser==99||checkuser==97){
            log.info("checkuser = "+checkuser);
            renderJson("result",checkuser);
            return;
        }
        int chooseNum = checkuser-1;
        //检查讲师是否有资格选课，如果存在已经选了但未开讲的课程,而且也没有选课次数时不允许再选
        Boolean checkLesson = userService.checkLessons(userName);
        System.out.println("chooseNum ==="+chooseNum);
        if(!checkLesson && checkuser<1){
            log.info("checkLesson = "+checkLesson);
            renderJson("result","95");
            return;
        }
        //根据讲师的身份进行系统选课
        List<Lessons> lessonses = chooseLesson.getRandomLesson(userName);
        System.out.println("lessonses="+lessonses);

        //新增选课记录,先删除原来的
        if(chooseLesson.delOldLesson(userName)){
            System.out.println("之前的选课记录已经删除");
        }else {
            renderText("系统内部错误，请联系管理员!");
        }

        LessonsPlan lessonsPlan = new LessonsPlan();
        Date now = new Date();
        String nowStr=new SimpleDateFormat("yyyyMMddhhmmss").format(now);
        lessonsPlan.set("lesson_name",lessonses.get(0).get("lesson_name"));
        lessonsPlan.set("lesson_title","待定");
        lessonsPlan.set("lesson_des","待补充");
        lessonsPlan.set("lesson_teacher",userName);
        lessonsPlan.set("create_time",now);
        lessonsPlan.set("state","0");
        lessonsPlan.save();

        //更新选课次数与课程状态
        int result = Db.update("UPDATE user_base SET choose_num = ?  WHERE real_name = ?", chooseNum, userName);
        System.out.println("result  ====="+result);

        //更新课程表的课程状态
        int result2 = Db.update("UPDATE lessons SET state = ?  WHERE is_cycle = ? and lesson_name = ? ", 1, "N", lessonses.get(0).get("lesson_name"));
        System.out.println("update result2 ===="+result2);

        Map<String, Object> map=new HashMap<String, Object>();
        map.put("lessons",lessonses);
        map.put("chooseNum",chooseNum);
        renderJson(map);
//        renderJson(lessonses);

    }


}

