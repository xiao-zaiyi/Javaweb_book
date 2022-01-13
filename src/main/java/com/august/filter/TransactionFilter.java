package com.august.filter;

import com.august.utils.JdbcUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 数据库事物过滤
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-12   22:33
 */
public class TransactionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JdbcUtils.commitAndClose(); //提交事物
        } catch (Exception e) {
            e.printStackTrace();
            JdbcUtils.RollbackAndClose(); //回滚事物
            String referer = req.getHeader("Referer");
            //重定向页面
            resp.sendRedirect(referer);
            throw new RuntimeException(e);
        }


    }
}
