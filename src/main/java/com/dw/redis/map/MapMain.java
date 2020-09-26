package com.dw.redis.map;


import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapMain {
    public static void main(String[] args) {

        // 创建连接
        Jedis jedis = new Jedis("192.168.169.128",6379);

        // 创建一个对象
        // <rediskey,Map<key,value>>
        jedis.hset("man","江湖名称","张三");
        jedis.hset("man","江湖阅历","11");
        jedis.hset("man","江湖技能","项目无中生有");
        jedis.hset("man","江湖修为","元婴期（具体不明）");



        // 打印对象
        Map<String, String> man = jedis.hgetAll("man");
        System.out.println("=====  江湖悬赏令 ======");
        for (Map.Entry entry:
             man.entrySet()) {
            System.out.println(entry.getKey()+"=========="+entry.getValue());
        }

        System.out.println();

        // 获取man所有字段信息  field中的值
        Set<String> keys = jedis.hkeys("man");
        System.out.println("===== hkeys获取所有field ===");
        for (String fields:
             keys) {
            System.out.println(fields);
        }

        // 获取man的所有值的信息 value
        List<String> values = jedis.hvals("man");
        System.out.println("===== hvals获取所有value =====");
        for (String val:
             values) {
            System.out.println(val);
        }

        // 获取张三年龄
        String hget = jedis.hget("man", "江湖阅历");
        System.out.println("============查看档案，张三修炼了为  "+hget+"年,众人面面相觑，产生质疑============");
        System.out.println("=============  如此年轻的 元婴修士，整个大陆八百修真国从未听闻过如此怪胎   ================");

        // 骨龄增加 10年
        jedis.hincrBy("man", "江湖阅历", Long.parseLong("10"));
        System.out.println("===========  此时张三显露真正的骨龄："+jedis.hget("man","江湖阅历")+"年,骨龄瞬间增加10年  ==========");
        System.out.println("============ 众人大惊，如此隐匿手段，着实看不懂，叹为观止 ================");


        // 张三没了修为
        System.out.println("=============  短短20载，便修炼到 元婴大圆满  ============");
        System.out.println("============== 张三为了突破 炼虚，元婴渡劫开始了 ==========");
        jedis.hdel("man","江湖修为");
        Map<String, String> getMan = jedis.hgetAll("man");
        for (Map.Entry entry:
             getMan.entrySet()) {
            System.out.println(entry.getKey()+"====="+entry.getValue());
        }

        System.out.println("==================== 张三没了修为，他能否渡劫成功，成为八百修真国的第一 炼虚修士？ ");
        System.out.println("============= 这谁能说得准呢？修士，修得便是与天夺命 ============== ");
    }
}
