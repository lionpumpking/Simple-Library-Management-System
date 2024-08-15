package com.myproject.my_demo.demos.common;

import com.sun.net.httpserver.Authenticator;
import lombok.Data;

/**
 * Description:
 * Author:
 * Data:
 */

@Data
public class ResultBook {
    private int code ;   //2000 4000
    private String msg;  //错误类型或成功提示
    Object data;


    public static ResultBook bookfail(String msg) {
        return resultBook(4000,msg,"NULL");
    }

    public static ResultBook booksuccess(String msg,Object data) {
        return resultBook(2000, msg ,data);
    }

    public static ResultBook booksuccess(Object data) {
        return resultBook(2000, "Success" ,data);
    }

    private static ResultBook resultBook(int code, String msg, Object data)
    {
        ResultBook resultBook = new ResultBook();
        resultBook.setCode(code);
        resultBook.setMsg(msg);
        resultBook.setData(data);
        return resultBook;
    }
}