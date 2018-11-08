package com.study.onlineshop;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServerWrapper {

    private int port;
    private HandlerList handlerList;

    public int getPort() {
        return port;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(HandlerList handlerList) {
        this.handlerList = handlerList;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        Server server = new Server(port);
        server.setHandler(handlerList);
        server.start();
    }
}
