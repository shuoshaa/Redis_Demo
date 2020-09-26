package com.dw.redis.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class SetMain {

    public static void main(String[] args) {
        // 连接
        Jedis jedis = new Jedis("192.168.169.128",6379);

        // 人员登记
        String[] hreos = new String[]{
            "虚竹","段誉","郭靖","黄蓉","乔峰","王语嫣","慕容复"
        };

        // 创建
        jedis.sadd("jinyong",hreos);
        // 获取一个set中所有的元素
        Set<String> man = jedis.smembers("jinyong");
        for (String name:
             man) {
            System.out.print(name+" ");
        }

        System.out.println();

        //判断一个成员是否属于某条特定的set数据
        // 判断张三是否登记了
        Boolean sismember = jedis.sismember("jinyong", "张三");
        if (!sismember){
            System.out.println(" 张三 ，尚未入场登记");
        }

        // 计算一个set中有多少元素
        Long manNum = jedis.scard("jinyong");
        System.out.println("有 "+manNum+" 位 江湖人士已经入场登记准备了");

        System.out.println();


        // 张三没有来，是报名去参加了 中国新说唱
        String[] rap = new String[]{
                "张三","郭靖","黄蓉","王语嫣"
        };
        // 说唱 登记表
        System.out.println("====== rapper都有谁 smembers");
        jedis.sadd("hipHop",rap);
        Set<String> hipHop = jedis.smembers("hipHop");
        for (String rapper:
             hipHop) {
            System.out.print(rapper+" ");
        }

        System.out.println();


        // 计算两个set之间的交集
        System.out.println("======= sinter交集");
        Set<String> sinter = jedis.sinter("jinyong", "hipHop");
        for (String name:
             sinter) {
            System.out.print(name+" ");
        }


        // 计算两个集合的并集
        System.out.println();
        System.out.println("======= 并集");
        sinter = jedis.sunion("jinyong", "hipHop");
        for (String name:
             sinter) {
            System.out.print(name+" ");
        }
        System.out.println("===========张三在此");


        // 计算两个集合的差集
        System.out.println("========== 差集");
        sinter = jedis.sdiff("jinyong", "hipHop");
        for (String name:
             sinter) {
            System.out.print(name+" ");
        }


        System.out.println();

        // 将两个集合计算处的差集保存起来，升级位超级vip
        System.out.println("======= 差集中的人都聚起来");
        jedis.sdiffstore("vips","jinyong","hipHop");
        for (String name:
             jedis.smembers("vips")) {
            System.out.print(name+" ");
        }
    }

}
