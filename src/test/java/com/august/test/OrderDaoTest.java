package com.august.test;

import com.august.dao.OrderDao;
import com.august.dao.impl.OrderDaoImpl;
import com.august.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDaoTest {
    private OrderDao orderDao = new OrderDaoImpl();


    @Test
    public void savaOrder() {
        int i = orderDao.savaOrder(new Order("1234", new Date(), new BigDecimal(111), 0, 1));
        System.out.println(i);
    }

    @Test
    public void savaOrderItem() {

        orderDao.savaOrderItem(new Order.OrderItem(null, "javac", 20, new BigDecimal(55), new BigDecimal(11), "1234"));
        orderDao.savaOrderItem(new Order.OrderItem(null, "javascript", 20, new BigDecimal(55), new BigDecimal(11), "1234"));
        orderDao.savaOrderItem(new Order.OrderItem(null, "c++", 20, new BigDecimal(55), new BigDecimal(11), "1234"));
        orderDao.savaOrderItem(new Order.OrderItem(null, "c#", 210, new BigDecimal(515), new BigDecimal(111), "1234"));

    }
}