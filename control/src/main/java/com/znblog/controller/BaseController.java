package com.znblog.controller;

import com.jfinal.core.Controller;
import com.znblog.Constant;
import com.znblog.model.base.BaseUserBase;
import com.znblog.model2json.JsonResult;

/**
 * Created by hzqiuxm on 2016/8/29 0029.
 */
public class BaseController extends Controller {

    /**
     * 得到modelId
     * @return
     */
    public Integer getParamModelId() {
        return getParaToInt("id");
    }
    /**
     * 得到session中的用户信息
     * @return
     */
    public BaseUserBase getSessionUser(){
        return getSessionAttr(Constant.USER_SESSION_KEY);
    }
    /**
     * 得到session中的用户ID
     * @returnger
     */
    public Integer getSessionAccountId() {
        BaseUserBase user=getSessionUser();
        if(user==null){
            return null;
        }
        return user.getId();
    }

    /**
     * 得到session中的手机号
     * @return
     */
    public String getSessionAccountUsername() {
        BaseUserBase user=getSessionUser();
        if(user==null){
            return null;
        }
        return user.getUserName();
    }
    /**
     * 得到session中的用户真实姓名
     * @return
     */
    public String getSessionAccountRealName() {
        BaseUserBase user=getSessionUser();
        if(user==null){
            return null;
        }
        return user.getRealName();
    }
    /**
     * 返回成功json信息
     */
    public void renderJsonData(boolean success,Object data,String msg) {
        renderJson(new JsonResult(success, data, msg));
    }
    /**
     * 返回json信息
     */
    public void renderJsonData(boolean success,Object data) {
        renderJson(new JsonResult(success, data));
    }
    /**
     * 返回成功json信息
     */
    public void renderJsonSuccessData(Object data) {
        renderJson(new JsonResult(true, data));
    }
    /**
     * 返回失败json信息
     */
    public void renderJsonFailData(Object data,String msg) {
        renderJson(new JsonResult(false, data,msg));
    }
    /**
     * 返回成功json信息
     */
    public void renderJsonSuccess() {
        renderJson("success",true);
    }
    /**
     * 返回成功json信息
     */
    public void renderJsonFail(String msg) {
        setAttr("success", false);
        setAttr("msg",msg);
        renderJson();
    }
    /**
     * 返回失败json信息
     */
    public void renderJsonFail() {
        renderJson("success", false);
    }
    /**
     * 设置错误信息msg
     * @param msg
     */
    public void setErrorMsg(String msg){
        setAttr("success",false);
        setAttr("msg",msg);
    }
}
