package com.myproject.my_demo.demos.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description:
 * Author:
 * Data:
 */
@Data
@TableName("user")
public class User {
    private int id;
    private String username;
    private String name;
    private String password;
    private int age;
    private int sex;
    private int roleid;
    @TableField("isValid")
    private int isValid;
    private String borrowbook;
    private String borrowbookauthor;
}