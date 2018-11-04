package com.study.onlineshop.service.impl;


import javax.servlet.http.Cookie;
import java.security.InvalidParameterException;

public class RequestParser {

    public static int getIdfromUri(String uri) {
        String[] split = uri.split("/");
        if (split.length == 4) {
            return Integer.parseInt(split[3]);
        } else {
            throw new InvalidParameterException();
        }
    }

    public static String getToken(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
