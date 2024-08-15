package com.myproject.my_demo.demos.servicec.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.my_demo.demos.mapper.LibraryMapper;
import com.myproject.my_demo.demos.model.Library;
import com.myproject.my_demo.demos.model.User;
import com.myproject.my_demo.demos.servicec.LibraryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Provider;
import java.sql.*;

/**
 * Description:
 * Author:
 * Data:
 */
@Service
public class LibraryServiceImpl extends ServiceImpl<LibraryMapper, Library> implements LibraryService {

    @SneakyThrows
    @Override
    public Library getByBookname(@RequestBody String string) {
        String[] str=string.split(" ");
        String bookname=str[0];
        String book_author=str[1];
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_demo1", "root", "20030819hxt=");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "select * from library where bookname=? AND book_author=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, bookname);
        statement.setString(2, book_author);
        ResultSet resultSet = statement.executeQuery();
        Library library = new Library();
        while (resultSet.next()) {
            library.setBookname(resultSet.getString("bookname"));
            library.setNumber(resultSet.getInt("number"));
            library.setBookAuthor(resultSet.getString("book_author"));
            library.setId(resultSet.getInt("id"));
            library.setRestnumber(resultSet.getInt("restnumber"));
        }
        return library;
    }
}