package com.study.onlineshop.web.servlet;

import com.study.onlineshop.entity.Session;
import com.study.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("session", req.getAttribute("session"));
            parameters.put("purchases", session.getPurchases());
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("cart", parameters);
            resp.getWriter().write(page);
        } else {
            resp.sendRedirect("/");
        }
    }
}
