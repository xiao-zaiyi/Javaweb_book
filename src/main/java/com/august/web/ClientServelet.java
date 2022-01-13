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

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-10   15:37
 */
public class ClientServelet extends BaseServelet {

    private BookService bookService = new BookServiceImpl();

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
        Integer pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        Integer pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUrl("client/bookServelet?action=pages");
        req.setAttribute("page", page);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

    /**
     * 价格查询
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pagesByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数
        Integer pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        Integer pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        Integer minPrice = WebUtils.parseInt(req.getParameter("min"), 0);
        Integer maxPrice = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);
        req.setAttribute("minPrice", minPrice);
        req.setAttribute("maxPrice", maxPrice);
        Page<Book> pageByPrice = bookService.pageByPrice(pageNo, pageSize, minPrice, maxPrice);
        pageByPrice.setUrl("client/bookServelet?action=pagesByPrice&min=" + minPrice + "&max=" + maxPrice);
        req.setAttribute("page", pageByPrice);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

}
