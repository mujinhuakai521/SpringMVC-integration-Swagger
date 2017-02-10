package com.albert.jdk.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by albertchen on 16/7/1.
 */
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public Class<?> getClz(String clsName) throws IOException {
        String filePath = this.getClass().getClassLoader().getResource(clsName.replace(".", "/") + ".class").getPath();
        File file = new File(filePath);
        byte[] b = new byte[(int)file.length()];
        InputStream in = new FileInputStream(file);
        in.read(b);
        in.close();
        return defineClass(clsName, b, 0, b.length);
    }
}
