package com.study.onlineshop.service;

import com.study.onlineshop.entity.Session;

public interface SecurityService {

    Session login(String login, String password);

    void logout(String token);

    Session getSession(String token);
}
