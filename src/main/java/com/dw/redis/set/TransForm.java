package com.dw.redis.set;

import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

public class TransForm {


    public static void main(String[] args) {
        // 连接
        Jedis jedis = new Jedis("192.168.169.128",6379);
        // 添加 浏览商品的用户
        jedis.sadd("thisUsers","虚竹","段誉","乔峰","阿朱","阿紫","王语嫣","张三");

        // 添加 下单用户
        jedis.sadd("orderUsers","乔峰","段誉","阿朱","阿紫","张三");

        // 添加 支付用户
        jedis.sadd("payUsers","乔峰","张三");

        // 浏览过商品的用户，有哪些下单了
        // 即 两个集合中 交集
        jedis.sinterstore("thisOrderUsers","thisUsers","orderUsers");

        // 计算浏览某商品的用户数量 和 既浏览又下单的用户数量
        Long thisUsers = jedis.scard("thisUsers");
        Long thisOrderUsers = jedis.scard("thisOrderUsers");
        NumberFormat numberFormat = new DecimalFormat("0.00");
        Long x = new Long(thisOrderUsers/thisUsers);
        System.out.println("订单"+thisOrderUsers+"/浏览"+thisUsers+"转化: "+numberFormat.format(x));
        for (String name:
             jedis.smembers("thisPayUsers")) {
            System.out.print(name+" ");
        }

        System.out.println();

        // 浏览并且下单的用户，最终支付的转化
        // 下单的用户 并且 支付 交集
        jedis.sinterstore("thisPayUsers","thisOrderUsers","payUsers");
        Long payUsers = jedis.scard("payUsers");
        x = new Long(payUsers / thisOrderUsers);
        System.out.println("支付:"+payUsers+"/订单"+thisOrderUsers+"转化: "+numberFormat.format(x));
        Set<String> thisPayUsers = jedis.smembers("thisPayUsers");
        for (String name:
             thisPayUsers) {
            System.out.print(name+" ");
        }

        System.out.println();

        // 浏览 并最终支付的转化
        x = new Long(payUsers / thisUsers);
        System.out.println("支付:"+payUsers+"/浏览："+thisUsers+"转化:"+numberFormat.format(x));
        for (String name:
             thisPayUsers) {
            System.out.println(name+" ");
        }
    }
}
