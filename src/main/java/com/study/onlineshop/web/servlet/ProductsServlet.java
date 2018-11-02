package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Product;
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

public class ProductsServlet extends HttpServlet {

    private ProductService productService;
    private SecurityService securityService;
    private PermissionService permissionService;

    public ProductsServlet(PermissionService permissionService, SecurityService securityService, ProductService productService) {
        this.productService = productService;
        this.securityService = securityService;
        this.permissionService = permissionService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (permissionService.checkPermission(this, securityService.getGroupByToken(RequestParser.getToken(req.getCookies())))) {
            PageGenerator pageGenerator = PageGenerator.instance();
            List<Product> products = productService.getAll();

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("products", products);

            String page = pageGenerator.getPage("products", parameters);
            resp.getWriter().write(page);
        } else {
            resp.sendRedirect("/login");
        }
    }
}
