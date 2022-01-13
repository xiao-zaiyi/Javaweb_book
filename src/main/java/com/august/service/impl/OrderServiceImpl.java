package com.august.service.impl;

import com.august.dao.BookDao;
import com.august.dao.OrderDao;
import com.august.dao.impl.BookDaoImpl;
import com.august.dao.impl.OrderDaoImpl;
import com.august.pojo.Book;
import com.august.pojo.Car;
import com.august.pojo.Order;
import com.august.service.OrderService;

import java.util.Date;
import java.util.Map;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-11   22:29
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    /**
     * 创建订单
     *
     * @param car
     * @param userId
     * @return 返回订单号
     */
    @Override
    public String createOrder(Car car, Integer userId) {

        //唯一性 时间戳 + userId
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建订单
        Order order = new Order(orderId, new Date(), car.getTotalPrice(), 0, userId);
        //保存到订单
        orderDao.savaOrder(order);
        // 保存订单项
        // 遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, Car.CarItem> entry : car.getItems().entrySet()) {
            //获取每个购物车的商品项
            Car.CarItem item = entry.getValue();
            Order.OrderItem orderItem = new Order.OrderItem(null, item.getName(), item.getCount(), item.getPrice(), item.getTotalPrice(), order.getOrderId());
            //保存订单到数据库
            orderDao.savaOrderItem(orderItem);
            //更新库存和销量
            Book book = bookDao.queryBookById(item.getId());
            book.setSales(book.getSales() + item.getCount());
            book.setStock(book.getStock() - item.getCount());
            //保存数据库
            bookDao.updateBook(book);
        }
        //清空购物车
        car.clear();
        return orderId;
    }
}
