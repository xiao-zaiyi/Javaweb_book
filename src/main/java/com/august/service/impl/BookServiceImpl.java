package com.august.service.impl;

import com.august.dao.BookDao;
import com.august.dao.impl.BookDaoImpl;
import com.august.pojo.Book;
import com.august.pojo.Page;
import com.august.service.BookService;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-07   15:10
 */
public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    /**
     * 添加图书
     *
     * @param book
     */
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    /**
     * 通过id删除图书
     *
     * @param id
     */
    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookbyId(id);
    }

    /**
     * 修改图书
     *
     * @param book
     */
    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    /**
     * 查询图书
     *
     * @param id
     */
    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    /**
     * 查询所有图书
     *
     * @return
     */
    @Override
    public List<Book> queryBook() {
        return bookDao.queryBooks();
    }

    /**
     * 查询分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Book> page(Integer pageNo, Integer pageSize) {
        Page<Book> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        // 设置总记录数
        Integer pageTotalCount = bookDao.queryForBookPageTotal();
        page.setPageTotalCount(pageTotalCount);

        // 页码数
        int pageTotal = pageTotalCount / page.getPageSize();

        if (pageTotal % page.getPageSize() > 0) {
            pageTotal += 1;
        }
        page.setPageTotal(pageTotal);

        // 设置当前页码
        page.setPageNo(pageNo);
        int begin = (pageNo - 1) * pageSize;
        // 当前页数据
        List<Book> items = bookDao.queryBookForItems(begin, pageSize);
        page.setItems(items);
        return page;
    }

    /**
     * 通过价格区间查询图书
     *
     * @param pageNo
     * @param pageSize
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public Page<Book> pageByPrice(Integer pageNo, Integer pageSize, Integer minPrice, Integer maxPrice) {
        Page<Book> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        // 设置总记录数
        Integer pageTotalCount = bookDao.queryForBookPageTotalByPrice(minPrice,maxPrice);
        page.setPageTotalCount(pageTotalCount);
        // 页码数
        int pageTotal = pageTotalCount / page.getPageSize();

        if (pageTotal % page.getPageSize() > 0) {
            pageTotal += 1;
        }
        page.setPageTotal(pageTotal);

        // 设置当前页码
        page.setPageNo(pageNo);
        int begin = (pageNo - 1) * pageSize;
        // 当前页数据
        List<Book> items = bookDao.queryBookForItemsByPrice(begin, pageSize,minPrice,maxPrice);

        page.setItems(items);

        return page;
    }
}
