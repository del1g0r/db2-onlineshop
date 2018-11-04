package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.service.PermissionService;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.service.impl.RequestParser;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class DeleteProductServlet extends HttpServlet {

    private ProductService productService;
    private SecurityService securityService;
    private PermissionService permissionService;

    public DeleteProductServlet(PermissionService permissionService, SecurityService securityService, ProductService productService) {
        this.productService = productService;
        this.securityService = securityService;
        this.permissionService = permissionService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            productService.delete(RequestParser.getIdfromUri(req.getRequestURI()));
            resp.sendRedirect("/");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
