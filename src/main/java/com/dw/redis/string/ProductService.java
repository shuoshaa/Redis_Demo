package com.dw.redis.string;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.*;

public class ProductService {

    public void saveProductRedis(){
        // 创建对象 设置person中name age属性值
        Person person = new Person("张三",18);
        // 连接redis
        Jedis jedis = new Jedis("192.168.169.128",6379);
        //把数据信息用toString方法，redis set方法保存对象
        jedis.set("person",person.toString());
        // 从redis中取值 get(key)
        System.out.println(jedis.get("person"));

        //保存json化之后的地下
        jedis.set("person",new Gson().toJson(person));
        String person1 = jedis.get("person");
        Person pjson = new Gson().fromJson(person1,Person.class);
        System.out.println(pjson.getName()+" "+pjson.getAge());

    }

    /*
    *  从字节数组中读取java对象
    * @param  productBytes
    * @return
    * @throws Exception
    *
    * */
    public Person getProductByBytes(byte[] productBytes) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(productBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Person) objectInputStream.readObject();
    }


    /*
    *   将对象转化成Byte数组
    *   @param product
    *   @return
    *   @throws Exception
    * */
    public byte[] getBytesByproduct(Person product) throws Exception {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bas);
        oos.writeObject(product);
        oos.flush();
        return bas.toByteArray();
    }



}
