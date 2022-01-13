# Java尚硅谷书城项目

1. 创建一个java项目,配置好Tomcat服务器(见博客[JavaWeb 之 IDEA21.2版本中部署 Tomcat](https://blog.csdn.net/weixin_45454773/article/details/122475752) )

2. 创建JavaEE三层架构对应的包

   ![img](https://gitee.com/xiao-zaiyi/blog-images/raw/master/blog-images/images1/1591648-20190122101125063-745112787.png)

   一般需要创建的包：
       web层：

   		com.august.web/servlet/controller
   	service层：
   	 	com.august.service（Service接口包）
   	 	com.august.service.impl（Service接口实现类）
   	dao持久层：
   	    com.august.dao（Dao接口包）
   	    com.august.dao.impl（Dao接口实现类）
   	实体bean对象：
   		com.august.pojo/entity/domain/bean（JavaBean类）
   	测试包：
   		com.august.test/junit
   	工具类：
   		com.august.utils
   
   ![image-20211129204041020](https://gitee.com/xiao-zaiyi/blog-images/raw/master/blog-images/images1/image-20211129204041020.png)
   

## 1.公共类、工具类提取

**1.Dao层:**

BaseDao.java

```java
package com.august.dao.impl;

import com.august.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-11-28   22:10
 */
public abstract class BaseDao {

    //使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * update 用来执行insert/update/delete
     *
     * @return 返回-1说明执行失败
     */
    public int update(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.update(conn, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询返回一个javaBean的sql对象
     *
     * @param type 返回的对象类型
     * @param sql  执行的sql语句
     * @param args sql对应的参数值
     * @param <T>  返回的类型泛型
     * @return
     */
    public <T> T queryForOne(Class<T> type, String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询返回多个javaBean的sql对象
     *
     * @param type 返回的对象类型
     * @param sql  执行的sql语句
     * @param args sql对应的参数值
     * @param <T>  返回的类型泛型
     * @return
     */

    public <T> List<T> queryForList(Class<T> type, String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanListHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 执行返回一行一列的数据
     *
     * @param sql
     * @param args
     * @return
     */
    public Object queryForSingleValue(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

```

**2.Web层**

BaseServelet.java

```java
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

```

**3.工具类:**

**JdbcUtils.java**

```java
package com.august.dao.impl;

import com.august.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-11-28   22:10
 */
public abstract class BaseDao {

    //使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * update 用来执行insert/update/delete
     *
     * @return 返回-1说明执行失败
     */
    public int update(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.update(conn, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询返回一个javaBean的sql对象
     *
     * @param type 返回的对象类型
     * @param sql  执行的sql语句
     * @param args sql对应的参数值
     * @param <T>  返回的类型泛型
     * @return
     */
    public <T> T queryForOne(Class<T> type, String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询返回多个javaBean的sql对象
     *
     * @param type 返回的对象类型
     * @param sql  执行的sql语句
     * @param args sql对应的参数值
     * @param <T>  返回的类型泛型
     * @return
     */

    public <T> List<T> queryForList(Class<T> type, String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanListHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 执行返回一行一列的数据
     *
     * @param sql
     * @param args
     * @return
     */
    public Object queryForSingleValue(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

```

**WebUtils.java**

```java
package com.august.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-12-30   21:40
 */
public class WebUtils {
    /**
     * 把 Map 的值注入到javaBean中
     *
     * @param value
     * @param bean
     * @return
     */

    public static <T> T copyParametersToBean(Map value, T bean) {
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

    public static int parseInt(String StrInt, int defaultValue) {
        if(StrInt == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(StrInt);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

}

```



## 2.User用户类

1.建立对应数据库和表

   ```mysql
   CREATE DATABASE book;
   
   USE book;
   
   CREATE TABLE IF NOT EXISTS  t_user(
   	`id` INT PRIMARY KEY auto_increment,
   	`username` VARCHAR(20) NOT NULL UNIQUE,
   	`password` VARCHAR(32) NOT NULL,
   	`email`	VARCHAR(200)
   	);
   	
   	INSERT INTO t_user(username,`password`,email)
   	VALUES("admin","123456","admin@qq.com");
   ```

2. 编写数据库对应的 User JavaBean 对象

   **User.java**
```java
package com.august.pojo;

/**
    * @author : Crazy_August
    * @description :
    * @Time: 2021-11-28   20:57
    */
public class User {

    private Integer id;
    private  String username;
    private String password;
    private String email;

    public User() {
    }

    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}

```

3. 编写UserDao接口

   **UserDao.java(接口)**
   
```java
package com.august.dao;

import com.august.pojo.User;

public interface UserDao {

    // 根据用户名查询用户信息  根据用户名和密码查询用户信息  保存用户信息

    /**
        * 根据用户名查询用户信息
        *
        * @param userName
        * @return 返回null说明没有这个用户
        */
    User queryUserByUsername(String userName) ;

    /**
        *  根据用户名和密码查询用户信息
        * @param userName
        * @param password
        * @return 返回null说明用户或者密码错误
        */
    User queryUserByUsernameAndPassword(String userName, String password);

    /**
        * 保存用户信息
        * @param user
        * @return
        */
    int saveUser(User user);

}
   
```

4. UserDaoImpl实现类

   **UserDaoImpl.java**

```java
package com.august.dao;

import com.august.dao.impl.UserDao;
import com.august.pojo.User;

/**
    * @author : Crazy_August
    * @description :
    * @Time: 2021-11-28   22:58
    */
public class UserDaoImpl extends BaseDao implements UserDao {

    /**
        * 根据用户名查询用户信息
        *
        * @param userName
        * @return 返回null说明没有这个用户
        */
    @Override
    public User queryUserByUsername(String userName) {
        String sql = "select `id` ,`username`,`password`,`email` from t_user where username = ?";
        return queryForOne(User.class, sql, userName);
    }

    /**
        * 根据用户名和密码查询用户信息
        *
        * @param userName
        * @param password
        * @return 返回null说明用户或者密码错误
        */
    @Override
    public User queryUserByUsernameAndPassword(String userName, String password) {
        String sql = "select `id` ,`username`,`password`,`email` from t_user where username = ? and password = ?";
        return queryForOne(User.class, sql, userName, password);
    }

    /**
        * 保存用户信息
        *
        * @param user
        * @return
        */
    @Override
    public int saveUser(User user) {
        String sql = "INSERT INTO t_user(`username`,`password`,`email`)values(?,?,?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}

```

5. 编写UserService层

   **UserService.java(接口)**

```java
    package com.august.service;
    
    import com.august.pojo.User;
    
    public interface UserService {
    
        //登录 注册 检查用户名是否存在三个业务
    
        /**
         * 注册用户
         * @param user
         */
        void registerUser(User user);
    
        /**
         * 登录
         * @param user
         * @return 返回null登录失败,
         *         返回有值,登录成功
         * */
        User longin(User user);
    
    
        /**
         * 检查是否有用户名
         * @param usernane
         * @return 返回turn说明已经存在不可用
         */
        boolean existsUserName(String usernane);
    
    }
    
    
```

6. 编写UserServiceImpl接口实现类

   **UserServiceImpl.java**

```java
package com.august.service.impl;

import com.august.dao.UserDao;
import com.august.dao.impl.UserDaoImpl;
import com.august.pojo.User;
import com.august.service.UserService;

/**
     * @author : Crazy_August
     * @description :
     * @Time: 2021-11-29   20:03
     */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
         * 注册用户
         *
         * @param user
         */
    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    /**
         * 登录
         *
         * @param user
         * @return 返回null登录失败,
         * 返回有值,登录成功
         */
    @Override
    public User longin(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    /**
         * 检查是否有用户名
         *
         * @param usernane
         * @return 返回turn说明已经存在不可用
         */
    @Override
    public boolean existsUserName(String usernane) {
        if (userDao.queryUserByUsername(usernane) != null){
            return true;
        }
        return false;
    }
}

```

7. 编写用户注册页面

    分析:
    
    ```mermaid
    graph LR;
    A[填写信息]--->B[前端校验填写内容]--->C[发送到Servelet]--->D{SQL查询用户名}--Y-->E[页面回显用户名不可用]
    D{SQL查询用户名}--N-->F[保存用户信息]--->H[跳转注册成功页面]
    ```
    
```java
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
```

**其余的 登录和注册等功能类似**,就不说明分析

8. Web层的 UserServlet 完整代码:

   **UserServelet.java**

```java
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
     * 验证用户名是否可用 (用户Ajax信息校验)
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

```



## 3.Book类

1. 建立对应数据库和表

```mysql
CREATE TABLE `t_book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `sales` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `img_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
```

2. 编写数据库对应的 Book JavaBean对象

   **Book.java**

```java
package com.august.pojo;

import java.math.BigDecimal;
import java.util.concurrent.PriorityBlockingQueue;

/**
    * @author : Crazy_August
    * @description :
    * @Time: 2022-01-07   13:52
    */
public class Book {

    private Integer id;
    private String name;
    private String author;
    private BigDecimal price;
    private int sales;
    private int stock;
    private String imgPath = "static/img/default.jpg";

    public Book() {
    }

    public Book(Integer id, String name, String author, BigDecimal price, int sales, int stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        if (imgPath!= null && !"".equals(imgPath)) {
            this.imgPath = imgPath;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgPath() {

        return imgPath;
    }

    public void setImgPath(String imgPath) {
        if (imgPath!= null && !"".equals(imgPath)) {
            this.imgPath = imgPath;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", author='" + author + '\'' +
            ", price=" + price +
            ", sales=" + sales +
            ", stock=" + stock +
            ", imgPath='" + imgPath + '\'' +
            '}';
    }
}

```

3. 编写BookDao接口

   **BookDao.java**

```java
package com.august.dao;

import com.august.pojo.Book;

import java.util.List;

public interface BookDao {

    /**
        * 添加图书
        * @param book
        * @return
        */
    int addBook(Book book);

    /**
        * 删除图书
        * @param id
        * @return
        */
    int deleteBookbyId(Integer id);

    /**
        * 修改图书
        * @param book
        * @return
        */
    int updateBook(Book book);

    /**
        * 通过id查询图书
        * @param id
        * @return
        */
    Book queryBookById(Integer id);

    /**
        * 查询所以的图书
        * @return
        */
    List<Book> queryBooks();

    /**
        * 查询所有图书记录数
        * @return
        */
    Integer queryForBookPageTotal();

    /**
        * 分页数据
        * @param begin
        * @param pageSize
        * @return
        */
    List<Book> queryBookForItems(int begin, Integer pageSize);

    /**
        * 查询价格区间记录数
        * @param minPrice
        * @param maxPrice
        * @return
        */
    Integer queryForBookPageTotalByPrice(Integer minPrice, Integer maxPrice);

    /**
        * 查询价格区间图书
        * @param begin
        * @param pageSize
        * @param maxPrice
        * @param maxPrice1
        * @return
        */
    List<Book> queryBookForItemsByPrice(int begin, Integer pageSize, Integer maxPrice, Integer maxPrice1);

}

```

4. BookDaoImpl实现类

   **BookDaoImpl.java**

```java
package com.august.dao.impl;

import com.august.dao.BookDao;
import com.august.pojo.Book;

import java.util.List;

/**
    * @author : Crazy_August
    * @description :
    * @Time: 2022-01-07   14:03
    */
public class BookDaoImpl extends BaseDao implements BookDao {

    /**
        * 添加图书
        *
        * @param book
        * @return
        */
    @Override
    public int addBook(Book book) {
        String sql = "INSERT INTO t_book(`name`,`price`,`author`,`sales`,`stock`,`img_path`)values(?,?,?,?,?,?)";
        return update(sql, book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getImgPath());
    }

    /**
        * 删除图书
        *
        * @param id
        * @return
        */
    @Override
    public int deleteBookbyId(Integer id) {
        String sql = "delete from t_book where `id` = ?";
        return update(sql, id);
    }

    /**
        * 修改图书
        *
        * @param book
        * @return
        */
    @Override
    public int updateBook(Book book) {
        String sql = "update t_book set `name` = ?,`price` = ?,`author` = ?,`sales` = ?,`stock` = ?,`img_path` = ? where `id` = ?";
        return update(sql, book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    /**
        * 通过id查询图书
        *
        * @param id
        * @return
        */
    @Override
    public Book queryBookById(Integer id) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book where `id` = ?";
        return queryForOne(Book.class, sql, id);
    }

    /**
        * 查询所以的图书
        *
        * @return
        */
    @Override
    public List<Book> queryBooks() {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book";
        return queryForList(Book.class, sql);
    }

    @Override
    public Integer queryForBookPageTotal() {
        String sql = "SELECT count(*) from t_book";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Book> queryBookForItems(int begin, Integer pageSize) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book limit ?,?";
        return queryForList(Book.class, sql,begin,pageSize);
    }

    /**
        * 查询价格区间记录数
        *
        * @param minPrice
        * @param maxPrice
        * @return
        */
    @Override
    public Integer queryForBookPageTotalByPrice(Integer minPrice, Integer maxPrice) {
        String sql = "SELECT count(*) from t_book where price between ? and ?";
        Number count = (Number) queryForSingleValue(sql,minPrice,maxPrice);
        return count.intValue();
    }

    /**
        * 查询价格区间图书
        *
        * @param begin
        * @param pageSize
        * @param minxPrice
        * @param maxPrice
        * @return
        */
    @Override
    public List<Book> queryBookForItemsByPrice(int begin, Integer pageSize, Integer minxPrice, Integer maxPrice) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` `imgPath` from t_book where price between ? and ? limit ?,?";
        return queryForList(Book.class, sql,minxPrice,maxPrice,begin,pageSize);
    }
}

```

5. 编写BookService层

   **BookService.java(接口)**

```java
package com.august.service;

import com.august.pojo.Book;
import com.august.pojo.Page;

import java.util.List;

public interface BookService {

    /**
        * 添加图书
        * @param book
        */
    void addBook(Book book);

    /**
        * 通过id删除图书
        * @param id
        */
    void deleteBookById(Integer id);

    /**
        * 修改图书
        * @param book
        */
    void updateBook(Book book);

    /**
        * 查询图书
        * @param id
        */
    Book queryBookById(Integer id);

    /**
        * 查询所有图书
        * @return
        */
    List<Book> queryBook();

    /**
        * 查询分页
        * @param pageNo
        * @param pageSize
        * @return
        */
    Page<Book> page(Integer pageNo, Integer pageSize);


    /**
        * 通过价格区间查询图书
        * @param pageNo
        * @param pageSize
        * @param minPrice
        * @param maxPrice
        * @return
        */
    Page<Book> pageByPrice(Integer pageNo, Integer pageSize, Integer minPrice, Integer maxPrice);

}

```

6. 编写BookServiceImpl接口实现类

   **BookServiceImpl.java**

```java
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
   
```

7.BookServelet层

**BookServelet.java**

```java
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

```



## 4.Page 分页类

页面分析:

分页一般由下面几个组成:

pageNo			当前页码  				由客户端传递

pageTotalCount   	 总记录数			可以通过sql查询 `select count(*) from 表名;`

pageTotal		   	  页码总数 			总记录数 = 页码总数  / 每条显示的数量 

```html
注:页码总数%每条显示的数量>0时,	页码总数+1
```

pageSize		  每条显示的数量		1.客户端觉得 2.页码布局影响

items				当前页的数据			Sql语句: `select * from 表名 limit begin , pageSize`

```t
begin = (pageNo - 1) * pageSize
```



1. 建立对应数据库和表

​	分页类也是操作Book数据,SQl不需要建表.

2. 编写数据库对应的JavaBean对象

   **Page.java**

```java
package com.august.pojo;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-09   14:21
 */
public class Page<T> {

    public static int PAGE_SIZE = 4;

    // 每条显示的数量
    private Integer pageSize = PAGE_SIZE;
    // 当前页码
    private Integer pageNo;
    //   页码总数
    private Integer pageTotal;
    //  总记录数
    private Integer pageTotalCount;
    // 当前页的数据
    private List<T> items;

    private String url;



    public Page() {
    }

    public Page(Integer pageSize, Integer pageNo, Integer pageTotal, Integer pageTotalCount, List<T> items) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageTotalCount = pageTotalCount;
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
//        数字边界检测
        if (pageNo < 0) {
            pageNo = 1;
        }
        if(pageNo > pageTotal){
            pageNo = pageTotal;
        }
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageTotalCount=" + pageTotalCount +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }
}

```

## 5.Car购物车类

1. 建立对应数据库和表

​	购物车类也是操作Boot图书,故也不需要建立表

2. 编写数据库对应的 Car JavaBean以及CarItem商品类对象

   **Car.java**

```java
package com.august.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
    * 购物车对象
    */
public class Car {

    private Map<Integer, CarItem> items = new LinkedHashMap<>();

    public Car() {
    }

    public Car(Map<Integer, CarItem> items) {
        this.items = items;
    }

    /**
        * 添加商品项
        * @param CarItem
        */
    public void addItem(CarItem CarItem) {
        // 判断购物车时候拥有相同的商品
        Car.CarItem carItem = items.get(CarItem.getId());
        if (carItem == null) {
            //之前购物车没有此商品
            items.put(CarItem.getId(), CarItem);
        } else {
            //数目加1
            carItem.setCount(carItem.getCount() + 1);
            // 总价格计算
            carItem.setTotalPrice(carItem.getPrice().multiply(new BigDecimal(carItem.getCount())));
        }


    }

    /**
        * 删除商品项
        *
        * @param id
        */
    public void removeItem(Integer id) {
        items.remove(id);
    }

    /**
        * 修改商品数量
        *
        * @param id
        * @param count
        */
    public void updateCount(Integer id, Integer count) {
        // 1. 先查看购物车中是否有此商品。如果有，修改商品数量，更新总金额
        // 判断购物车时候拥有相同的商品
        Car.CarItem carItem = items.get(id);
        if (carItem != null) {
            //修改商品数量
            carItem.setCount(count);
            // 总价格计算
            carItem.setTotalPrice(carItem.getPrice().multiply(new BigDecimal(carItem.getCount())));
        }
    }

    /**
        * 清空购物车
        */
    public void clear() {
        items.clear();
    }


    public Map<Integer, CarItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CarItem> items) {
        this.items = items;
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CarItem> entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CarItem> entry : items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getPrice().multiply(new BigDecimal(entry.getValue().getCount())));
        }
        return totalPrice;
    }


    @Override
    public String toString() {
        return "Car{" +
            "items=" + items +
            ", totalCount=" + getTotalCount() +
            ", totalPrice=" + getTotalPrice() +
            '}';
    }

    /**
        * 购物车商品项
        *
        * @author : Crazy_August
        * @description :
        * @Time: 2022-01-11   16:34
        */
    public static class CarItem {
        private Integer id;
        private Integer count;
        private String name;
        private BigDecimal price;
        private BigDecimal totalPrice;

        public CarItem() {
        }

        public CarItem(Integer id, Integer count, String name, BigDecimal price, BigDecimal totalPrice) {
            this.id = id;
            this.count = count;
            this.name = name;
            this.price = price;
            this.totalPrice = totalPrice;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "CarItem{" +
                "id=" + id +
                ", count=" + count +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
        }
    }

}

