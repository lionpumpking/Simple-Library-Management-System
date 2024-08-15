package com.myproject.my_demo.demos.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.my_demo.demos.model.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Your Name
 * @since 2024-07-20
 */
@Mapper
public interface MenuMapper extends BaseMapper <Menu> {

}
