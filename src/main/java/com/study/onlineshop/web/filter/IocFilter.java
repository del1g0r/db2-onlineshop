package com.study.onlineshop.web.filter;

import com.study.ioc.context.ApplicationContext;
import com.study.onlineshop.entity.Group;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.EnumSet;

public abstract class IocFilter extends RoleFilter {

    IocFilter(EnumSet<Group> groups) {
        super(groups);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("context");
        System.out.println("Call deffered initialization " + this);
        context.defferedInjectDepenencies(this);
    }
}
