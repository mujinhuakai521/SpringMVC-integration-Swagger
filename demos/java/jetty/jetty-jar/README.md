## spring mvc + jetty = jar

一般的java web项目都是打成war包，其有固定的目录结构，然后部署到一个web 容器如：tomcat, jetty, jboss等等。

![web-module](http://7xiowk.com1.z0.glb.clouddn.com/web-module.gif)

现在我们的目标是输出一个可执行的 jar 包，直接通过 `java -jar XXX.jar` 启动 web 项目，通过对比 war 包和 jar 包，发现主要需要解决如下几个问题：

1. war 包时候项目启动所有用到的 class 文件除 jre 自带的包括如下几块:
    - 用到的容器的 class 文件
    - WEB-INF/lib 里 jar 包中的 class 文件，也就是第三方 jar 包
    - 项目自己的 class  文件
所有这些都需要打到一个 jar 包中，也就是需要将原来所有的 jar 包拆开打包到一个jar包中。
2. war 包中的静态文件, 可以选择不打到 jar 包中然后通过 `java -jar XXX.jar -Dstaticdir=XXX` 指定用到的静态文件路径，不过这里选择另一种方式，干脆将静态文件也打到 jar 包中。
3. servlet 规范中 war 包 /WEB-INF/web.xml 必须有的, 将其也打到 jar 中并在启动时引导容器加载。

maven 默认的打包插件肯定是不行了，因为不会将项目依赖的第三方包也输出到最后的jar包中。搜索了一下主要参考了[jetty-executable-maven](http://blog.anvard.org/articles/2013/10/09/embedded-jetty-executable-maven.html) 这篇博客写的东西，使用maven-assembly-plugin 插件自定义加载，不过它使用了一些其他不需要的东西,  而我只使用它插件的配置，然后重新指定jar包的main方法。

最后的代码如下：
### 项目结构

![jetty-jar](http://7xiowk.com1.z0.glb.clouddn.com//jetty/jetty-jar.png)

### pom.xml 配置
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.albert.jetty</groupId>
	<artifactId>jetty-jar</artifactId>
	<packaging>jar</packaging>
	<version>1.0-SNAPSHOT</version>
	<name>jetty-jar</name>
	<properties>
		<spring-version>3.2.4.RELEASE</spring-version>
        <jackson.version>2.6.3</jackson.version>
	</properties>
	<dependencies>
        <!-- Need this for json to/from object -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>${jackson.version}</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>

        <!-- Spring AOP 依赖 start -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.5</version>
        </dependency>
        <!-- Spring AOP 依赖 end -->
        <!-- Spring ApplicationContext 依赖 start -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring-version}</version>
        </dependency>
        <!-- Spring ApplicationContext 依赖 end -->
        <!-- Spring WebApplicationContext 依赖 start -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring-version}</version>
        </dependency>
        <!-- Spring WebApplicationContext 依赖 end -->
        <!-- 集成 Jetty 启动 start -->
        <dependency>
            <groupId>org.eclipse.jetty.aggregate</groupId>
            <artifactId>jetty-all</artifactId>
            <version>9.1.4.v20140401</version>
        </dependency>
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-server</artifactId>
            <version>9.1.4.v20140401</version>
        </dependency>
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-webapp</artifactId>
            <version>9.1.4.v20140401</version>
        </dependency>
        <!-- JSP Support -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.servlet.jsp</artifactId>
            <version>2.2.3</version>
        </dependency>
        <!-- EL Support -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.el</artifactId>
            <version>2.2.3</version>
        </dependency>
        <!-- 集成 Jetty 启动 end -->
        <!-- Spring WEB MVC 依赖 start -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring-version}</version>
        </dependency>
        <!-- Spring WEB MVC 依赖 end -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
	</dependencies>
    <build>
        <finalName>hw-jetty</finalName>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptors>
                        <descriptor>src/assemble/distribution.xml</descriptor>
                    </descriptors>
                    <archive>
                        <manifest>
                            <mainClass>com.albert.Runner</mainClass>
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
###  启动的 main 方法

```
package com.albert;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class Runner {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        ProtectionDomain protectionDomain = Runner.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        System.out.println(location.toExternalForm());
        context.setWar(location.toExternalForm());
        server.setHandler(context);

        server.start();
        server.join();
    }
}
```
### 插件的配置文件 src/assemble/distribution.xml

