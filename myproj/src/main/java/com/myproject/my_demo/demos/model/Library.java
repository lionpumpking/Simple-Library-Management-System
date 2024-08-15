package com.myproject.my_demo.demos.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * Description:
 * Author:
 * Data:
 */

@Data
@TableName("library")
public class Library {
    private int id;
    private String bookname;
    private String bookAuthor;
    private int number;
    private int restnumber;
}