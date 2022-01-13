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
