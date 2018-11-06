package com.study.onlineshop;

import com.study.onlineshop.context.ApplicationContext;
import com.study.onlineshop.context.impl.ClassPathApplicationContext;
import com.study.onlineshop.context.impl.XmlBeanDefinitionReader;
import com.study.onlineshop.entity.Group;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.web.filter.RoleFilter;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.util.EnumSet;

public class Starter {

    public static void main(String[] args) throws Exception {
        // configure
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader("context.xml");
        ApplicationContextProvider.setApplicationContext(new ClassPathApplicationContext(reader));

        // config web server
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        servletContextHandler.addServlet(new ServletHolder(context.getBean("productsServlet", Servlet.class)), "/products");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("productsServlet", Servlet.class)), "");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("addProductServlet", Servlet.class)), "/product/add");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("editProductServlet", Servlet.class)), "/product/edit/*");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("deleteProductServlet", Servlet.class)), "/product/delete/*");
        //servletContextHandler.addServlet(new ServletHolder(context.getBean("productsApiServlet", Servlet.class)), "/api/v1/products");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("loginServlet", Servlet.class)), "/login");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("logoutServlet", Servlet.class)), "/logout");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("cartServlet", Servlet.class)), "/cart");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("addCartServlet", Servlet.class)), "/cart/add");
        servletContextHandler.addServlet(new ServletHolder(context.getBean("deleteCartServlet", Servlet.class)), "/cart/delete");

        Filter guestFilter = new RoleFilter(context.getBean("securityService", SecurityService.class), EnumSet.of(Group.GUEST, Group.USER, Group.ADMIN));
        Filter userFilter = new RoleFilter(context.getBean("securityService", SecurityService.class), EnumSet.of(Group.USER, Group.ADMIN));
        Filter adminFilter = new RoleFilter(context.getBean("securityService", SecurityService.class), EnumSet.of(Group.ADMIN));

        servletContextHandler.addFilter(new FilterHolder(guestFilter), "/login", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(guestFilter), "/logout", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/products", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/cart", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(userFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.addFilter(new FilterHolder(adminFilter), "/product/*", EnumSet.of(DispatcherType.REQUEST));

        ServerWrapper serverWrapper = context.getBean("serverWrapper", ServerWrapper.class);
        serverWrapper.setServletContextHandler(servletContextHandler);
        serverWrapper.start();
    }
}