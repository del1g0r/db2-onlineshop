package com.study.onlineshop;

import com.study.onlineshop.dao.jdbc.JdbcProductDao;
import com.study.onlineshop.dao.jdbc.JdbcUserDao;
import com.study.onlineshop.entity.Group;
import com.study.onlineshop.service.impl.DefaultCartService;
import com.study.onlineshop.service.impl.DefaultProductService;
import com.study.onlineshop.service.impl.DefaultSecurityService;
import com.study.onlineshop.service.impl.DefaultUserService;
import com.study.onlineshop.web.filter.RoleFilter;
import com.study.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

public class Starter {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        if (new File("application.properties").exists()) {
            try (InputStream inputStream = new FileInputStream("application.properties");
            ) {
                properties.load(inputStream);
            }
        } else {
            System.out.println("The application.properties file is not found. Uses default values");
            properties.put("url", "jdbc:postgresql://localhost/db2_onlineshop");
            properties.put("name", "postgres");
            properties.put("password", "123456");
            properties.put("port", "8081");
        }

        // configure daos
        JdbcProductDao jdbcProductDao = new JdbcProductDao(properties);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(properties);

        // configure services
        DefaultUserService defaultUserService = new DefaultUserService(jdbcUserDao);
        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(defaultUserService);
        DefaultProductService defaultProductService = new DefaultProductService(jdbcProductDao);
        DefaultCartService defaultCartService = new DefaultCartService();

        // store
        List<String> activeTokens = new ArrayList<>();

        // servlets
        ProductsServlet productsServlet = new ProductsServlet(defaultProductService);
        AddProductServlet addProductServlet = new AddProductServlet(defaultProductService);
        EditProductServlet editProductServlet = new EditProductServlet(defaultProductService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(defaultProductService);
        ProductsApiServlet productsApiServlet = new ProductsApiServlet(defaultProductService);
        LoginServlet loginServlet = new LoginServlet(defaultSecurityService);
        LogoutServlet logoutServlet = new LogoutServlet(defaultSecurityService);
        CartServlet cartServlet = new CartServlet();
        AddCartServlet addCartServlet = new AddCartServlet(defaultCartService, defaultProductService);
        DeleteCartServlet deleteCartServlet = new DeleteCartServlet(defaultCartService, defaultProductService);

        // config web server
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "/products");
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/product/add");
        servletContextHandler.addServlet(new ServletHolder(editProductServlet), "/product/edit/*");
        servletContextHandler.addServlet(new ServletHolder(deleteProductServlet), "/product/delete/*");
        servletContextHandler.addServlet(new ServletHolder(productsApiServlet), "/api/v1/products");
        servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        servletContextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
        servletContextHandler.addServlet(new ServletHolder(cartServlet), "/cart");
        servletContextHandler.addServlet(new ServletHolder(addCartServlet), "/cart/add");
        servletContextHandler.addServlet(new ServletHolder(deleteCartServlet), "/cart/delete");

        Filter guestFilter = new RoleFilter(defaultSecurityService, EnumSet.of(Group.GUEST, Group.USER, Group.ADMIN));
        Filter userFilter = new RoleFilter(defaultSecurityService, EnumSet.of(Group.USER, Group.ADMIN));
        Filter adminFilter = new RoleFilter(defaultSecurityService, EnumSet.of(Group.ADMIN));

        servletContextHandler.addFilter(new FilterHolder(guestFilter), "/login", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(guestFilter), "/logout", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/products", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/cart", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(adminFilter), "/product/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();
    }
}