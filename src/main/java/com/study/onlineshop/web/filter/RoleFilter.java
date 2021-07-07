package com.study.onlineshop.web.filter;

import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public class RoleFilter implements Filter {

    private SecurityService securityService;
    private EnumSet<Group> groups = EnumSet.noneOf(Group.class);

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
                        Group useGroup = session.getUser().getGroup();
                        if (groups.contains(useGroup)) {
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
        for (String strGroup : filterConfig.getInitParameter("groups").split(",")) {
            groups.add(Group.getByName(strGroup));
        }
        securityService = WebApplicationContextUtils.getRequiredWebApplicationContext
                (filterConfig.getServletContext()).getBean(SecurityService.class);
    }

    @Override
    public void destroy() {
    }
}


