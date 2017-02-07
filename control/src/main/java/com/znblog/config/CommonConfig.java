package com.znblog.config;


import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.znblog.controller.*;
import com.znblog.model._MappingKit;

/**
 * Created by hzqiuxm on 2015/10/13
 */
public class CommonConfig extends JFinalConfig {

    /**
     * 配置常量
     * @param me
     */
    @Override
    public void configConstant(Constants me) {


        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("properties/config.properties");
        //设置开发模式，如果是true，后台会输出Controller、action参数信息
        me.setDevMode(PropKit.getBoolean("devMode", true));
        // 设置视图类型为Jsp，否则默认为FreeMarker
        me.setViewType(ViewType.JSP);
        //设置view层的根目录
        me.setBaseViewPath("/views");
//        me.setBaseViewPath("/");
        //设置404错误页面
        me.setError404View("404.html");
        //设置文件下载地址
//        me.setFileRenderPath("/file/download");
        //设置文件上传默认保存路径
//        PathKit.setWebRootPath("/");
//        me.setUploadedFileSaveDirectory("D:\\javaDev\\mytest\\LxWeb\\web\\upload");
        //设置URL参数分隔符
        me.setUrlParaSeparator("-");

    }

    /**
     * 配置路由映射
     * @param me
     */
    @Override
    public void configRoute(Routes me) {
        // 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
        me.add("/", IndexController.class);
        me.add("/blog", BlogController.class);
        me.add("/lesson", LessonController.class);
        me.add("/lessonPlan", LessonPlanController.class);
        me.add("/weekreport",WeekReportController.class);
        me.add("/daydayup",DayDayUpController.class);
        me.add("/signup",SignUpLessonsController.class);
        //前后端路由分开
        //me.add(new 前端配置类());
        //me.add(new 后端配置类());
    }

    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    /**
     * 配置插件
     * @param me
     */
    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = createC3p0Plugin();
        me.add(c3p0Plugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);

        //配置redis插件
        //用户每日精进的redis
        RedisPlugin redisPlugin = new RedisPlugin("daydayup","120.26.231.72");
        me.add(redisPlugin);

        /* 2.1以后不需要配置表盒模型的映射关系
        //建立表和模型的映射关系，例如：映射user_base表到 UserBase模型
        arp.addMapping("user_base", UserBase.class);
        arp.addMapping("lessons",Lessons.class);
        arp.addMapping("lessons_plan", LessonsPlan.class);
        arp.addMapping("lesson_eval", LessonsEval.class);
        */
        // 所有配置在 MappingKit 中搞定
        _MappingKit.mapping(arp);
        



    }

    /**
     * 配置全局拦截器
     * @param me
     */
    @Override
    public void configInterceptor(Interceptors me) {

//        可以配置多个拦截器，先调用的后完成,controller只执行一次
        me.add(new GlobaInterceptor());
        me.add(new SessionInterceptor());
    }

    /**
     * 配置全局公共处理器
     * @param me
     */
    @Override
    public void configHandler(Handlers me) {

        //可以创建多个handler，最后会执行默认的actionHandler
//        me.add(new HtmlHandler());
    }


    @Override
    public void afterJFinalStart() {
        System.out.println("Jfinal启动后，启动自动调度线程......");
        super.afterJFinalStart();
//        Quartztest.main(new String[0]);
    }

    @Override
    public void beforeJFinalStop() {
        System.out.println("Jfinal关闭后，释放调度线程的内存......");
        super.beforeJFinalStop();
    }

    /**
     * **
     * 建议使用 JFinal 手册推荐的方式启动项目
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {

//        JFinal.start("web", 80, "/", 5);
//        System.out.println("11111");
        JFinalConfig jfinalConfig;
        Object temp = null;
        try {
            temp = Class.forName("com.demo.common.CommonConfig").newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can not create instance of class: " , e);
        }
        if (temp instanceof JFinalConfig){
            System.out.println("ddasdasd");
            jfinalConfig = (JFinalConfig)temp;
        }
    }
}
