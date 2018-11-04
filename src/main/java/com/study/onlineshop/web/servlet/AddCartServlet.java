package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.CartService;
import com.study.onlineshop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCartServlet extends HttpServlet {

    private ProductService productService;
    private CartService cardService;

    public AddCartServlet(CartService cardService, ProductService productService) {
        this.cardService = cardService;
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            cardService.add(session.getPurchases(), productService.get(id));
            resp.sendRedirect("/cart");
        }
    }
}
