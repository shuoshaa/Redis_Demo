package com.dw.redis.string;


import java.io.Serializable;

public class Person implements Serializable {
    private String name;    // 姓名
    private int age;    //年龄

    private static final long serialVersionUid = -9012113097419111583L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static long getSerialVersionUid() {
        return serialVersionUid;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
