package com.study.onlineshop.web.servlet;

import com.study.onlineshop.service.PermissionService;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.service.impl.RequestParser;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LogoutServlet extends HttpServlet {

    private SecurityService securityService;
    private PermissionService permissionService;

    public LogoutServlet(PermissionService permissionService, SecurityService securityService) {
        this.securityService = securityService;
        this.permissionService = permissionService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            securityService.logout(req);
            Cookie cookie = new Cookie("user-token", "");
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
