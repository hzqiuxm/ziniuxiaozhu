package com.znblog.model2json;

import java.io.Serializable;

/**
 * Copyright © 2016年 author. All rights reserved.
 *
 * @Author 临江仙 hxqiuxm@163.com
 * @Date 2017/1/18 0018 9:53
 */
public class DailyWords implements Serializable {

    private String english;
    private String chinese;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "DailyWords{" +
                "english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}