```

3. 编写CarServelet层

   **CarServelet.java**

```java
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

```

   

## 6.Order订单类

分析:

![image-20220111210126805](https://gitee.com/xiao-zaiyi/blog-images/raw/master/blog-images/images1/image-20220111210126805.png)

1. 建立对应数据库和表

```mysql
CREATE TABLE t_order(
	`order_id` VARCHAR(50) PRIMARY KEY,
	`create_time` datetime,
	`price` DECIMAL(11,2),
	`status` INT,
	`user_id` INT, 
	FOREIGN KEY(`user_id`) REFERENCES t_user(`id`)
);

CREATE TABLE t_order_item(
	`id` INT PRIMARY KEY auto_increment,
	`name` VARCHAR(100),
	`count` INT,
	`price`	DECIMAL(11,2),
	`total_price` DECIMAL(11,2),
	`order_id` VARCHAR(50),
	FOREIGN KEY(`order_id`) REFERENCES t_order(`order_id`)
);
```

2. 编写数据库对应的 Order JavaBean对象

```java
package com.august.pojo;

import java.math.BigDecimal;
import java.util.Date;


/** 订单模块
    * @author : Crazy_August
    * @description :
    * @Time: 2022-01-11   21:38
    */
public class Order {
    private String orderId;
    private Date createdTime;
    private BigDecimal  price;
    // 0 表示未发货, 1 表示发货, 2 表示已签收
    private Integer status = 0;
    private Integer userId;

