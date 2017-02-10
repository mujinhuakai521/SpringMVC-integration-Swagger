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
