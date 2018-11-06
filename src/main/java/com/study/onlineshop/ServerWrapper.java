package com.study.onlineshop;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServerWrapper {

    private int port;
    private ServletContextHandler servletContextHandler;

    public int getPort() {
        return port;
    }

    public ServletContextHandler getServletContextHandler() {
        return servletContextHandler;
    }

    public void setServletContextHandler(ServletContextHandler servletContextHandler) {
        this.servletContextHandler = servletContextHandler;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        Server server = new Server(port);
        server.setHandler(servletContextHandler);
        server.start();

    }
}