```
<assembly xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
  <id>standalone</id>
  <formats>
    <format>jar</format>
  </formats>
  <baseDirectory></baseDirectory>
  <dependencySets>
    <dependencySet>
      <unpack>true</unpack>
      <unpackOptions>
        <excludes>
          <exclude>META-INF/spring.handlers</exclude>
          <exclude>META-INF/spring.schemas</exclude>
        </excludes>
      </unpackOptions>
    </dependencySet>
  </dependencySets>
  <files>
    <file>
      <source>src/assemble/spring.handlers</source>
      <outputDirectory>/META-INF</outputDirectory>
      <filtered>false</filtered>
    </file>
    <file>
      <source>src/assemble/spring.schemas</source>
      <outputDirectory>/META-INF</outputDirectory>
      <filtered>false</filtered>
    </file>
  </files>
</assembly>
```
### spring 配置文件

`user-servlet.xml`  中必须配置静态文件，并且添加 `classpath` 路径，因为静态文件是在jar包里的。
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
      http://www.springframework.org/schema/context
      http://www.springframework.org/schema/context/spring-context-3.0.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
 <mvc:annotation-driven/>
 <mvc:resources mapping="/**" location="classpath:web" />
 <!-- 扫描类包，将标注Spring注解的类自动转化为Bean，同时完成Bean的注入 -->
 <context:component-scan base-package="com.albert.web" />
    <bean id="viewResolver"
      class="org.springframework.web.servlet.view.InternalResourceViewResolver"
      p:prefix="/" p:suffix=".html" />
</beans>
```

### web.xml

```
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Spring MVC</display-name>
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
    <servlet-name>user</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>2</load-on-startup>
</servlet>
    <servlet-mapping>
        <servlet-name>user</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

其他的就是 spring 的全局配置文件 `applicationContext.xml`，controller 以及静态文件了，完整项目请点击[这里](https://github.com/albertchendao/demos/java/jetty/HelloWorld-jetty)

补充:
做完之后才发现还有另一种解决方法, 不使用 maven-assembly-plugin 插件，而使用 maven-shade-plugin, maven-assembly-plugin 缺陷是打包时不能将 spring  的 xsd 也输出到最后的 jar 包中, 而 maven-shade-plugin 可以很容易解决这个问题。

```
<plugin>  
    <groupId>org.apache.maven.plugins</groupId>  
    <artifactId>maven-shade-plugin</artifactId>  
    <version>1.4</version>  
    <executions>  
        <execution>  
            <phase>package</phase>  
            <goals>  
                <goal>shade</goal>  
            </goals>  
            <configuration>  
                <transformers>  
                    <transformer  
                        implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">  
                        <resource>META-INF/spring.handlers</resource>  
                    </transformer>  
                    <transformer  
                        implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">  
                        <mainClass>com.chenzhou.examples.Main</mainClass>  
                    </transformer>  
                    <transformer  
                        implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">  
                        <resource>META-INF/spring.schemas</resource>  
                    </transformer>  
                </transformers>  
            </configuration>  
        </execution>  
    </executions>  
</plugin>  
```

然后发现已经有人做过这种[示例项目](https://github.com/s4nchez/jetty-spring-mvc-jar)了, 真是个悲伤的故事 =￣ω￣=

感谢搜索引擎，感谢开源╰(*°▽°*)╯


参考链接：
- [Java EE5 Tutorial](http://docs.oracle.com/javaee/5/tutorial/doc/bnadx.html)
- [常见的web容器](http://blog.csdn.net/liuyinghui523/article/details/38640945)
- [java web容器与servlet容器](http://blog.chinaunix.net/uid-28793431-id-3575527.html)
- [java -D 命令](http://www.blogjava.net/xzclog/archive/2015/01/21/422309.html)
- [maven jetty 容器可执行 jar 包](http://blog.anvard.org/articles/2013/10/09/embedded-jetty-executable-maven.html)
- [jetty-spring-mvc-jar](https://github.com/s4nchez/jetty-spring-mvc-jar)
- [使用 maven-shade-plugin](http://chenzhou123520.iteye.com/blog/1706242)
- [jetty-spring-mvc-jar](https://github.com/s4nchez/jetty-spring-mvc-jar)
