package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Product;
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

    public DeleteProductServlet(SecurityService securityService, ProductService productService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (securityService.checkPermission(this, req)) {
            productService.delete(RequestParser.getIdfromUri(req.getRequestURI()));
            resp.sendRedirect("/");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
