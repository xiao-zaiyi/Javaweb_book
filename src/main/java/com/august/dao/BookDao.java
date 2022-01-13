package com.august.dao;

import com.august.pojo.Book;

import java.util.List;

public interface BookDao {

    /**
     * 添加图书
     * @param book
     * @return
     */
    int addBook(Book book);

    /**
     * 删除图书
     * @param id
     * @return
     */
    int deleteBookbyId(Integer id);

    /**
     * 修改图书
     * @param book
     * @return
     */
    int updateBook(Book book);

    /**
     * 通过id查询图书
     * @param id
     * @return
     */
    Book queryBookById(Integer id);

    /**
     * 查询所以的图书
     * @return
     */
    List<Book> queryBooks();

    /**
     * 查询所有图书记录数
     * @return
     */
    Integer queryForBookPageTotal();

    /**
     * 分页数据
     * @param begin
     * @param pageSize
     * @return
     */
    List<Book> queryBookForItems(int begin, Integer pageSize);

    /**
     * 查询价格区间记录数
     * @param minPrice
     * @param maxPrice
     * @return
     */
    Integer queryForBookPageTotalByPrice(Integer minPrice, Integer maxPrice);

    /**
     * 查询价格区间图书
     * @param begin
     * @param pageSize
     * @param maxPrice
     * @param maxPrice1
     * @return
     */
    List<Book> queryBookForItemsByPrice(int begin, Integer pageSize, Integer maxPrice, Integer maxPrice1);

}
