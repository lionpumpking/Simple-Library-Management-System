package com.myproject.my_demo.demos.common;

import lombok.Data;

/**
 * Description:
 * Author:
 * Data:
 */
@Data
public class userBook1 {
private String book;
private String author;
//private Object author;

   public static userBook1 booksuccess(String book,String author) {
      return resultBook(book , author );
   }

   private static userBook1 resultBook(String book , String author)
   {
      userBook1 res = new userBook1();
      res.setAuthor(author);
      res.setBook(book);
//      res.setAuthor(author);
      return res;
   }
}