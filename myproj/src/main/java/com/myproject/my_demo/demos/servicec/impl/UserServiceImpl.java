package com.myproject.my_demo.demos.servicec.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.my_demo.demos.mapper.UserMapper;
import com.myproject.my_demo.demos.model.User;
import com.myproject.my_demo.demos.servicec.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * Description:
 * Author:
 * Data:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

@SneakyThrows
@Override
public User getByUsername(String username) {
    Connection conn;
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_demo1", "root", "20030819hxt=");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    String sql = "select * from user where username=?";
    PreparedStatement statement = conn.prepareStatement(sql);
    statement.setString(1, username);
    ResultSet resultSet = statement.executeQuery();
    User user = new User();
    while (resultSet.next()) {
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setAge(resultSet.getInt("age"));
        user.setSex(resultSet.getInt("sex"));
        user.setRoleid(resultSet.getInt("roleid"));
        user.setIsValid(resultSet.getInt("isValid"));
        user.setBorrowbook(resultSet.getString("borrowbook"));
        user.setBorrowbookauthor(resultSet.getString("borrowbookauthor"));
    }
    return user;
}

    @SneakyThrows
    @Override
    public User getByName(String name) {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_demo1", "root", "20030819hxt=");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "select * from user where name=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        while (resultSet.next()) {
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setSex(resultSet.getInt("sex"));
            user.setRoleid(resultSet.getInt("roleid"));
            user.setIsValid(resultSet.getInt("isValid"));
            user.setBorrowbook(resultSet.getString("borrowbook"));
            user.setBorrowbookauthor(resultSet.getString("borrowbookauthor"));
        }
        return user;
    }

////    @SneakyThrows
//    @Override
//    public List<User> getBySex(int sex) {
//        return userMapper.getBySex(sex);
//    }

}
