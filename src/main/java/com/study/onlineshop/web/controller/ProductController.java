package com.study.onlineshop.web.controller;

import com.study.onlineshop.entity.Product;
import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = {"/", "/products"}, method = RequestMethod.GET)
    public String get(@RequestAttribute(required = false) Session session,
                      Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("tempSession", session);
        return "products";
    }

    @RequestMapping(path = "/product/add", method = RequestMethod.GET)
    public String getAdd() {
        return "addProduct";
    }

    @RequestMapping(path = "/product/add", method = RequestMethod.POST)
    public String postAdd(@RequestParam("name") String name,
                          @RequestParam("creationDate") String creationDate,
                          @RequestParam("price") double price) {
        productService.create(new Product(name, LocalDateTime.parse(creationDate), price));
        return "redirect:/";
    }

    @RequestMapping(path = "/product/delete/{productId}", method = RequestMethod.POST)
    public String postDelete(@PathVariable int productId) {
        productService.delete(productId);
        return "redirect:/";
    }

    @RequestMapping(path = "/product/edit/{productId}", method = RequestMethod.GET)
    public String getEdit(@PathVariable int productId,
                          Model model) {
        model.addAttribute("product", productService.get(productId));
        return "editProduct";
    }

    @RequestMapping(path = "/product/edit/{productId}", method = RequestMethod.POST)
    public String podtEdit(@PathVariable int productId,
                           @RequestParam("name") String name,
                           @RequestParam("creationDate") String creationDate,
                           @RequestParam("price") double price) {
        productService.update(new Product(productId, name, LocalDateTime.parse(creationDate), price));
        return "redirect:/";
    }
}
