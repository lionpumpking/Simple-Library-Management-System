package com.myproject.my_demo.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.my_demo.demos.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper <User>{
  //  List<User> getBySex(int sex);
}
