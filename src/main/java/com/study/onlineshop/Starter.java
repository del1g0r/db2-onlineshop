package com.study.onlineshop;

import com.study.onlineshop.dao.jdbc.JdbcProductDao;
import com.study.onlineshop.dao.jdbc.JdbcUserDao;
import com.study.onlineshop.entity.Group;
import com.study.onlineshop.service.impl.DefaultPermissionService;
import com.study.onlineshop.service.impl.DefaultProductService;
import com.study.onlineshop.service.impl.DefaultSecurityService;
import com.study.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

public class Starter {

    public static void main(String[] args) throws Exception {
        try (InputStream inputStream = new FileInputStream("application.properties");
        ) {
            Properties properties = new Properties();
            properties.load(inputStream);

            // configure daos
            JdbcProductDao jdbcProductDao = new JdbcProductDao(properties);
            JdbcUserDao jdbcUserDao = new JdbcUserDao(properties);

            // configure services
            DefaultProductService defaultProductService = new DefaultProductService(jdbcProductDao);
            DefaultSecurityService defaultSecurityService = new DefaultSecurityService(jdbcUserDao);
            DefaultPermissionService defaultPermissionService = new DefaultPermissionService();

            // store
            List<String> activeTokens = new ArrayList<>();

            // servlets
            ProductsServlet productsServlet = new ProductsServlet(defaultPermissionService, defaultSecurityService, defaultProductService);
            AddProductServlet addProductServlet = new AddProductServlet(defaultPermissionService, defaultSecurityService, defaultProductService);
            EditProductServlet editProductServlet = new EditProductServlet(defaultPermissionService, defaultSecurityService, defaultProductService);
            DeleteProductServlet deleteProductServlet = new DeleteProductServlet(defaultPermissionService, defaultSecurityService, defaultProductService);
            ProductsApiServlet productsApiServlet = new ProductsApiServlet(defaultProductService);
            LoginServlet loginServlet = new LoginServlet(defaultPermissionService, defaultSecurityService);
            LogoutServlet logoutServlet = new LogoutServlet(defaultPermissionService, defaultSecurityService);

            // config web server
            ServletContextHandler servletContextHandler = new ServletContextHandler();
            servletContextHandler.addServlet(new ServletHolder(productsServlet), "/products");
            servletContextHandler.addServlet(new ServletHolder(productsServlet), "/");
            defaultPermissionService.addPermission(productsServlet, EnumSet.of(Group.USER, Group.ADMIN));

            servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/product/add");
            defaultPermissionService.addPermission(addProductServlet, EnumSet.of(Group.ADMIN));
            servletContextHandler.addServlet(new ServletHolder(editProductServlet), "/product/edit/*");
            defaultPermissionService.addPermission(editProductServlet, EnumSet.of(Group.ADMIN));
            servletContextHandler.addServlet(new ServletHolder(deleteProductServlet), "/product/delete/*");
            defaultPermissionService.addPermission(deleteProductServlet, EnumSet.of(Group.ADMIN));

            servletContextHandler.addServlet(new ServletHolder(productsApiServlet), "/api/v1/products");

            servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
            defaultPermissionService.addPermission(loginServlet, EnumSet.of(Group.GUEST));
            servletContextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
            defaultPermissionService.addPermission(logoutServlet, EnumSet.of(Group.USER, Group.ADMIN));

            Server server = new Server(Integer.parseInt(properties.getProperty("port")));
            server.setHandler(servletContextHandler);
            server.start();
        }
    }
}
