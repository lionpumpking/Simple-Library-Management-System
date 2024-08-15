package com.myproject.my_demo.demos.common;

import lombok.Data;

import java.util.HashMap;

/**
 * Description:
 * Author:
 * Data:
 */
@Data
public class QueryPage {
    private static int PAGE_SIZE = 5;
    private static  int PAGE_NUM=1;

    private int page_size=PAGE_SIZE;
    private int page_num=PAGE_NUM;

    private HashMap map=new HashMap();

}