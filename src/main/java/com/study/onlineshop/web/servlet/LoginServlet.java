package com.study.onlineshop.web.servlet;

import com.study.onlineshop.service.PermissionService;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.service.impl.RequestParser;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    private SecurityService securityService;
    private PermissionService permissionService;

    public LoginServlet(PermissionService permissionService, SecurityService securityService) {
        this.securityService = securityService;
        this.permissionService = permissionService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            PageGenerator pageGenerator = PageGenerator.instance();
            HashMap<String, Object> parameters = new HashMap<>();

            String page = pageGenerator.getPage("login", parameters);
            resp.getWriter().write(page);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            System.out.println(login + " : " + password);

            String userToken = securityService.login(login, password);
            if (userToken != null) {
                Cookie cookie = new Cookie("user-token", userToken);
                resp.addCookie(cookie);
                resp.sendRedirect("/");
            } else {
                resp.sendRedirect("/login");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
