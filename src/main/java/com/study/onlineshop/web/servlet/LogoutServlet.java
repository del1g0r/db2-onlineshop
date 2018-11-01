package com.study.onlineshop.web.servlet;

import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LogoutServlet extends HttpServlet {

    private SecurityService securityService;

    public LogoutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (securityService.checkPermission(this, req)) {
            securityService.logout(req);
            Cookie cookie = new Cookie("user-token", "");
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
