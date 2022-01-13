package com.august.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-12-30   21:03
 */
public  abstract class  BaseServelet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        //这里代码一般是固定
        resp.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");

        String action = req.getParameter("action");
        // 方法一 普通方法
//        if ("regist".equals(action)) {
//            regist(req,resp);
//        } else  if ("login".equals(action)) {
//            login(req,resp);
//        }
        // 方法二 通过反射
        try {
            Method Method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            Method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        //这里代码一般是固定
        resp.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");

        String action = req.getParameter("action");
        // 方法一 普通方法
//        if ("regist".equals(action)) {
//            regist(req,resp);
//        } else  if ("login".equals(action)) {
//            login(req,resp);
//        }
        // 方法二 通过反射
        try {
            Method Method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            Method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
