package com.august.test;

import com.august.pojo.Book;
import com.august.pojo.Page;
import com.august.service.BookService;
import com.august.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"小红花","景墙",new BigDecimal(1111),8888,999,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(148,"小红花","景墙",new BigDecimal(1111),8888,999,null));
    }

    @Test
    public void queryBookById() {
        bookService.queryBookById(7);
    }

    @Test
    public void queryBook() {
        bookService.queryBook().forEach(System.out::println);
    }

    @Test
    public void pageByPrice() {
        Page<Book> bookPage = bookService.pageByPrice(1, 4, 1, 9999);
        System.out.println(bookPage);

    }
}