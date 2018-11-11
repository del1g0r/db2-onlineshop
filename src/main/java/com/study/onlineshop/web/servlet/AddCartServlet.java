package com.study.onlineshop.web.servlet;

import com.study.ioc.annotation.ResourceService;
import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.CartService;
import com.study.onlineshop.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCartServlet extends IocHttpServlet {

    private ProductService productService;
    private CartService cartService;

    public ProductService getProductService() {
        return productService;
    }

    @ResourceService
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public CartService getCartService() {
        return cartService;
    }

    @ResourceService
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            cartService.add(session.getPurchases(), productService.get(id));
            resp.sendRedirect("/cart");
        }
    }
}
