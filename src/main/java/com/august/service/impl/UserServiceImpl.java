package com.august.service.impl;

import com.august.dao.UserDao;
import com.august.dao.impl.UserDaoImpl;
import com.august.pojo.User;
import com.august.service.UserService;

/**
 * @author : Crazy_August
 * @description : 服务层面
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
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    /**
     * 检查是否有用户名
     *
     * @param username
     * @return 返回turn说明已经存在
     */
    @Override
    public boolean existsUserName(String username) {
        if (userDao.queryUserByUsername(username) != null) {
            return true;
        }
        return false;
    }
}
