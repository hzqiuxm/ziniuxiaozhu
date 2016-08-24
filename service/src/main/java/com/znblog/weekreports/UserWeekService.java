package com.znblog.weekreports;

import com.jfinal.plugin.activerecord.Db;
import com.znblog.model.UserBase;
import com.znblog.model.ZnDiscuss;
import com.znblog.model.ZnWeeklyReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZhangYifan on 2016/8/12.
 */
public class UserWeekService {
    List<UserBase> userBases=null;
    String user_name=null;
    String user_pwd=null;

    public UserWeekService(String Name){
        userBases=UserBase.dao.find("select user_pwd from user_base where user_name = ?",Name);
        UserBase ub=userBases.get(0);
        user_name=Name;
        user_pwd=ub.getUserPwd();
    }

    /**
    * 检查密码函数
     * @param   pwd   用户密码
     * @return  0    账号密码正确
     *          1    代表没有这个用户
     *          2    代表密码错误
    */
    public int CheckPwd(String pwd){
        if (userBases == null || userBases.size()< 1 )//没有这个用户
            return 1;
        if (!user_pwd.equals(pwd))//密码错误
            return 2;
        return 0;
    }

    /**
     * 获取周报函数
     * @param name 关键字人名
     * @param time 关键字时间
     * @param start 周报查询开始位置
     * @param number 获取周报数
     * @return 周报集合
     */
    public List<Map> GetReport(String name,String time,String start,int number){
        //// TODO: 2016/8/15 这里要check session？
        List<ZnWeeklyReport> weekreports=null;

        //查询部分
        if(time.equals("")) {//time 为空
            if (name.equals(""))//name 也为空 load所有
                weekreports = ZnWeeklyReport.dao.find("select zn_weekly_report.id,real_name,report_this_week,report_next_week,report_difficulty,report_time,report_writer from zn_weekly_report,user_base where report_writer=user_name order by report_time desc limit ?,?", Integer.parseInt(start), number);
            else
                weekreports = ZnWeeklyReport.dao.find("select zn_weekly_report.id,real_name,report_this_week,report_next_week,report_difficulty,report_time,report_writer from zn_weekly_report,user_base where report_writer=user_name and real_name like ?  order by report_time desc limit ?,?", name + "%", Integer.parseInt(start), number);
        }
        else{//有时间
            if(name.equals(""))//没名字
                weekreports=ZnWeeklyReport.dao.find("select zn_weekly_report.id,real_name,report_this_week,report_next_week,report_difficulty,report_time,report_writer from zn_weekly_report,user_base where report_writer=user_name  and date(report_time)= ? order by report_time desc limit ?,?",time,Integer.parseInt(start),number);
            else//有名字
                weekreports=ZnWeeklyReport.dao.find("select zn_weekly_report.id,real_name,report_this_week,report_next_week,report_difficulty,report_time,report_writer from zn_weekly_report,user_base where report_writer=user_name and real_name like ? and date(report_time)= ? order by report_time desc limit ?,?",name+"%",time,Integer.parseInt(start),number);
        }

        return MakeMaps(weekreports);
    }

    /**
     * 根据id获取周报函数
     * @param id 周报的id
     * @return 周报内容集合
     */
    public List<Map> GetReportDependsID(int id){
        List<ZnWeeklyReport> weekreport=ZnWeeklyReport.dao.find("select zn_weekly_report.id,real_name,report_this_week,report_next_week,report_difficulty,report_time,report_writer from zn_weekly_report,user_base where zn_weekly_report.id = ? and user_name=report_writer",id);
        return MakeMaps(weekreport);
    }

