package com.albert.jdk.classloader;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by albertchen on 16/7/2.
 */
public class DelegateTest {

    String clsName = "com.albert.domain.Hello";

    @Test
    public void testLoadClass() throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> helloCls = myClassLoader.loadClass(clsName);
        System.out.println(helloCls.getClassLoader().getClass().getName());
    }

    @Test
    public void testDefineClass() throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> helloCls = myClassLoader.getClz(clsName);
        System.out.println(helloCls.getClassLoader().getClass().getName());

        Method sayHello = helloCls.getMethod("sayHello", null);
        sayHello.invoke(helloCls.newInstance(), null);
    }
}
