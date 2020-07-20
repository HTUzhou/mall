package com.macro.mall.tiny.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装错误信息对象
 */
public class Msg {
    private int code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(200);
        msg.setMessage("获取数据成功！");
        return msg;
    }

    public static Msg fail() {
        Msg msg = new Msg();
        msg.setCode(500);
        msg.setMessage("处理失败！");
        return msg;
    }

    public Msg add(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