    public Order() {
    }

    public Order(String orderId, Date createdTime, BigDecimal price, Integer status, Integer userId) {
        this.orderId = orderId;
        this.createdTime = createdTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = (java.sql.Date) createdTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderId='" + orderId + '\'' +
            ", createdTime=" + createdTime +
            ", price=" + price +
            ", status=" + status +
            ", userId=" + userId +
            '}';
    }

    /**
        * 订单项
        */
    public static class OrderItem{
        private Integer id;
        private String name;
        private Integer count;
        private BigDecimal price;
        private BigDecimal totalPrice;
        private String orderId;

        public OrderItem() {
        }

        public OrderItem(Integer id, String name, Integer count, BigDecimal price, BigDecimal totalPrice, String orderId) {
            this.id = id;
            this.name = name;
            this.count = count;
            this.price = price;
            this.totalPrice = totalPrice;
            this.orderId = orderId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public String toString() {
            return "orderItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", orderId='" + orderId + '\'' +
                '}';
        }
    }
}

```

3. 编写OrderDao接口

   **OrderDao.java**(接口)

```java
package com.august.dao;

import com.august.pojo.Order;


public interface OrderDao {

    /**
        * 保存订单
        * @param order
        * @return
        */
    int savaOrder(Order order);

    /**
        * 保存订单项
        * @param OrderItem
        * @return
        */
    int savaOrderItem(Order.OrderItem OrderItem);
}

```

4. UserDaoImpl实现类

```java
package com.august.dao.impl;

import com.august.dao.OrderDao;
import com.august.pojo.Order;

/**
    * @author : Crazy_August
    * @description :
    * @Time: 2022-01-11   21:59
    */
public class OrderDaoImpl extends BaseDao implements OrderDao {
    /**
        * 保存订单
        *
        * @param order
        * @return
        */
    @Override
    public int savaOrder(Order order) {
        String sql = "INSERT INTO t_order(`order_id`,`create_time`,`price`,`status`,`user_id`)VALUES(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreatedTime(),order.getPrice(),0,order.getUserId());
    }

