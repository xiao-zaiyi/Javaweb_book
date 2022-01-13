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
