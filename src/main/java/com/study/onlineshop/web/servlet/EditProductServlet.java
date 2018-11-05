package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.service.ProductService;
import com.study.onlineshop.service.impl.RequestParser;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class EditProductServlet extends HttpServlet {

    private ProductService productService;

    public EditProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("product", productService.get(RequestParser.getIdfromUri(req.getRequestURI())));
        String page = pageGenerator.getPage("editProduct", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = RequestParser.getIdfromUri(req.getRequestURI());
        String name = req.getParameter("name");
        LocalDateTime creationDate = LocalDateTime.parse(req.getParameter("creationDate"));
        double price = Double.parseDouble(req.getParameter("price"));
        productService.update(new Product(id, name, creationDate, price));
        resp.sendRedirect("/");
    }
}
