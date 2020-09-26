package com.dw.redis.test;

import redis.clients.jedis.Jedis;
// 张三 测试连接redis
public class RedisTest {
    public static void main(String[] args) {

        // 连接Linux redis
        //参数1 是Linux的ip   参数2是端口号
        Jedis jedis = new Jedis("192.168.169.128",6379);
        //客户端向服务器发送ping
        String ping = jedis.ping();
        System.out.println(ping);
    }
}
