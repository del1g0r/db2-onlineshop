package com.study.onlineshop;

import com.study.onlineshop.dao.jdbc.JdbcProductDao;
import com.study.onlineshop.service.impl.DefaultProductService;
import com.study.onlineshop.web.servlet.ProductsApiServlet;
import com.study.onlineshop.web.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        // configure daos
        JdbcProductDao jdbcProductDao = new JdbcProductDao();

        // configure services
        DefaultProductService defaultProductService = new DefaultProductService(jdbcProductDao);

        // servlets
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(defaultProductService);
        ProductsApiServlet productsApiServlet = new ProductsApiServlet(defaultProductService);

        // config web server
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "/products");
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "/");

        servletContextHandler.addServlet(new ServletHolder(productsApiServlet), "/api/v1/products");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}
