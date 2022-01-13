package com.august.web;

import com.august.pojo.Book;
import com.august.pojo.Page;
import com.august.service.BookService;
import com.august.service.impl.BookServiceImpl;
import com.august.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-07   14:43
 */
public class BookServelet extends BaseServelet {

    private BookService bookService = new BookServiceImpl();

    /**
     * 添加图书
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = WebUtils.copyParametersToBean(req.getParameterMap(), new Book());
//        添加图书
        bookService.addBook(book);
        // 3. 请求转发(一次请求) 使用getRequestDispatcher 会发生表单重复提交  默认路径到工程下
//        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
//        重定向 (两次请求) 默认路径到端口号
        resp.sendRedirect(req.getContextPath() + "/manage/bookServelet?action=list");
    }

    /**
     * 删除图书
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tid = req.getParameter("id");
        Integer id = Integer.valueOf(tid);
//        int id = Integer.parseInt(tid);
        bookService.deleteBookById(id);
        resp.sendRedirect(req.getContextPath() + "/manage/bookServelet?action=pages");

    }

    /**
     * 修改更新图书
     *
     * @param req
     * @param resp
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取图书信息
        Book book = WebUtils.copyParametersToBean(req.getParameterMap(), new Book());
        //保存
        bookService.updateBook(book);
        //请求重定向
        resp.sendRedirect(req.getContextPath() + "/manage/bookServelet?action=pages");
    }

    /**
     * 获取图书列表
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.查询图书
        List<Book> books = bookService.queryBook();
        // 2. 保存数据到request域中
        req.setAttribute("books", books);
        // 3. 请求转发
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);

//        books.forEach(System.out::println);
    }

    /**
     * 获取要修改的图书信息
     *
     * @param req
     * @param resp
     */
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tid = req.getParameter("id");
        Integer id = Integer.valueOf(tid);
        Book book = bookService.queryBookById(id);
        req.setAttribute("book", book);
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req, resp);
    }


    /**
     * 分页处理
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理分页
        //获取参数
        Integer pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        Integer pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUrl("manage/bookServelet?action=pages");
        req.setAttribute("page", page);
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);

    }


}
