package com.study.onlineshop.web.servlet;

import com.study.ioc.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class IocHttpServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("context");
        System.out.println("Call deffered initialization " + this);
        context.defferedInjectDepenencies(this);
    }
}
