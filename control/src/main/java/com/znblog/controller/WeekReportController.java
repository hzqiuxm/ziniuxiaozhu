package com.znblog.controller;


import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.znblog.model.UserBase;
import com.znblog.model.ZnJudge;
import com.znblog.model.ZnWeekreports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzqiuxm on 2016/7/21 0021.
 */
public class WeekReportController extends Controller {

    public void index(){
        render("week-report.html");
    }

    public void Checksession(){
        if(getSessionAttr("id")!=null&&getSessionAttr("pwd")!=null)//如果有session
            renderJson("true");
        else//需要登录
            renderJson("  <center><div id=\"center-div\">\n" +
                    "    <span id=\"loaderror\" style=\"display:none;color:red;\">账号或密码错误！</span>\n" +
                    "    <br />\n" +
                    "    <div class=\"login-input\" id=\"login-login\"><i></i>\n<span>登录：</span><input type=\"text\" id=\"inputid\"></div>\n" +
                    "    <br />\n" +
                    "    <div class=\"login-input\" id=\"login-pwd\"><i></i>\n<span>密码：</span><input type=\"password\" id=\"inputpwd\"  onkeydown=\"if(event.keyCode==13){$('#confirm').trigger('click')}\"></div>\n" +
                    "    <br />\n" +
                    "\n  <div>" +
                    "        <button id=\"confirm\" type=\"submit\">确定</button>\n" +
                    "        <button id=\"cancel\">取消</button>\n" +
                    "    </div>"+
                    "  </div></center>");
    }

    /**
     * 判断登录是否成功函数
     * @return true:登录成功 false：登录失败
     */
    public void Login(){
        String id=getPara("id");
        String pwd=getPara("pwd");
        if(pwd==null||id==null){//账号密码获取失败
            renderJson("false");
            return;
        }
        // 从数据库中判断账号密码是否正确
        List<UserBase> userBases= UserBase.dao.find("select user_pwd from user_base where  user_name = ?",id);
        if (userBases == null || userBases.size()< 1 ){
            renderJson("false");//没有这个用户
            return ;
        }
        String pwd_fromdatabase = userBases.get(0).get("user_pwd");
        if (!pwd_fromdatabase.equals(pwd)) {
            renderJson("false");//密码错误
            return ;
        }
        renderJson("true");
        //设置session
        setSessionAttr("id", id);
        setSessionAttr("pwd", pwd);
    }

