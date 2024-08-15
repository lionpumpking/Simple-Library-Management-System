package com.myproject.my_demo.demos.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Your Name
 * @since 2024-07-20
 */
@Getter
@Setter
@Data
@TableName("menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    /**
     * 菜单编码
     */
    @TableField("menucode")
    private String menucode;

    /**
     * 菜单名字
     */
    @TableField("menuname")
    private String menuname;

    /**
     * 级别
     */
    @TableField("menulevel")
    private String menulevel;

    /**
     * 父code
     */
    @TableField("menuparentcode")
    private String menuparentcode;

    /**
     * 点击事件
     */
    @TableField("menuclick")
    private String menuclick;

    /**
     * 权限
     */
    @TableField("menuright")
    private String menuright;

    @TableField("menucompoment")
    private String menucompoment;

    @TableField("menuicon")
    private String menuicon;
}
