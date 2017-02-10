package com.albert.jdk.classloader;

import com.albert.domain.Hello;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by albertchen on 16/7/1.
 */
public class MyClassLoaderTest {

    String clsName = "com.albert.domain.Hello";

    @Test(expected = ClassCastException.class)
    public void testDiffClassLoader() {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> helloCls1 = null;
        try {
            helloCls1 = myClassLoader.getClz(clsName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(helloCls1.getClassLoader().getClass().getName() + "  ----  " + helloCls1.getName());

        System.out.println(Hello.class.getClassLoader().getClass().getName() + " ---- " + Hello.class.getName());

        System.out.println(helloCls1.equals(Hello.class));

        try {
            Hello hello = (Hello)helloCls1.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
