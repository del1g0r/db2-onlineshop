package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.CartService;
import com.study.onlineshop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartServlet extends HttpServlet {

    private ProductService productService;
    private CartService cartService;

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            cartService.delete(session.getPurchases(), productService.get(id));
            resp.sendRedirect("/cart");
        }
    }
}
