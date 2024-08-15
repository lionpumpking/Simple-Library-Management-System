package com.myproject.my_demo.demos.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myproject.my_demo.demos.common.ResultBook;
import com.myproject.my_demo.demos.model.Library;
import com.myproject.my_demo.demos.model.Menu;
import com.myproject.my_demo.demos.model.User;
import com.myproject.my_demo.demos.servicec.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 * Author:
 * Data:
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController

public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/api/listbook")
    public ResultBook listBook(){
        return ResultBook.booksuccess(libraryService.list());
    }

    @GetMapping("/api/listbookP")
    public IPage listBookP(int pagenum, int pagesize){
        Page<Library> page=new Page(pagenum,pagesize);
        IPage res=libraryService.page(page);
        return res;
    }

    @PostMapping("/api/add")
    public ResultBook addBook(@RequestBody Library library){
        System.out.println(library);
        String string=library.getBookname()+" "+library.getBookAuthor();
        Library library1=libraryService.getByBookname(string );
        if (library1.getBookname()!=null&&library.getBookAuthor().equals(library1.getBookAuthor()))
        {
            library1.setNumber(library1.getNumber()+ library.getNumber());
            library1.setRestnumber(library1.getRestnumber()+library.getNumber());
            return ResultBook.booksuccess("增加数量成功",libraryService.updateById(library1));
        }
        else {
            libraryService.save(library);
            return ResultBook.booksuccess("增加书成功",library);
        }
    }

    @GetMapping("/api/search")
    public ResultBook getBook(String bookname,long pagenum,long pagesize){
        //List res=libraryService.lambdaQuery().like(Library::getBookname,bookname).list();
                //libraryService.getByBookname(bookname);
        QueryWrapper<Library> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("bookname",bookname);
        //Object data = libraryService.list(lambdaQueryWrapper);

        Page<Library> page=new Page(pagenum,pagesize);
        IPage res1=libraryService.getBaseMapper().selectPage(page,queryWrapper);
        //System.out.println(res);
        return ResultBook.booksuccess(res1);
    }

    @GetMapping("/api/deletebook")
    public ResultBook deletebook(int id){
        Library library=libraryService.getById(id);
        if(library.getNumber()!=library.getRestnumber()){
            return ResultBook.bookfail("false");
        }
        return ResultBook.booksuccess(libraryService.removeById(id));
    }

//    public IPage listBookP(int pagenum, int pagesize){
//        List user = LibraryService.list();
//        Page<Library> page = new  Page(pagenum, pagesize);
//        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(User::getName,user.getName());
//        Object data = userService.list(lambdaQueryWrapper);
//        System.out.println(data);
//        if(userService.list(lambdaQueryWrapper).isEmpty()) return Result.fail("不存在匹配用户");
//        return Result.succ(userService.list(lambdaQueryWrapper));*/
//        IPage res=LibraryService.page(page);
//        //IPage res=LibraryService.page(page);
//        return res;
//    }

}