    public String Reportdivcontrol(ZnWeekreports report){
        String id=getSessionAttr("id");
        String re="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String time = report.get("reporter_time").toString();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time = new SimpleDateFormat("M月dd日 HH:mm").format(date);
        re+="    <div class=\"report\">\n" +
                "        <div class=\"report-head\">\n" +
                "            <div class=\"report-head-left\"><span>"+report.get("real_name")+"</span>:</div>";

        if(report.get("reporter_writer").equals(id)){//个人动态可以修改
            re+="<div class=\"report-head-right\">\n" +
                    "                <div class=\"hide\"></div>\n" +
                    "                <div class=\"right\">\n" +
                    "                    <div class=\"font\"><i class=\"array\"></i></div>\n" +
                    "                    <div class=\"report-head-control\" >\n" +
                    "                        <span class=\"change\">修改</span>\n" +
                    "                        <span class=\"delete\">删除</span>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>";
        }
        re+="</div>\n" +
                "        <div class=\"report-main\"><span>本周工作</span>"+report.get("reporter_thisweek")+"<span>下周计划</span>"+report.get("reporter_nextweek")+"<span>遇到问题与解决办法</span>"+report.get("reporter_difficulty")+"</div>\n" +
                "        <div class=\"report-foot\">\n" +
                "            <div class=\"report-time\">"+time+"</div>\n" +
                "        </div>\n" +
                "    </div>";

        re+=" <div class=\"judge\">\n" +
                "        <div class=\"judge-textarea-div\" tabindex=\"0\" hidefocus=\"true\">\n" +
                "            <textarea name=\"judge-textarea\" class=\"judge-textarea\" id=\"judge-textarea-"+report.get("id")+"\" cols=\"30\" rows=\"10\"></textarea>\n" +
                "            <div class=\"judge-toolbar\">\n" +
                "                <span class=\"reply\">回复</span>\n" +
                "                <span class=\"can-input\"></span>"+
                "            </div>\n" +
                "        </div>\n" +
                "    </div>";
        List<ZnJudge> judges= ZnJudge.dao.find("select zn_judge.id,judge_weekreports_id,judge_critic,judge_reply,judge_message,judge_time,real_name from zn_judge,user_base where user_base.user_name=judge_critic and judge_weekreports_id=? order by judge_time asc",report.get("id"));
        if(judges.size()>0){//这个周报是有评论的
            for(ZnJudge judge :judges){
                //评论
                re+="    <div class=\"replies\" data-judgeid="+judge.get("id")+">\n" +
                        "        <div class=\"replies-main\">\n" +
                        "            <span>"+judge.get("real_name")+"</span>";
                if(judge.get("judge_reply")!=null){//如果是回复
                    re+="回复<span>"+judge.get("judge_reply")+"</span>";
                }

                time = judge.get("judge_time").toString();
                try {
                    date = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                time = new SimpleDateFormat("M月dd日 HH:mm").format(date);

                re+=":"+judge.get("judge_message")+"\n" +
                        "        </div>\n" +
                        "        <div class=\"replies-foot\">\n" +
                        "            <span class=\"time\">"+time+"</span>\n"+
                        "            <div class=\"replies-control\">";

                if(judge.get("judge_critic").toString().equals(id)){//如果是登录用户的信息
                    re+="                <span class=\"judge-delete\">删除</span>\n";
                }
                re+=    "                <span class=\"judge-reply\">回复</span>\n" +
                        "            </div>\n" +
                        "            <div class=\"reply-textarea\" style=\"display:none;\" tabindex=\"0\" hidefocus=\"true\">\n" +
                        "                <textarea name=\"\" class=\"judge-textarea\" id=\"\" cols=\"30\" rows=\"10\"></textarea>\n" +
                        "                <div class=\"judge-toolbar\">\n" +
                        "                    <span class=\"reply-textarea-span\">回复</span>\n" +
                        "                    <span class=\"can-input\"></span>\n"+
                        "                </div>\n" +
                        "            </div>"+
                        "        </div>\n" +
                        "    </div>\n";

            }
        }
        return re;
    }


    public void Loadreport(){
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String name=getPara("name");
            String time=getPara("time");
            String start=getPara("start");
            List<ZnWeekreports> weekreports=null;

            if(time.equals("")||time==null)//time 为空
                if(name.equals("")||name==null)//name 也为空 load所有
                    weekreports= ZnWeekreports.dao.find("select zn_weekreports.id,real_name,reporter_thisweek,reporter_nextweek,reporter_difficulty,reporter_stars,reporter_time,reporter_writer from zn_weekreports,user_base where reporter_writer=user_name order by reporter_time desc limit ?,5",Integer.parseInt(start));
                else
                    weekreports=ZnWeekreports.dao.find("select zn_weekreports.id,real_name,reporter_thisweek,reporter_nextweek,reporter_difficulty,reporter_stars,reporter_time,reporter_writer from zn_weekreports,user_base where reporter_writer=user_name and real_name like ?  order by reporter_time desc limit ?,5","%"+name+"%",Integer.parseInt(start));
            else{//有时间
                if(name.equals("")||name==null)//没名字
                    weekreports=ZnWeekreports.dao.find("select zn_weekreports.id,real_name,reporter_thisweek,reporter_nextweek,reporter_difficulty,reporter_stars,reporter_time,reporter_writer from zn_weekreports,user_base where reporter_writer=user_name  and DATE(reporter_time)= ? order by reporter_time desc limit ?,5",time,Integer.parseInt(start));
                else//有名字
                    weekreports=ZnWeekreports.dao.find("select zn_weekreports.id,real_name,reporter_thisweek,reporter_nextweek,reporter_difficulty,reporter_stars,reporter_time,reporter_writer from zn_weekreports,user_base where reporter_writer=user_name and real_name like ? and DATE(reporter_time)= ? order by reporter_time desc limit ?,5","%"+name+"%",time,Integer.parseInt(start));
            }

            String re="";
            for(ZnWeekreports report : weekreports) {
                //时间处理部分
                re+="<div class=\"one-report\" id=\""+report.get("id")+"\">\n";
                re+=Reportdivcontrol(report);
                re+="</div>\n";
            }
            renderJson(re);
        }else{//需要登录
            renderJson("nosession");
            return ;
        }
    }

    public void Reloadreport(){
        String reportid=getPara("reportid");
        List<ZnWeekreports> weekreports= ZnWeekreports.dao.find("select zn_weekreports.id,real_name,reporter_thisweek,reporter_nextweek,reporter_difficulty,reporter_stars,reporter_time,reporter_writer from zn_weekreports,user_base where reporter_writer=user_name and zn_weekreports.id=? order by reporter_time",Integer.parseInt(reportid));
        String re="";
        for(ZnWeekreports report : weekreports) {
            re+=Reportdivcontrol(report);
        }
        renderJson(re);

    }

