package com.august.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 */
public class Car {

    private Map<Integer, CarItem> items = new LinkedHashMap<>();

    public Car() {
    }

    public Car(Map<Integer, CarItem> items) {
        this.items = items;
    }

    /**
     * 添加商品项
     * @param CarItem
     */
    public void addItem(CarItem CarItem) {
        // 判断购物车时候拥有相同的商品
        Car.CarItem carItem = items.get(CarItem.getId());
        if (carItem == null) {
            //之前购物车没有此商品
            items.put(CarItem.getId(), CarItem);
        } else {
            //数目加1
            carItem.setCount(carItem.getCount() + 1);
            // 总价格计算
            carItem.setTotalPrice(carItem.getPrice().multiply(new BigDecimal(carItem.getCount())));
        }


    }

    /**
     * 删除商品项
     *
     * @param id
     */
    public void removeItem(Integer id) {
        items.remove(id);
    }

    /**
     * 修改商品数量
     *
     * @param id
     * @param count
     */
    public void updateCount(Integer id, Integer count) {
        // 1. 先查看购物车中是否有此商品。如果有，修改商品数量，更新总金额
        // 判断购物车时候拥有相同的商品
        Car.CarItem carItem = items.get(id);
        if (carItem != null) {
            //修改商品数量
            carItem.setCount(count);
            // 总价格计算
            carItem.setTotalPrice(carItem.getPrice().multiply(new BigDecimal(carItem.getCount())));
        }
    }

    /**
     * 清空购物车
     */
    public void clear() {
        items.clear();
    }


    public Map<Integer, CarItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CarItem> items) {
        this.items = items;
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CarItem> entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CarItem> entry : items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getPrice().multiply(new BigDecimal(entry.getValue().getCount())));
        }
        return totalPrice;
    }


    @Override
    public String toString() {
        return "Car{" +
                "items=" + items +
                ", totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }

    /**
     * 购物车商品项
     *
     * @author : Crazy_August
     * @description :
     * @Time: 2022-01-11   16:34
     */
    public static class CarItem {
        private Integer id;
        private Integer count;
        private String name;
        private BigDecimal price;
        private BigDecimal totalPrice;

        public CarItem() {
        }

        public CarItem(Integer id, Integer count, String name, BigDecimal price, BigDecimal totalPrice) {
            this.id = id;
            this.count = count;
            this.name = name;
            this.price = price;
            this.totalPrice = totalPrice;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        @Override
        public String toString() {
            return "CarItem{" +
                    "id=" + id +
                    ", count=" + count +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

}
