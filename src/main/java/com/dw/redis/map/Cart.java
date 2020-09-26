package com.dw.redis.map;


import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 购物车
public class Cart {

    // 定义Jedis  代码复用
    private Jedis jedis;

    // 定义方法连接redis
    public Cart() {
        jedis = new Jedis("192.168.169.128",6379);
    }

    // 有参
    public Cart(Jedis jedis){
        this.jedis = jedis;
    }


    /*
    *  修改购物车中的商品
    *   @param userName     用户名
    *   @param productId    商品编号
    *   @param num          操作商品的数量
    * */
    public void updateProduct2Cart(String userName,String productId,int num){
        jedis.hincrBy("shop:cart:"+userName,productId,num);
    }


    /*
    *   获取用户购物车的商品信息
    *   @param userName
    *   @return
    * */
    public List<Product> getProductsByUserName(String userName){
        List<Product> products = new ArrayList<>();
        Map<String, String> productMap = jedis.hgetAll("shop:product:" + userName);
        if (productMap == null || productMap.size() == 0) {
            return products;
        }
        for (Map.Entry entry:
             productMap.entrySet()) {
            Product product = new Product();
            // 获取用户购物车中商品的编号
            product.setId((String) entry.getKey());
            // 获取用户购物车中商品的数量
            int num = Integer.parseInt((String) entry.getValue());
            // 如果商品数量大于0，返回正常的值，如果商品小于0，初始花为0
            product.setNum(num > 0 ? num : 0);
            // 补全商品的其他信息
            complementOtherFiled(product);
            products.add(product);
        }

        return products;
    }
    public  void complementOtherFiled(Product product){
        String productId = product.getId();
        String s = jedis.get("shop:product:" + productId);
        Product product1 =(Product) new Gson().fromJson("s", Product.class);
        if (product1 != null) {
            product.setName(product1.getName());
            product.setPrice(product1.getPrice());

        }
    }

    public static void main(String[] args) {
        // 初始化商品的信息
        initData();     // 把商品放到redis
        Cart cart = new Cart();
        // 创建用户
        String userName = "Jay";
        cart.updateProduct2Cart(userName,"1001",11);
        cart.updateProduct2Cart(userName,"1002",11);
        cart.updateProduct2Cart(userName,"1003",11);

        // 打印当前用户的购物车信息
        List<Product> productsByUserName = cart.getProductsByUserName(userName);
        for (Product product:
             productsByUserName) {
            System.out.println(product);
        }


    }

    public static void initData() {
        System.out.println("=============  初始化商品信息  =============");
        // 连接
        Jedis jedis = new Jedis("192.168.169.128",6379);

        // 准备数据
        Product product1 = new Product("1001","大话设计模式",new BigDecimal("100"),11);
        Product product2 = new Product("1002","锋利的Jquery",new BigDecimal("121"),12);
        Product product3 = new Product("1003","JVM底层原理",new BigDecimal("181"),11);

        // 将数据写入redis
        jedis.set("shop:product:"+product1.getId(),new Gson().toJson(product1));
        jedis.set("shop:product:"+ product2.getId(),new Gson().toJson(product2));
        jedis.set("shop:product:"+product3.getId(),new Gson().toJson(product3));

        // 打印所有产品
        // 获取所有商品信息 *
        Set<String> keys = jedis.keys("shop:product:*");
        for (String key:
             keys) {
            String json = jedis.get(key);
            // 从字符串中解析出对象
            Product product = new Gson().fromJson(json,Product.class);
            System.out.println(product);
        }
    }



}
