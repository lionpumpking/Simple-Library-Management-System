package com.myproject.my_demo.demos.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:
 * Data:
 */
@Data
public class Result {
    private int code;     //2000 4000
    private String msg;   //true  false
    private Object data;  //数据

    public static Result fail(String msg) {
        return result(4000,msg,"NULL");
    }

    public static Result succ(Object data) {
        return result(2000,"True",data);
    }

    private static Result result(int code, String msg, Object data) {
        Result res=new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }
}