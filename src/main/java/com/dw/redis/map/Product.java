package com.dw.redis.map;


import java.math.BigDecimal;

// 商品类
public class Product {

    private String id;      // 商品编号
    private String name;    // 商品名称
    private BigDecimal price;   // 商品价格
    private int num;        // 商品数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Product(String id, String name, BigDecimal price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}
