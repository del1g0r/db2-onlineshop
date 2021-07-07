package com.study.onlineshop.web.controller;


import com.study.onlineshop.entity.Session;
import com.study.onlineshop.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String postLogin(HttpServletResponse httpServletResponse,
                            @RequestParam("login") String login,
                            @RequestParam("password") String password) {
        Session session = securityService.login(login, password);
        if (session != null) {
            Cookie cookie = new Cookie("user-token", session.getToken());
            httpServletResponse.addCookie(cookie);
            cookie.setMaxAge(securityService.getSessionAge());
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(path = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String postLogout(HttpServletResponse httpServletResponse,
                             @RequestAttribute(required = false) Session session) {
        if (session != null) {
            securityService.logout(session.getToken());
            Cookie cookie = new Cookie("user-token", "");
            cookie.setMaxAge(0);
            httpServletResponse.addCookie(cookie);
        }
        return "redirect:/";
    }
}
