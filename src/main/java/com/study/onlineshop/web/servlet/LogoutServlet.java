package com.study.onlineshop.web.servlet;

import com.study.ioc.annotation.ResourceService;
import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.SecurityService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends IocHttpServlet {

    private SecurityService securityService;

    public SecurityService getSecurityService() {
        return securityService;
    }

    @ResourceService
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            securityService.logout(session.getToken());
            Cookie cookie = new Cookie("user-token", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/");
    }
}