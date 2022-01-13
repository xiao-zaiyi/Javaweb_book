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
     * @return 返回turn说明已经存在
     */
    boolean existsUserName(String usernane);

}

