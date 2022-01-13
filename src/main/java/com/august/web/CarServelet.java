package com.august.web;

import com.august.pojo.Book;
import com.august.pojo.Car;
import com.august.service.BookService;
import com.august.service.impl.BookServiceImpl;
import com.august.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-11   17:15
 */
public class CarServelet extends BaseServelet {
    private BookService bookService = new BookServiceImpl();

    /**
     * 添加商品
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求的参数商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //获取当前地址
        String referer = req.getHeader("Referer");
        //调用bookService.queryBookByid(id) : Book得到圉书的信息
        Book book = bookService.queryBookById(id);
        //调用cart.addItem(cartItem);添加商品项 //把图书信息，转换成为CartItem商品项
        Car car = (Car) req.getSession().getAttribute("cart");
        if (car == null) {
            car = new Car();
            //设置Session
            req.getSession().setAttribute("cart", car);
        }
        Car.CarItem carItem = new Car.CarItem(book.getId(), 1, book.getName(), book.getPrice(), book.getPrice());
        car.addItem(carItem);
        // 把最后添加的商品添加到Session域中
        req.getSession().setAttribute("lastName", carItem.getName());
        //重定向回商品列表页面
        resp.sendRedirect(referer);
    }

    /**
     * 删除购物车商品项
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求的参数商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        Car car = (Car) req.getSession().getAttribute("cart");
        if (car != null) {
            car.removeItem(id);
        }
        //获取当前地址
        String referer = req.getHeader("Referer");
        //重定向回商品列表页面
        resp.sendRedirect(referer);
    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Car car = (Car) req.getSession().getAttribute("cart");
        if (car != null){
            car.clear();
        }
        //获取当前地址
        String referer = req.getHeader("Referer");
        //重定向回商品列表页面
        resp.sendRedirect(referer);
    }

    /**
     * 更新商品数量
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        Integer count = Integer.valueOf(req.getParameter("itemNum"));
        Car car = (Car) req.getSession().getAttribute("cart");
        if (car != null){
            car.updateCount(id,count);
            //获取当前地址
            String referer = req.getHeader("Referer");
            //重定向回商品列表页面
            resp.sendRedirect(referer);
        }

    }
}
