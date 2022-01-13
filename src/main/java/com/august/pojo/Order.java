package com.august.pojo;

import java.math.BigDecimal;
import java.util.Date;


/** 订单模块
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-11   21:38
 */
public class Order {
    private String orderId;
    private Date createdTime;
    private BigDecimal  price;
    // 0 表示未发货, 1 表示发货, 2 表示已签收
    private Integer status = 0;
    private Integer userId;

    public Order() {
    }

    public Order(String orderId, Date createdTime, BigDecimal price, Integer status, Integer userId) {
        this.orderId = orderId;
        this.createdTime = createdTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = (java.sql.Date) createdTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", createdTime=" + createdTime +
                ", price=" + price +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }

    /**
     * 订单项
     */
    public static class OrderItem{
        private Integer id;
        private String name;
        private Integer count;
        private BigDecimal price;
        private BigDecimal totalPrice;
        private String orderId;

        public OrderItem() {
        }

        public OrderItem(Integer id, String name, Integer count, BigDecimal price, BigDecimal totalPrice, String orderId) {
            this.id = id;
            this.name = name;
            this.count = count;
            this.price = price;
            this.totalPrice = totalPrice;
            this.orderId = orderId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public String toString() {
            return "orderItem{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", count=" + count +
                    ", price=" + price +
                    ", totalPrice=" + totalPrice +
                    ", orderId='" + orderId + '\'' +
                    '}';
        }
    }





}
