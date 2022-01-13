package com.august.dao.impl;

import com.august.dao.BookDao;
import com.august.pojo.Book;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-07   14:03
 */
public class BookDaoImpl extends BaseDao implements BookDao {

    /**
     * 添加图书
     *
     * @param book
     * @return
     */
    @Override
    public int addBook(Book book) {
        String sql = "INSERT INTO t_book(`name`,`price`,`author`,`sales`,`stock`,`img_path`)values(?,?,?,?,?,?)";
        return update(sql, book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getImgPath());
    }

    /**
     * 删除图书
     *
     * @param id
     * @return
     */
    @Override
    public int deleteBookbyId(Integer id) {
        String sql = "delete from t_book where `id` = ?";
        return update(sql, id);
    }

    /**
     * 修改图书
     *
     * @param book
     * @return
     */
    @Override
    public int updateBook(Book book) {
        String sql = "update t_book set `name` = ?,`price` = ?,`author` = ?,`sales` = ?,`stock` = ?,`img_path` = ? where `id` = ?";
        return update(sql, book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    /**
     * 通过id查询图书
     *
     * @param id
     * @return
     */
    @Override
    public Book queryBookById(Integer id) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book where `id` = ?";
        return queryForOne(Book.class, sql, id);
    }

    /**
     * 查询所以的图书
     *
     * @return
     */
    @Override
    public List<Book> queryBooks() {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book";
        return queryForList(Book.class, sql);
    }

    @Override
    public Integer queryForBookPageTotal() {
        String sql = "SELECT count(*) from t_book";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Book> queryBookForItems(int begin, Integer pageSize) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book limit ?,?";
        return queryForList(Book.class, sql,begin,pageSize);
    }

    /**
     * 查询价格区间记录数
     *
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public Integer queryForBookPageTotalByPrice(Integer minPrice, Integer maxPrice) {
        String sql = "SELECT count(*) from t_book where price between ? and ?";
        Number count = (Number) queryForSingleValue(sql,minPrice,maxPrice);
        return count.intValue();
    }

    /**
     * 查询价格区间图书
     *
     * @param begin
     * @param pageSize
     * @param minxPrice
     * @param maxPrice
     * @return
     */
    @Override
    public List<Book> queryBookForItemsByPrice(int begin, Integer pageSize, Integer minxPrice, Integer maxPrice) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book where price between ? and ? limit ?,?";
        return queryForList(Book.class, sql,minxPrice,maxPrice,begin,pageSize);
    }
}
