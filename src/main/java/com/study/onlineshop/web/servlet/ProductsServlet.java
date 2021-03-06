package com.study.onlineshop.web.servlet;

import com.study.ioc.annotation.ResourceService;
import com.study.onlineshop.entity.Product;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ProductsServlet extends IocHttpServlet {

    private ProductService productService;

    public ProductService getProductService() {
        return productService;
    }

    @ResourceService
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        List<Product> products = productService.getAll();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        parameters.put("session", req.getAttribute("session"));
        String page = pageGenerator.getPage("products", parameters);
        resp.getWriter().write(page);
    }
}
