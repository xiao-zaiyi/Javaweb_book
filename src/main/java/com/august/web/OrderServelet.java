package com.august.web;

import com.august.pojo.Car;
import com.august.pojo.User;
import com.august.service.OrderService;
import com.august.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-11   23:22
 */
public class OrderServelet extends BaseServelet{
        private OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     * @param req
     * @param resp
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取Car
        Car car = (Car) req.getSession().getAttribute("cart");
        // 获取userId
        User loginUser = (User) req.getSession().getAttribute("user");
        if(loginUser == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer id = loginUser.getId();
        String order = orderService.createOrder(car, id);
        req.setAttribute("order", order);
        req.getSession().setAttribute("order",order);
        //重定向到结算页面
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
        /**
     * 查看所以订单信息
     * @param req
     * @param resp
     */
    protected void showAllOrders(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * 发货
     * @param req
     * @param resp
     */
    protected void sendOrder(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * 查看订单详情
     * @param req
     * @param resp
     */
    protected void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * 查看我的订单
     * @param req
     * @param resp
     */
    protected void showMyOrders(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * 签收订单/确认收货
     * @param req
     * @param resp
     */
    protected void receiverOrder(HttpServletRequest req, HttpServletResponse resp) {

    }

}
