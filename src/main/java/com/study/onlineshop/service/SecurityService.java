package com.study.onlineshop.service;

import com.study.onlineshop.entity.Group;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;
import java.util.List;

public interface SecurityService {

    void addPermission(HttpServlet servlet, EnumSet<Group> groups);

    boolean checkPermission(HttpServlet servlet, String userName);

    boolean checkPermission(HttpServlet servlet, HttpServletRequest req);

    String getAuthUserName(HttpServletRequest req);

    String login(String name, String password);

    void logout(HttpServletRequest req);
}
