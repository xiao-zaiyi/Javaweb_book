package com.august.test;

import com.august.dao.BookDao;
import com.august.dao.impl.BookDaoImpl;
import com.august.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;


public class BookDaoTest {
    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null, "肖哥的幸福生活", "肖哥", new BigDecimal(9999), 999999999, 99, null));
    }

    @Test
    public void deleteBookbyId() {
        bookDao.deleteBookbyId(11);
    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(148, "灰太狼的幸福生活", "肖哥", new BigDecimal(9999), 999999999, 99, null));
    }

    @Test
    public void queryBookById() {
        Book book = bookDao.queryBookById(22);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        bookDao.queryBooks().forEach(System.out::println);
    }

    @Test
    public void queryForBookPageTotal() {
        Integer integer = bookDao.queryForBookPageTotal();
        System.out.println(integer.intValue());
    }

    @Test
    public void queryBookForItems() {
        List<Book> items = bookDao.queryBookForItems(1, 5);
        items.forEach(System.out::println);
    }
    @Test
    public void queryForBookPageTotalByPrice() {
        Integer integer = bookDao.queryForBookPageTotalByPrice(1, 10);
        System.out.println(integer);
    }
    @Test
    public void queryBookForItemsByPrice() {
        List<Book> items = bookDao.queryBookForItemsByPrice(1, 4,80,999);
        items.forEach(System.out::println);
    }

}