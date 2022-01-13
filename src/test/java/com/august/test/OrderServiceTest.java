package com.august.test;

import com.august.dao.OrderDao;
import com.august.dao.impl.OrderDaoImpl;
import com.august.pojo.Car;
import com.august.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderServiceTest {

    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void createOrder() {
        Car car = new Car();
        car.addItem(new Car.CarItem(1,  2, "javac",new BigDecimal(10), new BigDecimal(20)));
        car.addItem(new Car.CarItem(2,  5, "c++",new BigDecimal(8), new BigDecimal(40)));
        OrderServiceImpl orderService = new OrderServiceImpl();
        String order = orderService.createOrder(car, 1);

    }
}