package com.august.dao.impl;

import com.august.dao.UserDao;

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
