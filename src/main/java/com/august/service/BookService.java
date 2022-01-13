package com.august.service;

import com.august.pojo.Book;
import com.august.pojo.Page;

import java.util.List;

public interface BookService {

    /**
     * 添加图书
     * @param book
     */
    void addBook(Book book);

    /**
     * 通过id删除图书
     * @param id
     */
    void deleteBookById(Integer id);

    /**
     * 修改图书
     * @param book
     */
    void updateBook(Book book);

    /**
     * 查询图书
     * @param id
     */
    Book queryBookById(Integer id);

    /**
     * 查询所有图书
     * @return
     */
    List<Book> queryBook();

    /**
     * 查询分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Book> page(Integer pageNo, Integer pageSize);


    /**
     * 通过价格区间查询图书
     * @param pageNo
     * @param pageSize
     * @param minPrice
     * @param maxPrice
     * @return
     */
    Page<Book> pageByPrice(Integer pageNo, Integer pageSize, Integer minPrice, Integer maxPrice);

}
