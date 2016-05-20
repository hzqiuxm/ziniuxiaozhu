package com.znblog.controller;


import com.jfinal.core.Controller;
import com.znblog.model.LessonsPlan;
import org.apache.log4j.Logger;

import java.util.List;

public class LessonPlanController extends Controller {
	private static final Logger log = Logger.getLogger(LessonPlanController.class);

	public void index(){
		 render("lesson_plan.jsp");
	}

	public void getLessonPlan(){
		List<LessonsPlan> plans = LessonsPlan.dao.find("select a.id, a.lesson_name, a.lesson_title,a.lesson_des,"
				+ "a.lesson_time,a.lesson_teacher,a.lesson_grade,a.lesson_score,a.lesson_place,a.state,"
				+ "b.lesson_type from lessons_plan a left join lessons b on a.lesson_name=b.lesson_name "
				+ "order by a.lesson_time");
		renderJson(plans);
	}

	public void modifyPlan(){
		int id = getParaToInt("id");
		String lessonTitle = getPara("lessonTitle");
		String lessonDes = getPara("lessonDes");
		boolean isUpdate = false;
		LessonsPlan plan = LessonsPlan.dao.findById(id);
		if(null !=plan){
			plan.set("lesson_title", lessonTitle);
			plan.set("lesson_des", lessonDes);
		    isUpdate = plan.update();
		}
		renderJson("success", isUpdate);
	}

}
