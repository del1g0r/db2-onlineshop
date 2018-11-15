package com.study.onlineshop.web.filter;

import com.study.ioc.annotation.ResourceService;
import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.SecurityService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public abstract class RoleFilter implements Filter {

    private SecurityService securityService;
    private EnumSet<Group> groups;

    public RoleFilter(SecurityService securityService, EnumSet<Group> groups) {
        this.securityService = securityService;
        this.groups = groups;
    }

    public RoleFilter(EnumSet<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        boolean isAuth = groups.contains(Group.GUEST);
        Cookie[] cookies;
        Session session = null;
        if ((cookies = httpServletRequest.getCookies()) != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    String token = cookie.getValue();
                    session = securityService.getSession(token);
                    if (session != null) {
                        if (groups.contains(session.getUser().getGroup())) {
                            isAuth = true;
                        }
                    }
                    break;
                }
            }
        }

        if (isAuth) {
            if (servletRequest.getAttribute("session") == null) {
                servletRequest.setAttribute("session", session);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    @ResourceService
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

