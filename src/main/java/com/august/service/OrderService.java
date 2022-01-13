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
