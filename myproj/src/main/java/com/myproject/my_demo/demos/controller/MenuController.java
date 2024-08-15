package com.myproject.my_demo.demos.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myproject.my_demo.demos.common.Result;
import com.myproject.my_demo.demos.model.Menu;
import com.myproject.my_demo.demos.model.User;
import com.myproject.my_demo.demos.servicec.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Your Name
 * @since 2024-07-20
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController

public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/api/listmenu")
    public Result listmenu(String roleid) {

        List list= menuService. lambdaQuery().like(Menu::getMenuright,roleid).list();
        //System.out.println(4);
        return Result.succ(list);

    }
}
