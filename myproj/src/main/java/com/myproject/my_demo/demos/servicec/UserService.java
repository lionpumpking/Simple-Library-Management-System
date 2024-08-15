package com.myproject.my_demo.demos.servicec;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.my_demo.demos.model.User;
import jakarta.validation.constraints.NegativeOrZero;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public interface UserService extends IService<User> {


    static List<User> obtolist(Object data) {
        List<User> users = new ArrayList<>();
        users.add((User) data);
        return users;
    }

    User getByUsername(String username) ;


    User getByName(String name);

//    List<User> getBySex(int sex);
}
