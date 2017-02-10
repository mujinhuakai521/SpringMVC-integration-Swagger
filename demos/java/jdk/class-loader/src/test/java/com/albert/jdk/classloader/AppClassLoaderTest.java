package com.albert.jdk.classloader;

import org.junit.Test;

/**
 * Created by albertchen on 16/7/1.
 */
public class AppClassLoaderTest {

    String clsName = "com.albert.domain.Hello";

    @Test
    public void testAppLoadClass() throws ClassNotFoundException {
        ClassLoader currClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(currClassLoader.getClass().getName());

        Class<?> helloCls = currClassLoader.loadClass(clsName);
        System.out.println(helloCls.getClassLoader().getClass().getName());
    }

    @Test
    public void testExttLoadClass() throws ClassNotFoundException {
        ClassLoader currClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader parentClassLoader = currClassLoader.getParent();

        System.out.println(currClassLoader.getClass().getName());
        System.out.println(parentClassLoader.getClass().getName());

        //com.sun.nio.zipfs.ZipCoder 在 %JAVA_HOME%/jre/lib/ext/zipfs.jar 中
        Class<?> cls = currClassLoader.loadClass("com.sun.nio.zipfs.ZipCoder");

        System.out.println(cls.getClassLoader().getClass().getName());
    }
}
