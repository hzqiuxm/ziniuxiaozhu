package com.znblog.model2json;

/**
 * Created by hzqiuxm on 2016/8/29 0029.
 */
public class JsonResult {

    public boolean success;
    public String msg;
    public Object data;
    public JsonResult() {
    }
    public JsonResult(boolean success,Object data) {
        this.success=success;
        this.data=data;
    }
    public JsonResult(boolean success,Object data,String msg) {
        this.success=success;
        this.data=data;
        this.msg=msg;
    }
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public static void main(String[] args) {
    }
}
