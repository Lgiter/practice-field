package com.lgiter.practice.mmp.asm;

/**
 * Author: lixiaolong
 * Date: 2023/5/16
 * Desc:
 */
public class HelloWorldRun {
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> clazz = classLoader.loadClass("lgiter.practice.mmp.asm.HelloWorld");
        Object instance = clazz.newInstance();
        System.out.println(instance);
    }
}