    /**
     * 获取评论函数
     * @param id 周报号
     * @return 评论集合
     */
    public List<Map> GetDiscuss(String id){

        List<Map> maps=new ArrayList<Map>();

        List<ZnDiscuss> discuss=ZnDiscuss.dao.find("select zn_discuss.id,discuss_report_id,discuss_critic,discuss_reply,discuss_message,discuss_time,real_name from zn_discuss,user_base where user_base.user_name=discuss_critic and discuss_report_id = ? order by discuss_time asc",Integer.parseInt(id));

        for(ZnDiscuss comment :discuss){
            Map<String,String> map=new HashMap<String,String>();
            map.put("id",comment.getId().toString());
            map.put("report_id",comment.getDiscussReportId().toString());
            map.put("critic",comment.get("real_name").toString());
            map.put("reply","");
            if(comment.getDiscussReply()!=null)
                map.put("reply",comment.getDiscussReply().toString());
            map.put("delete","0");
            if(comment.getDiscussCritic().toString().equals(user_name))
                map.put("delete","1");
            map.put("message",comment.getDiscussMessage().toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=null;
            String time=comment.getDiscussTime().toString();
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            time = new SimpleDateFormat("M月dd日 HH:mm").format(date);

            map.put("time",time);

            maps.add(map);
        }

        return maps;
    }

    /**
     * 添加周报函数
     * @param this_week 本周工作
     * @param next_week 下周计划
     * @param difficulty 遇到的困难
     * @return true of false
     */
    public boolean InsertReport(String this_week,String next_week,String difficulty){
        Date now=new Date();
        int result = Db.update("Insert into zn_weekly_report (report_writer,report_this_week,report_next_week,report_difficulty,report_time) values (?,?,?,?,?)",user_name,this_week,next_week,difficulty,now);
        return result > 0;
    }

    /**
     * 更新周报函数
     * @param this_week 本周工作
     * @param next_week 下周计划
     * @param difficulty 遇到的困难
     * @param id 周报id
     * @return true of false
     */
    public boolean UpdateReport(String this_week,String next_week,String difficulty,int id){
        Date now=new Date();
        int result =Db.update("Update zn_weekly_report set report_this_week=?,report_next_week=?,report_difficulty=?,report_time=? where id=? and report_writer=?",this_week,next_week,difficulty,now,id,user_name);
        return result > 0;
    }

    /**
     * 删除周报函数
     * @param id 周报id
     * @return true of false
     */
    public boolean DeleteReport(String id){
        int result = Db.update("delete from zn_weekly_report where id = ? and report_writer = ?",Integer.parseInt(id),user_name);
        Db.update("delete from zn_discuss where discuss_report_id = ?",Integer.parseInt(id));//同时删除评论内容
        return result > 0;
    }

    /**
     * 删除评论函数
     * @param id 评论id
     * @return true or false
     */
    public boolean DeleteComment(String id){
        int result = Db.update("delete from zn_discuss where id = ? and discuss_critic = ?",Integer.parseInt(id),user_name);
        return result > 0;
    }

    /**
     * 添加评论函数
     * @param message 评论信息
     * @param id 评论人id
     * @param reply 回复人
     * @return true or false
     */
    public boolean InsertComment(String message,int id,String reply){
        Date now=new Date();
        int result =Db.update("Insert into zn_discuss (discuss_report_id,discuss_critic,discuss_message,discuss_time,discuss_reply) values (?,?,?,?,?)",id,user_name,message,now,reply);
        return result > 0;
    }

    /**
     * 周报内容生成函数
     * @param weekreports sql获取到的list
     * @return 生成完的list
     */
    public List<Map> MakeMaps(List<ZnWeeklyReport> weekreports){
        List<Map> maps=new ArrayList<Map>();
        for(ZnWeeklyReport report : weekreports) {//周报集合生成
            Map<String,Object> map=new HashMap<String,Object>();
            String id=report.getId().toString();
            map.put("id",id);
            map.put("realname",report.get("real_name").toString());
            map.put("owner","0");
            try {
                if(report.getReportWriter().equals(user_name))//如果是该用户的周报，则可以修改
                    map.put("owner","1");
            }catch (Exception ignored){

            }
            map.put("thisweekreport",report.getReportThisWeek());
            map.put("nextweekreport",report.getReportNextWeek());
            map.put("difficulty",report.getReportDifficulty());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=null;
            String time=report.getReportTime().toString();
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            time = new SimpleDateFormat("M月dd日 HH:mm").format(date);
            map.put("time",time);

            map.put("comment",GetDiscuss(id));//周报评论

            maps.add(map);
        }
        return maps;

    }

    /**
     * 获取周报详细内容函数
     * @param id 周报id
     * @return 内容集合
     */
    public Map GetReportDetail(String id){
        try {
            List<ZnWeeklyReport> weekreports= ZnWeeklyReport.dao.find("select report_this_week,report_next_week,report_difficulty from zn_weekly_report where id=?",Integer.parseInt(id));
            ZnWeeklyReport report=weekreports.get(0);
            Map<String, Object> map=new HashMap<String, Object>();

            String thisweek=report.getReportThisWeek().toString().replace("<br />","\n").replace("&lt;","<").replace("&gt;",">");
            String nextweek=report.getReportNextWeek().toString().replace("<br />","\n").replace("&lt;","<").replace("&gt;",">");
            String difficulty=report.getReportDifficulty().toString().replace("<br />","\n").replace("&lt;","<").replace("&gt;",">");


            map.put("thisweek",thisweek);
            map.put("nextweek",nextweek);
            map.put("difficulty",difficulty);
            return map;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 获取上周未提交周报的内容函数
     * @return 名字集合
     */
    public List<String> GetLastWeekNoReportName(){
        List<ZnWeeklyReport> weekreports= ZnWeeklyReport.dao.find("select real_name from user_base where user_name not in (select distinct report_writer from zn_weekly_report where WEEK(report_time)= WEEK(NOW())-1)");
        List<String> NameList = new ArrayList<String>();
        for(ZnWeeklyReport report : weekreports) {
            NameList.add(report.get("real_name").toString());
        }
        return NameList;
    }

    /**
     * 修改密码函数
     * @param old_pwd  旧密码
     * @param new_pwd  新密码
     * @return true of false
     */
    public boolean ChangePwd(String old_pwd,String new_pwd){
        int result = Db.update("Update user_base set user_pwd = ? where user_name = ? and user_pwd = ?",new_pwd,user_name,old_pwd);
        return result > 0;
    }
}
