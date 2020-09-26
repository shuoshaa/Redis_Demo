package com.dw.redis.string;

import redis.clients.jedis.Jedis;

import java.util.List;

public class StringMain {

    public static void main(String[] args) throws InterruptedException {
        // 创建客户端连接
        Jedis jedis = new Jedis("192.168.169.128",6379);
        // 插入一个name zhangsan
        jedis.set("name","zhangsan");
        // 获取name
        System.out.println("===============获取key为name的值"+jedis.get("name"));

        // 对string类型数据进行增减操作
        // 设置age 18
        jedis.set("age","18");
        // 年龄加1
        jedis.incr("age");
        // 输出结果19
        System.out.println("=========== incr增1后的获取key为age中的value"+jedis.get("age"));
        // 年龄减1
        jedis.decr("age");
        // 19-1  18
        System.out.println("=========== decr减1后的获取key为age中的value"+jedis.get("age"));


        // 一次性插入多条数据
        jedis.mset("a","spring熟悉一下",
                   "b","mybatis熟悉一下",
                   "c","springmvc熟悉一下",
                   "d","ssm熟悉一下");
        // 获取到keys
        List<String> mget = jedis.mget("a", "b", "c", "d");
        // 循环遍历
        for (String value:
             mget) {
            System.out.println(value);
        }



        // 设置字段的自动过期
        jedis.setex("girl",12,"she is 18 years old");
        // 判断是否存在 存在线程睡眠模式
        while (jedis.exists("girl")){
            System.out.println("who is she");
            Thread.sleep(1000);
        }



        // 对已经存在的字段设置过期时间
        jedis.set("boy","this is zhangsan");
        // 过期时间10后
        jedis.expire("boy",10);
        // 判断是否存在
        while (jedis.exists("boy")){
            System.out.println("who am i");
            Thread.sleep(1000);
        }




    }
}
