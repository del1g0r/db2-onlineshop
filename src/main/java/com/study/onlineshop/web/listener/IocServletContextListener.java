package com.study.onlineshop.web.listener;

import com.study.ioc.ApplicationContextFactory;
import com.study.ioc.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.InputStream;

public class IocServletContextListener implements ServletContextListener {

    private ApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        //String contextPath = servletContext.getInitParameter("contextPath");
        String contextPath = "context.xml";

        System.out.println("Context initialization");
        try (InputStream contextStream = new FileInputStream(contextPath);) {
            context = ApplicationContextFactory.loadApplicationContext(contextStream);
            servletContext.setAttribute("context", context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
