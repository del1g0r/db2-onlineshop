package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.PermissionService;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.service.impl.DefaultPermissionService;
import com.study.onlineshop.service.impl.RequestParser;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class AddProductServlet extends HttpServlet {

    private ProductService productService;
    private SecurityService securityService;
    private PermissionService permissionService;

    public AddProductServlet(PermissionService permissionService, SecurityService securityService, ProductService productService) {
        this.productService = productService;
        this.securityService = securityService;
        this.permissionService = permissionService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            PageGenerator pageGenerator = PageGenerator.instance();
            HashMap<String, Object> parameters = new HashMap<>();
            String page = pageGenerator.getPage("addProduct", parameters);
            resp.getWriter().write(page);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            String name = req.getParameter("name");
            LocalDateTime creationDate = LocalDateTime.parse(req.getParameter("creationDate"));
            double price = Double.parseDouble(req.getParameter("price"));
            productService.create(new Product(name, creationDate, price));
            resp.sendRedirect("/");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
