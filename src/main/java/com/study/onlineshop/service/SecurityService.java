package com.study.onlineshop.service;

import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    String getUserNameByToken(String token);

    User getUserByToken(String token);

    Group getGroupByToken(String token);

    String login(String name, String password);

    void logout(HttpServletRequest req);
}
