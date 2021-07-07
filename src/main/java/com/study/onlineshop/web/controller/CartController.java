package com.study.onlineshop.web.controller;


import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.CartService;
import com.study.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    public String get(@RequestAttribute(required = false) Session session,
                      Model model) {
        if (session != null) {
            model.addAttribute("purchases", session.getPurchases());
            model.addAttribute("tempSession", session);
            return "cart";
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/cart/add", method = RequestMethod.POST)
    public String postAdd(@RequestAttribute(required = false) Session session,
                          @RequestParam("id") int productId) {
        if (session != null) {
            cartService.add(session.getPurchases(), productService.get(productId));
            return "redirect:/cart";
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/cart/delete", method = RequestMethod.POST)
    public String postDelete(@RequestAttribute(required = false) Session session,
                             @RequestParam("id") int productId) {
        if (session != null) {
            cartService.delete(session.getPurchases(), productService.get(productId));
            return "redirect:/cart";
        }
        return "redirect:/";
    }
}