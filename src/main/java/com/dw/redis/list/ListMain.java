package com.dw.redis.list;


import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

/*
*   天龙八部外传--麦当劳风云
* */
public class ListMain {
    public static void main(String[] args) {

        // 创建连接redis
        Jedis jedis = new Jedis("192.168.169.128",6379);
        // 先删除，每次执行都要先清空，不然会数据库中会一直加
        jedis.del("柜台1号");

        // 人物：鸠摩智 虚竹 段誉 乔峰 在麦当劳排队
        // lpush 从左边插入
        // 参数1 key值 即list的名，后边都为value
        jedis.lpush("柜台1号","乔峰","段誉","虚竹","鸠摩智");
        // lrange 查询
        // lrange key start end 查询列表指定范围内的数据 0第一位 -1末尾
        for (String name:
             jedis.lrange("柜台1号",0,-1)) {
            System.out.print(name+" ");
        }

        System.out.println();
        System.out.println("=========================  剧情1: 王语嫣插队  =========================");
        // 整个排列最靠右为第一情况下，rpush从右边插入
        jedis.rpush("柜台1号","王语嫣");
        List<String> list = jedis.lrange("柜台1号", 0, -1);
        for (String name:
             list) {
            System.out.print(name+" ");
        }

        System.out.println();
        System.out.println("=========================  剧情2: 慕容复来了，鸠摩智让慕容复排到他前边  =========================");
        jedis.linsert("柜台1号", BinaryClient.LIST_POSITION.AFTER,"鸠摩智","慕容复");
        list = jedis.lrange("柜台1号", 0, -1);
        for (String name:
             list) {
            System.out.print(name+" ");
        }


        System.out.println();
        System.out.println("=========================  剧情3: 看到慕容复插队后大家都很生气，恰好阿紫和阿朱过来了，" +
                "姐妹二人依次排到乔峰后面  =========================");
        jedis.linsert("柜台1号",BinaryClient.LIST_POSITION.BEFORE,"乔峰","阿紫");
        jedis.linsert("柜台1号",BinaryClient.LIST_POSITION.BEFORE,"阿紫","阿朱");
        list = jedis.lrange("柜台1号", 0, -1);
        for (String name:
             list) {
            System.out.print(name+" ");
        }


        System.out.println();
        System.out.println("=========================  剧情4: 插队不文明，这时大伙吵了起来，好像要动武的节奏，鸠摩智" +
                "感觉不妙，溜之大吉  =========================");
        // 鸠摩智位置在最后，此时在最左面，lpop 左侧弹出
        jedis.lpop("柜台1号");
        for (String name:
             jedis.lrange("柜台1号",0,-1)) {
            System.out.print(name+" ");
        }

        System.out.println();
        System.out.println("=========================  剧情5: 在他们互相出手之时，张三一看没人，便去买了麦当劳" +
                "，排在首位  =========================");
        // rpush 从右插入
        jedis.rpush("柜台1号","张三");
        for (String name:
             jedis.lrange("柜台1号",0,-1)) {
            System.out.print(name+" ");
        }

        System.out.println();
        System.out.println("=========================  剧情6: 张三排队买麦当劳，此时，慕容复不敌其他人，已退下" +
                "  =========================");
        jedis.lpop("柜台1号");
        for (String name:
             jedis.lrange("柜台1号",0,-1)) {
            System.out.print(name+" ");
        }

        System.out.println();
        System.out.println("=========================  剧情7: 此时，虚竹被达摩祖师带走，段誉被段正淳带走  " +
                "=========================");
        // 保留2号到6号元素，也就是说从左开始0 1 删掉
        String result = jedis.ltrim("柜台1号", 2, 6);
        // Ok 也就是输入返回的结果，再遍历
        if ("OK".equals(result)){
            for (String name:
                 jedis.lrange("柜台1号",0,-1)) {
                System.out.print(name+" ");
            }
        }


        System.out.println();
        System.out.println("=========================  剧情8：张三想见识传说中的降龙十八掌有多厉害，此时张三向乔峰发出挑战，" +
                "就此，二人展开搏斗  =========================");
        System.out.println("=========================  最终二人大战三百回合  =========================");
        System.out.println("=========================  张三全身而退，乔峰的降龙十八掌名不虚传  =========================");
        jedis.rpop("柜台1号");
        for (String name:
             jedis.lrange("柜台1号",0,-1)) {
            System.out.print(name+" ");
        }


        System.out.println();
        System.out.println("=========================  剧情9: 张三退下时对王语嫣说到，他已买到麦当劳" +
                "问王语嫣不要再排队了，跟他一起吃  =========================");
        System.out.println("=========================  王语嫣看到张三如此勇猛，能和乔帮助大战三百回合不立战败之地，" +
                "且邀约一起吃，也省得了排队了，稍作矜持，便答应了  =========================");
        String res = jedis.ltrim("柜台1号", 0, 2);
        if ("OK".equals(res)){
            for (String name:
                 jedis.lrange("柜台1号",0,-1)) {
                System.out.print(name+" ");
            }
        }

        System.out.println();
        System.out.println("=========================  王语嫣也走出队列，只剩乔峰 阿朱阿紫 三人  =========================");



    }
}