    /**
        * 保存订单项
        *
        * @param orderItem
        * @return
        */
    @Override
    public int savaOrderItem(Order.OrderItem orderItem) {
        String sql = "INSERT INTO t_order_item(`name`,`count`,`price`,`total_price`,`order_id`)VALUES(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }
}

```

5. 编写OrderService层

   **OrderService.java(接口)**

```java
package com.august.service;

import com.august.pojo.Car;

public interface OrderService {
    /**
        * 创建订单
        * @param car
        * @param userId
        * @return 返回订单号
        */
    String createOrder(Car car,Integer userId);
}

```

6. 编写OrderServiceImpl接口实现类

   **OrderServiceImpl.java**

```java
package com.august.service.impl;

import com.august.dao.BookDao;
import com.august.dao.OrderDao;
import com.august.dao.impl.BookDaoImpl;
import com.august.dao.impl.OrderDaoImpl;
import com.august.pojo.Book;
import com.august.pojo.Car;
import com.august.pojo.Order;
import com.august.service.OrderService;

import java.util.Date;
import java.util.Map;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-11   22:29
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    /**
     * 创建订单
     *
     * @param car
     * @param userId
     * @return 返回订单号
     */
    @Override
    public String createOrder(Car car, Integer userId) {

        //唯一性 时间戳 + userId
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建订单
        Order order = new Order(orderId, new Date(), car.getTotalPrice(), 0, userId);
        //保存到订单
        orderDao.savaOrder(order);
        // 保存订单项
        // 遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, Car.CarItem> entry : car.getItems().entrySet()) {
            //获取每个购物车的商品项
            Car.CarItem item = entry.getValue();
            Order.OrderItem orderItem = new Order.OrderItem(null, item.getName(), item.getCount(), item.getPrice(), item.getTotalPrice(), order.getOrderId());
            //保存订单到数据库
            orderDao.savaOrderItem(orderItem);
            //更新库存和销量
            Book book = bookDao.queryBookById(item.getId());
            book.setSales(book.getSales() + item.getCount());
            book.setStock(book.getStock() - item.getCount());
            //保存数据库
            bookDao.updateBook(book);
        }
        //清空购物车
        car.clear();
        return orderId;
    }
}

