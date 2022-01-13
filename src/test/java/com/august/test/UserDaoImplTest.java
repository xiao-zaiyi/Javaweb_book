package com.august.test;
import com.august.dao.UserDao;
import com.august.dao.impl.UserDaoImpl;
import com.august.pojo.User;
import org.junit.jupiter.api.Test;

class UserDaoTest {
    UserDao userDao = new UserDaoImpl();

    @Test
    void queryUserByUsername() {

        User user = userDao.queryUserByUsername("xiaozaiyi");
        if (user == null) {
            System.out.println("用户名可用");
        } else {
            System.out.println("用户名也存在");
        }

    }

    @Test
    void queryUserByUsernameAndPassword() {
        User user = userDao.queryUserByUsernameAndPassword("admin", "123456");
        System.out.println(user);
    }

    @Test
    void saveUser() {
        User xiaozaiyi = new User(null,"xiaozaiyi", "123456", "1932794922@qq.com");
        userDao.saveUser(xiaozaiyi);

    }
}