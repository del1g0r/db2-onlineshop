package com.study.onlineshop.web.servlet;

import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.impl.RequestParser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {

    private ProductService productService;

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productService.delete(RequestParser.getIdfromUri(req.getRequestURI()));
        resp.sendRedirect("/");
    }

}