    public void Insertreport(){
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String thisweek=getPara("thisweek").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");
            String nextweek=getPara("nextweek").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");
            String difficulty=getPara("difficulty").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");
            //// TODO: 2016/7/28 如果没有report检查

            if(thisweek=="")
                thisweek="无";
            if(nextweek=="")
                nextweek="无";
            if(difficulty=="")
                difficulty="无";

            int result = Db.update("Insert into zn_weekreports (reporter_writer,reporter_thisweek,reporter_nextweek,reporter_difficulty,reporter_stars,reporter_time) values (?,?,?,?,0,NOW())",id,thisweek,nextweek,difficulty);
            if(result==0){
                renderJson("error");
                return;
            }
            renderJson("success");
        }else{//需要登录
            renderJson("nosession");
            return ;
        }

    }

    public void Updatereport(){//更新周报
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String reportid=getPara("id");
            String thisweek=getPara("thisweek").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");
            String nextweek=getPara("nextweek").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");
            String difficulty=getPara("difficulty").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");

            if(thisweek=="")
                thisweek="无";
            if(nextweek=="")
                nextweek="无";
            if(difficulty=="")
                difficulty="无";

            int result = Db.update("Update zn_weekreports set reporter_thisweek=?,reporter_nextweek=?,reporter_difficulty=?,reporter_time=NOW() where reporter_writer=? and id=?",thisweek,nextweek,difficulty,id,Integer.parseInt(reportid));
            if(result==0){
                renderJson("error");
                return;
            }
            renderJson("success");
        }else{//需要登录
            renderJson("nosession");
            return ;
        }

    }

    public void Deletereport(){
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String report=getPara("reportid");
            int result = Db.update("delete from zn_weekreports where id = ? and reporter_writer = ?",Integer.parseInt(report),id);
            if(result==0){
                renderJson("error");
                return;
            }
            //删除评论
            result = Db.update("delete from zn_judge where judge_weekreports_id = ?",report);

            renderJson("success");
        }else{//需要登录
            renderJson("nosession");
            return ;
        }

    }

    public void Getreport(){
        String id=getPara("id");
        try {
            List<ZnWeekreports> weekreports= ZnWeekreports.dao.find("select reporter_thisweek,reporter_nextweek,reporter_difficulty from zn_weekreports where id=?",Integer.parseInt(id));
            ZnWeekreports report=weekreports.get(0);
            Map<String, Object> map=new HashMap<String, Object>();

            String thisweek=report.get("reporter_thisweek").toString().replace("<br />","\n").replace("&lt;","<").replace("&gt;",">");
            String nextweek=report.get("reporter_nextweek").toString().replace("<br />","\n").replace("&lt;","<").replace("&gt;",">");
            String difficulty=report.get("reporter_difficulty").toString().replace("<br />","\n").replace("&lt;","<").replace("&gt;",">");


            map.put("thisweek",thisweek);
            map.put("nextweek",nextweek);
            map.put("difficulty",difficulty);
            renderJson(map);
        }catch(Exception e){
            renderJson("error");
            return;
        }
    }

    public void Logout(){
        removeSessionAttr("id");
        removeSessionAttr("pwd");
        renderJson("success");
    }

    public void Judgeinsert(){//新增评论
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String message=getPara("message").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");//评论信息
            String reportid=getPara("reportid");//评论周报id
            int result =Db.update("Insert into zn_judge (judge_weekreports_id,judge_critic,judge_message,judge_time) values (?,?,?,NOW())",Integer.parseInt(reportid),id,message);
            if(result==0){
                renderJson("error");
                return;
            }
            renderJson("success");
        }else{//需要登录
            renderJson("nosession");
            return ;
        }

    }

    public void Replyjudge(){//回复评论
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String message=getPara("message").replace("<","&lt;").replace(">","&gt;").replace("\n","<br />");//评论信息
            String reportid=getPara("reportid");//评论周报id
            String replyname=getPara("replyname");//回复人的名字
            int result =Db.update("Insert into zn_judge (judge_weekreports_id,judge_critic,judge_message,judge_time,judge_reply) values (?,?,?,NOW(),?)",Integer.parseInt(reportid),id,message,replyname);
            if(result==0){
                renderJson("error");
                return;
            }
            renderJson("success");
        }else{//需要登录
            renderJson("nosession");
            return ;
        }

    }

    public void Deletejudge(){//删除评论
        if(getSessionAttr("id")!=null){//如果有session
            String id=getSessionAttr("id");
            String judge_id=getPara("judgeid");
            int result = Db.update("delete from zn_judge where id = ? and judge_critic = ?",Integer.parseInt(judge_id),id);
            if(result==0){
                renderJson("error");
                return;
            }
            renderJson("success");
        }else{//需要登录
            renderJson("nosession");
            return ;
        }
    }

    public void Lastweek(){//上周周报未提交
        List<ZnWeekreports> weekreports= ZnWeekreports.dao.find("select real_name from user_base where user_name not in (select distinct reporter_writer from zn_weekreports where WEEK(reporter_time)= WEEK(NOW())-1)");
        String re="";
        for(ZnWeekreports report : weekreports) {
            //时间处理部分
            re+="<span>"+report.get("real_name")+"</span>\n";
        }
        renderJson(re);
    }
}
