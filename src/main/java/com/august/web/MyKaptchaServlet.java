package com.august.web;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-11   13:11
 */
public class MyKaptchaServlet extends HttpServlet implements Servlet {
    private Properties props = new Properties();
    private Producer kaptchaProducer = null;
    private String sessionKeyValue = null;
    private String sessionKeyDateValue = null;

    public MyKaptchaServlet() {
    }

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ImageIO.setUseCache(false);
        Enumeration initParams = conf.getInitParameterNames();

        while(initParams.hasMoreElements()) {
            String key = (String)initParams.nextElement();
            String value = conf.getInitParameter(key);
            this.props.put(key, value);
        }

        Config config = new Config(this.props);
        this.kaptchaProducer = config.getProducerImpl();
        this.sessionKeyValue = config.getSessionKey();
        this.sessionKeyDateValue = config.getSessionDate();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setDateHeader("Expires", 0L);
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setContentType("image/jpeg");
        String capText = this.kaptchaProducer.createText();
        req.getSession().setAttribute(this.sessionKeyValue, capText);
        req.getSession().setAttribute(this.sessionKeyDateValue, new Date());
        BufferedImage bi = this.kaptchaProducer.createImage(capText);
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(bi, "jpg", out);
    }
}
