package com.znblog.controller;

import com.jfinal.plugin.activerecord.Db;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by ZhangYifan on 2016/8/16.
 */
public class Quartztest {

    public static void main(String[] args) {
        try {
            //1.从StdSchedulerFactory工厂中获取一个任务调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //2. 启动调度器
            scheduler.start();
            System.out.println("scheduler is start...");
            //3. 添加定时任务
            //  3.1 定义job
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            //  3.2 定义Trigger，使得job现在就运行
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * SAT"))
                    .build();

            //  3.3 交给scheduler去调度
            scheduler.scheduleJob(job, trigger);
            //4. 关闭调度器
            //scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static class HelloJob implements Job {

        public void execute(JobExecutionContext context) throws JobExecutionException {//上周周报未提交的人 次数加一
            int result = Db.update("update user_base set noweekreport_times=noweekreport_times+1 where real_name in(select a.real_name from (select b.real_name from user_base b where b.user_name not in (select distinct report_writer from zn_weekly_report where WEEK(report_time)= WEEK(NOW())-1))a)");
            if(result==0){
                System.out.println("全都提交了");
            }else{
                System.out.println("成功修改用户表，记录未提交员工");
            }
        }
    }
}
