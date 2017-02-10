Java 类装载机制

### JVM 默认的 ClassLoader

1. Bootstrap ClassLoader

使用 C++ 编写, 加载 `%JAVA_HOME%/jre/lib`, `-Xbootclasspath`, `%JAVA_HOME%/jre/classes` 中的类

输出使用 Bootstrap Loader 加载的类路径

```
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();

        for (URL url : urLs) {
            System.out.println(url);
        }
```

2. ExtClassLoader

ExtClassLoader 本身是被 Bootstrap Loader 加载的, ExtClassLoader 加载 `%JAVA_HOME%/jre/lib/ext`, `%JAVA_HOME%/jre/lib/ext/classes`, `java.ext.dirs` 指定的路径

```
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
```

3. AppClassLoader

AppClassLoader 又可以叫 SystemClassLoader, 加载 `classpath` 下的类

```
    @Test
    public void testAppLoadClass() throws ClassNotFoundException {
        ClassLoader currClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(currClassLoader.getClass().getName());

        Class<?> helloCls = currClassLoader.loadClass(clsName);
        System.out.println(helloCls.getClassLoader().getClass().getName());
    }
```

### 全盘负责与委托机制

全盘负责是指当一个 ClassLoader 转载一个类时,除非显式使用另一个 Classoader, 该类所在依赖及其引用的类都使用 ClassLoader 载入。

委托机制是先委托父类寻找装载类, 只有在找不到时才自己查找目标类, 这样可以避免 jdk 源码被恶意覆盖篡改。

### 自定义 ClassLoader

自定义 ClassLoader 可以直接继承 `java.lang.ClassLoader` 类, `java.lang.ClassLoader` 中已有的方法:

1. `findLoadedClass`: 每个类加载器都维护有自己的一份已加载类名字空间，其中不能出现两个同名的类。凡是通过该类加载器加载的类，无论是直接的还是间接的，都保存在自己的名字空间中，该方法就是在该名字空间中寻找指定的类是否已存在，如果存在就返回给类的引用，否则就返回 null。这里的直接是指，存在于该类加载器的加载路径上并由该加载器完成加载，间接是指，由该类加载器把类的加载工作委托给其他类加载器完成类的实际加载。
2. `getSystemClassLoader`：Java2 中新增的方法。该方法返回系统使用的 ClassLoader。可以在自己定制的类加载器中通过该方法把一部分工作转交给系统类加载器去处理。
3. `defineClass`：该方法是 ClassLoader 中非常重要的一个方法，它接收以字节数组表示的类字节码，并把它转换成 Class 实例，该方法转换一个类的同时，会先要求装载该类的父类以及实现的接口类。
4. `loadClass`：加载类的入口方法，调用该方法完成类的显式加载。通过对该方法的重新实现，我们可以完全控制和管理类的加载过程。
5. `resolveClass`：链接一个指定的类。这是一个在某些情况下确保类可用的必要方法，详见 Java 语言规范中“执行”一章对该方法的描述。

自定义 `com.albert.jdk.classloader.MyClassLoader`, 继承 ClassLoader 所有方法, 并且封装 `defineClass` 方法到 `getClz` 方法。
因为 `loadClass` 继承自 ClassLoader 类, 所以如果使用它装载类会委托给 AppClassLoader。

测试代码:

```
    String clsName = "com.albert.domain.Hello";

    @Test
    public void testLoadClass() throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> helloCls = myClassLoader.loadClass(clsName);
        System.out.println(helloCls.getClassLoader().getClass().getName());
    }
```

测试结果:

```
sun.misc.Launcher$AppClassLoader
```

而 `getClz` 直接从 class 的二进制数据装载, 不会委托给其他 ClassLoader.
测试代码:

```
    String clsName = "com.albert.domain.Hello";
    @Test
    public void testDefineClass() throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> helloCls = myClassLoader.getClz(clsName);
        System.out.println(helloCls.getClassLoader().getClass().getName());

        Method sayHello = helloCls.getMethod("sayHello", null);
        sayHello.invoke(helloCls.newInstance(), null);
    }
```

测试结果:

```
com.albert.jdk.classloader.MyClassLoader
Hello
```

### 类的唯一性

在 `findLoadedClass` 方法说明中, 每个类加载器都维护有自己的一份已加载类名字空间, 并且里边类名唯一。所以有时候会出现同名类不等的情况, 因为是通过不同的加载器加载的类。

比如在 `MyClassLoaderTest` 类中, 有引入的 `com.albert.domain.Hello`。
所以根据全盘委托, 引入的`Hello.class` 与 `MyClassLoaderTest.class` 通过默认的 AppClassLoader 加载, 而`helloCls1`通过自定义加载器加载, 两者不相等, 生成的对象不能强制转换。

```

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
```


