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
