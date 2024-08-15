package com.myproject.my_demo.demos.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myproject.my_demo.demos.common.*;
import com.myproject.my_demo.demos.model.Library;
import com.myproject.my_demo.demos.model.Menu;
import com.myproject.my_demo.demos.model.User;
import com.myproject.my_demo.demos.servicec.LibraryService;
import com.myproject.my_demo.demos.servicec.MenuService;
import com.myproject.my_demo.demos.servicec.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Description:
 * Author:
 * Data:
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private MenuService menuService;

    //查询所有
    @GetMapping("/api/list")
    public Result list() {

        return Result.succ(userService.list());
    }

    //新增
    @PostMapping("/api/save")
    public boolean save(@RequestBody User user) throws Exception {
        if((userService.getByUsername(user.getUsername()).getUsername())!=null)
            throw new Exception("该账号已被使用");
        return userService.save(user);
    }
    //修改
    @GetMapping("/api/byusername")
    public Result buusername(String username) throws Exception {
        User user = userService.getByUsername(username);
        return Result.succ(UserService.obtolist(user));
    }

    @PostMapping("/api/mod")
    public boolean mod(@RequestBody User user) {
        System.out.println(user);
        User user1 = userService.getById(user.getId());
        user1.setUsername(user.getUsername());
        user1.setId(user.getId());
        user1.setName(user.getName());
        user1.setSex(user.getSex());
        user1.setAge(user.getAge());
        if(user.getBorrowbookauthor()!=null){
            user1.setBorrowbookauthor(user.getBorrowbookauthor());
        user1.setBorrowbook(user.getBorrowbook());}
        return userService.updateById(user1);
    }

    @PostMapping("/api/mod1")
    public boolean mod1(@RequestBody User user) {
        System.out.println(user);
        User user1 = userService.getById(user.getId());
        user1.setRoleid(user.getRoleid());
        user1.setIsValid(user.getIsValid());
        return userService.updateById(user1);
    }
    //删除
    @GetMapping("/api/delete")
    public boolean delete(Integer id ) {
        return userService.removeById(id);
    }
    //模糊搜索
    @PostMapping("/api/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,user.getName());
        Object data = userService.list(lambdaQueryWrapper);
        System.out.println(data);
        if(userService.list(lambdaQueryWrapper).isEmpty()) return Result.fail("不存在匹配用户");
        return Result.succ(userService.list(lambdaQueryWrapper));
    }

    @GetMapping("/api/listP1")
    public IPage listP1(int pagenum, int pagesize) {
        List user = userService.list();
        Page<User> page = new  Page(pagenum, pagesize);
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,user.getName());
        Object data = userService.list(lambdaQueryWrapper);
        System.out.println(data);
        if(userService.list(lambdaQueryWrapper).isEmpty()) return Result.fail("不存在匹配用户");
        return Result.succ(userService.list(lambdaQueryWrapper));*/
        IPage res=userService.page(page);
        return res;
    }

    @PostMapping("/api/login")
    public Result  login(String  username, String password) throws Exception {
        User user =userService.getByUsername(username);
        if(user.getUsername()==null) {
            //throw new Exception("账户不存在");
            return Result.fail("用户名或密码不正确");
        }
           else if (!(user.getPassword().equals(password))) {
            // throw new Exception("用户名或密码不正确");
            return Result.fail("用户名或密码不正确");
        }

           else {
            List list= menuService. lambdaQuery().like(Menu::getMenuright,user.getRoleid()).list();
            HashMap res=new HashMap();
            res.put("user",user);
            res.put("menu",list);
                return Result.succ(res);
             }
    }
    @PostMapping("/api/mange")
    public Result PermissionChange(String username){
        User user = userService.getByUsername(username);
        if(user.getRoleid()!=2) {
            user.setRoleid(1 - user.getRoleid());
            mod(user);
            return Result.succ(user);
        }
        else return Result.fail("false");
    }

    @PostMapping("/api/valid")
    public Result ValidChange(String username){
        User user = userService.getByUsername(username);
        System.out.println(user);
        user.setIsValid(1-user.getIsValid());
        mod(user);
        return Result.succ(user);
    }

    @PostMapping("/api/modname")
    public Result modName(String username,String name){
        User user = userService.getByUsername(username);
        user.setName(name);
        mod(user);
        return Result.succ(user);
    }

    @GetMapping("/api/byname")
    public Result getbyname(String name,long pagenum,long pagesize) {
//        Result result = Result.succ(userService.getByName(name));
//        result.setData(UserService.obtolist(result.getData()));
//        return Result.succ(result);

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        Page<User> page=new Page(pagenum,pagesize);
        IPage res1=userService.getBaseMapper().selectPage(page,queryWrapper);
        return Result.succ(res1);
    }

    @PostMapping("/api/modpassword")
    public Result modPassword(String username,String password,String newPassword) throws Exception {
        User user = userService.getByUsername(username);
        if(user.getPassword().equals(password))
        {
            if(password.equals(newPassword))
                throw new Exception("新密码不可与旧密码一致");
            user.setPassword(newPassword);
            mod(user);
        }
         else return Result.fail("原密码错误");
         return Result.succ(user);
    }

    @PostMapping("/api/modage")
    public Result modAge(String username,int age){
        User user = userService.getByUsername(username);
        user.setAge(age);
        mod(user);
        return Result.succ(user);
    }

    @PostMapping("/api/modsex")
    public Result modSex(String username,int sex){
        User user = userService.getByUsername(username);
        user.setSex(sex);
        mod(user);
        return Result.succ(user);
    }

    @PostMapping("/api/borrow")
    public ResultBook borrowBook(String Bookname,String BookAuthor, String username) throws Exception {
        System.out.println(Bookname);
        System.out.println(username);
        User user1 = userService.getByUsername(username);
        String string=Bookname+" "+BookAuthor;
        Library library2 = libraryService.getByBookname(string);
        System.out.println(library2);
        if(library2.getBookname()==null)
            return ResultBook.bookfail("该书不存在");
        if(user1.getBorrowbook()==null)
        {
            user1.setBorrowbook(Bookname);
            user1.setBorrowbookauthor(BookAuthor);
        }

        else
        {
            user1.setBorrowbook(Bookname+" "+user1.getBorrowbook());
            user1.setBorrowbookauthor(BookAuthor+" "+user1.getBorrowbookauthor());
        }

        library2.setRestnumber(library2.getRestnumber()-1);
        libraryService.updateById(library2);
        mod(user1);
        return ResultBook.booksuccess("借出成功","书名:"+Bookname+"  "+"作者:"+BookAuthor);
    }

    @PostMapping("/api/returnBook")
    public ResultBook returnBook(String username,String bookname, String book_author) throws Exception {
        System.out.println(username);
        System.out.println(bookname);
        System.out.println(book_author);
        User user = userService.getByUsername(username);
        String string=bookname+" "+book_author;
        Library library2 = libraryService.getByBookname(string);
        String[] str_name =user.getBorrowbook().split(" ");
        String[] str_author =user.getBorrowbookauthor().split(" ");
        if(str_author[str_name.length-1].equals(book_author)&&str_name[str_name.length-1].equals(bookname)){
            str_author[str_name.length-1]="";
            str_name[str_name.length-1]="";
            user.setBorrowbook(String.join(" ",str_name));
            user.setBorrowbookauthor(String.join(" ",str_author));
            mod(user);
            library2.setRestnumber(library2.getRestnumber()+1);
            libraryService.updateById(library2);
            return ResultBook.booksuccess("还书成功",library2);
        }
        for (int i=0;i<str_name.length-1;i++) {
            if(str_author[i].equals(book_author)&&str_name[i].equals(bookname)) {
                for (int j=i;j<str_name.length-1;j++) {
                    str_name[j] = str_name[j + 1];
                    str_author[j] = str_author[j + 1];
                }
                str_name[str_name.length-1]="";
                str_author[str_name.length-1]= "";
                user.setBorrowbook(String.join(" ", str_name));
                user.setBorrowbookauthor(String.join(" ", str_author));
                mod(user);
                library2.setRestnumber(library2.getRestnumber() + 1);
                libraryService.updateById(library2);
                System.out.println(i);
                return ResultBook.booksuccess("还书成功", library2);
            }

        }
     return ResultBook.bookfail("false");
    }

    @GetMapping("/api/userbook")
    public List getBook(){
        List<User> userbook1 =userService.list();
        List res=new ArrayList();
        for(int i=0;i<userbook1.size();i++){

            if(userbook1.get(i).getBorrowbook()!=null && !Objects.equals(userbook1.get(i).getBorrowbook(), "")){
            String book[]=userbook1.get(i).getBorrowbook().split(" ");
            String author[]= userbook1.get(i).getBorrowbookauthor().split(" ");
                List<userBook1> list1=new ArrayList();
            for(int j=0;j<author.length;j++){
                userBook1 j1=new userBook1();
                j1.setAuthor(author[j]);
                j1.setBook(book[j]);
                list1.add(j1);
                System.out.println(list1);

            }
                userBook userbook =userBook.booksuccess(userbook1.get(i).getId(),list1);
                res.add(userbook);
            }
            else {
                String book[]=null;
//                String author[]= null;
                userBook userbook =userBook.booksuccess(userbook1.get(i).getId(),book);
                res.add(userbook);
            }

        }
//        System.out.println(res);
        return res;
    }

//@GetMapping
//    public List getBySex(int sex)
//{
////    List<User> user =userService.getBySex;
//    return userService.getBySex(sex);
//}



}