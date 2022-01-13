package com.august.web;

import com.august.pojo.User;
import com.august.service.UserService;
import com.august.service.impl.UserServiceImpl;
import com.august.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-12-30   20:13
 */
public class UserServelet extends BaseServelet {

    private UserService userService = new UserServiceImpl();

    /**
     * 用户登录 servlet
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //判断是否已经注册
        if (userService.existsUserName(username)) {
            //已经注册,判断密码是否正确
            User user = userService.longin(new User(username, password));
            if (user == null) {
                //登录失败,页面回显 ,保存到 request 中
                req.setAttribute("username", username);
                req.setAttribute("msg", "用户名或者密码错误");
                System.out.println("[" + username + "]密码错误");
                req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            } else {
                // 设置session
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
            }
        } else {
            // 用户未注册
            req.setAttribute("username", username);
            req.setAttribute("msg", "该用户名未注册,请前往注册!!");
            System.out.println("[" + username + "]用户未注册");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }
    }

    /**
     * 注销操作
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.销毁 Session
        req.getSession().invalidate();
        // 2.重定向首页
        resp.sendRedirect(req.getContextPath());
    }

    /**
     * 用户注册 servlet
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1.获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        User user = WebUtils.copyParametersToBean(req.getParameterMap(), new User());
        // 2. 检查验证码是否正确
        // 2.1获取谷歌验证码
        String googleCode = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 2.2 删除验证码Session
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        if (googleCode != null && googleCode.equalsIgnoreCase(code)) {
            //正确
            // 3.检查用户名是否可用
            if (userService.existsUserName(username)) {
                //不可用
                //注册失败页面回显
                req.setAttribute("msg", "用户名已存在!!");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                System.out.println("用户名[" + username + "]已存在");

                //跳转注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                //可用
                //调用 Service 保存到数据库
                userService.registerUser(new User(username, password, email));
                //跳转到注册成功页面
                // 设置session
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }

        } else {
            //不正确
            //验证码错误页面回显
            req.setAttribute("msg", "验证码错误!!");
            req.setAttribute("username", username);
            req.setAttribute("password", password);
            req.setAttribute("email", email);
            System.out.println("验证码[" + code + "]不正确");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

    /**
     * 验证用户名是否可用
     *
     * @param req
     * @param resp
     */
    protected void ValidExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        if (userService.existsUserName(username)) {
            resp.getWriter().write("true");
            return;
        }
        resp.getWriter().write("false");
    }

}
