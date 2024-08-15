package com.myproject.my_demo.demos.common;

import lombok.Data;

/**
 * Description:
 * Author:
 * Data:
 */
@Data
public class userBook {
private int id;
private Object book;
//private Object author;

   public static userBook booksuccess(int id, Object book) {
      return resultBook(id , book );
   }

   private static userBook resultBook(int id, Object book)
   {
      userBook res = new userBook();
      res.setId(id);
      res.setBook(book);
//      res.setAuthor(author);
      return res;
   }
}