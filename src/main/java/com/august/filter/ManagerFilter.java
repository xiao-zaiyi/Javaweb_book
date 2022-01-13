package com.august.filter;

import com.august.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * 管理员页面的过滤
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-12   21:15
 */
public class ManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        //表示用户未登录
        if (user == null) {
            request.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
