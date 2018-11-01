package com.study.onlineshop.service.impl;

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
}
