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