```

7. OrderServelet层

   **OrderServelet.java**

```java
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

```



## 7.谷歌验证码(Tomcat10)

**MyKaptchaServlet.java**

```java
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

```

## 8.客户端Servelet

ClientServelet.java

```java
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

```

## 9.Filter过滤类

1. 管理过滤

**ManagerFilter.java**

```java
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

```

2. 数据库事物过滤

   **TransactionFilter.java**

```java
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

```

   

## 10.相关配置文件

**jdbc.properties**

```text
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/book?charSet=UTF-8
username=root
password=123456
initialSize=5
maxActive=6
```

**web.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>UserServelet</servlet-name>
        <servlet-class>com.august.web.UserServelet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>UserServelet</servlet-name>
        <url-pattern>/userServelet</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>BookServelet</servlet-name>
        <servlet-class>com.august.web.BookServelet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>BookServelet</servlet-name>
        <url-pattern>/manage/bookServelet</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>ClientServelet</servlet-name>
        <servlet-class>com.august.web.ClientServelet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ClientServelet</servlet-name>
        <url-pattern>/client/bookServelet</url-pattern>
    </servlet-mapping>


    <servlet>
        <servlet-name>KaptchaServlet</servlet-name>
        <servlet-class>com.august.web.MyKaptchaServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>KaptchaServlet</servlet-name>
        <url-pattern>/kaptcha.jpg</url-pattern>
    </servlet-mapping>


    <servlet>
        <servlet-name>CarServelet</servlet-name>
        <servlet-class>com.august.web.CarServelet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>CarServelet</servlet-name>
        <url-pattern>/carServelet</url-pattern>
    </servlet-mapping>


    <servlet>
        <servlet-name>OrderServelet</servlet-name>
        <servlet-class>com.august.web.OrderServelet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>OrderServelet</servlet-name>
        <url-pattern>/orderServelet</url-pattern>
    </servlet-mapping>

    <filter>
        <filter-name>ManagerFilter</filter-name>
        <filter-class>com.august.filter.ManagerFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>ManagerFilter</filter-name>
        <url-pattern>/manager/*</url-pattern>
        <url-pattern>/pages/manager/*</url-pattern>
    </filter-mapping>


    <filter>
        <filter-name>TransactionFilter</filter-name>
        <filter-class>com.august.filter.TransactionFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>TransactionFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


    <error-page>
        <error-code>500</error-code>
        <location>/pages/error/error500.jsp</location>
    </error-page>
    <error-page>
        <error-code>404</error-code>
        <location>/pages/error/error404.jsp</location>
    </error-page>

</web-app>
```



​																						2021年11月29日1------ 2022年1月13日19:05:48





















