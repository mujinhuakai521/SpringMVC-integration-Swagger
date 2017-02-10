### Spring MVC 简单登陆示例

#### 父子容器
Spring MVC 的程序入口在 web.xml 配置，一般需要配置一个 Listener，一个Servlet。
```
<context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
```
`ContextLoaderListener`初始化的是 `WebApplicationContext`, 创建后可以从`ServletContext`中获取，`WebApplicationContext`是应用程序内共享的，最多只有一个，如果寻求简单也可以不初始化此容器。与之不同 `DispatcherServlet`可以有多个，并共享一个`WebApplicationContext`容器，每一个`DispatcherServlet`有不同的配置，控制不同的WEB访问。一般将 DAO、Service 层Bean共享的放在`ContextLoaderListener`配置的容器中，将WEB层的Bean放在特定的`DispatcherServlet`配置的容器中。
```
 <servlet>
  	<servlet-name>user</servlet-name>
  	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  	<load-on-startup>2</load-on-startup>
  </servlet>
  <servlet-mapping>
  	<servlet-name>user</servlet-name>
  	<url-pattern>*.html</url-pattern>
  </servlet-mapping>
```
`DispatcherServlet`默认查找WEB-INF下的 servlet名字 + "-servlet.xml"的配置文件,这里就是查找`/WEB-INF/user-servlet.xml`

#### 视图映射
在user-servlet.xml里配置视图映射, 这里采用`InternalResourceViewResolver`解析。
```
 <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
 		p:viewClass="org.springframework.web.servlet.view.JstlView"
 		p:prefix="/WEB-INF/jsp/"
 		p:suffix=".jsp"></bean>
```
这样在Controller中返回的字符串或者`ModelAndView`对象时会将视图名解析成 /WEB-INF/jsp/viewName.jsp

#### 参数绑定

参考链接: <http://www.cnblogs.com/HD/p/4107674.html>


