### WEB 项目集成Jetty

> 本项目基于 demos\java\spring\HelloWorld-MVC, 输出的还是 war 包，不过将 jetty 集成到 test 中，方便在开发中直接启动整个项目而不用配置。

#### 引入 Jetty 依赖

在 pom.xml 中添加如下依赖:
```
 <!-- 集成 Jetty 启动 start -->
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-server</artifactId>
            <version>9.1.4.v20140401</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-webapp</artifactId>
            <version>9.1.4.v20140401</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-continuation</artifactId>
            <version>9.1.4.v20140401</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-jsp</artifactId>
            <version>9.1.4.v20140401</version>
            <scope>test</scope>
        </dependency>
<!-- 集成 Jetty 启动 end -->
```
因为用到了 JSP，还要添加 JSP 的依赖:
```
 <!-- JSP Support -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.servlet.jsp</artifactId>
            <version>2.2.3</version>
            <scope>test</scope>
        </dependency>
        <!-- EL Support -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.el</artifactId>
            <version>2.2.3</version>
            <scope>test</scope>
        </dependency>
```

#### 建立启动类
在 src/test/java 如下建立启动类:
```
package com.albert.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author albert
 * @version 1.0
 * @since JDK 1.7
 */
public class WebAppWarServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        // 相对于项目路径 或 使用绝对路径
        context.setWar("C:\\Users\\Albert\\Desktop\\test\\example\\demos\\java\\jetty\\HelloWorld-jetty\\src\\main\\webapp");
        server.setHandler(context);

        server.start();
        server.join();
    }
}
```
运行 WebAppWarServer 里的 main 方法即可。