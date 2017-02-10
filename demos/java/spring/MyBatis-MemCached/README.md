MyBatis 使用 memcached 作为二级缓存

### **MyBatis 缓存**

MyBatis 分为一级缓存与二级缓存
* 一级缓存是session范围内, 其默认开启, 在同一个session内如果查询条件一致则可以直接从缓存中获得之前的查询结果。如下条件一级缓存会失效:
    1. session 关闭, 则此seesion里缓存不可用
    2. 执行过session.cleanCach()来清理缓存
    3. 执行过增删改操作
> 注意使用原生的MyBatis的SqlSessionFactory来构造sqlSession查询时一级缓存可以生效
> 使用 mybatis-spring 集成spring与mybatis时, mybatis的mapper交由spring管理, 此时mybatis的一级缓存无法使用, 因为每次查询都相当于新建session
* 二级缓存是跨session的, 如果要启动只需要在mybatis全局配置文件中添加如下设置
```
<settings>
         <setting name="cacheEnabled" value="true" />
</settings>
```
并在mapper.xml里的 `<mapper>` 节点下添加`<cache />`, 此采用默认值, 可以添加如下可选配置参数
    1. eviction 是缓存的淘汰算法，可选值有"LRU"、"FIFO"、"SOFT"、"WEAK"，缺省值是LRU
    2. flashInterval 指缓存过期时间，单位为毫秒，60000即为60秒，缺省值为空，即只要容量足够，永不过期
    3. size指缓存多少个对象，默认值为1024
    4. readOnly是否只读，如果为true，则所有相同的sql语句返回的是同一个对象（有助于提高性能，但并发操作同一条数据时，可能不安全），如果设置为false，则相同的sql，后面访问的是cache的clone副本。
设置了全局缓存后还可以在单条select语句上添加`useCache="false"`参数关闭此语句的缓存
```
<select id="getOrder" parameterType="int" resultType="TOrder"  useCache="false">
```

### **使用memcached缓存**

官方mybatis、memcached整合包<http://mybatis.github.io/memcached-cache/>

首先引入memcached缓存包
```
        <!-- MyBatis MemCached begin -->
        <dependency>
            <groupId>org.mybatis.caches</groupId>
            <artifactId>mybatis-memcached</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>net.spy</groupId>
            <artifactId>spymemcached</artifactId>
            <version>2.10.6</version>
        </dependency>
        <!-- MyBatis MemCached end-->
```
在mapper.xml里的`<mapper>`节点下添加如下
```
    <cache type="org.mybatis.caches.memcached.MemcachedCache" />
    <!-- log输出 非必要 -->
    <cache type="org.mybatis.caches.memcached.LoggingMemcachedCache" />
```
会自动到classpath下查找memcached.properties文件对memcached的配置, 如果没有则采用默认值;同样可以对具体的select语句采用`useCache="false"`.

> 注意测试时最后抛出的异常是正常情况, 参考<http://blog.sina.com.cn/s/blog_7045cb9e0100wqzf.html>
