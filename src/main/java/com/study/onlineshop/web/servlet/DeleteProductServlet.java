package com.study.onlineshop.web.servlet;

import com.study.ioc.annotation.ResourceService;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.impl.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends IocHttpServlet {

    private ProductService productService;

    public ProductService getProductService() {
        return productService;
    }

    @ResourceService
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productService.delete(RequestParser.getIdfromUri(req.getRequestURI()));
        resp.sendRedirect("/");
    }

}
