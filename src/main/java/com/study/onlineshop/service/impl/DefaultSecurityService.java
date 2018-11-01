package com.study.onlineshop.service.impl;

import com.study.onlineshop.dao.UserDao;
import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.SecurityService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.UUID;

public class DefaultSecurityService implements SecurityService {

    private UserDao userDao;
    private HashMap<HttpServlet, EnumSet<Group>> permissions;
    private HashMap<String, String> activeTokens;

    public DefaultSecurityService(UserDao userDao) {
        this.userDao = userDao;
        permissions = new HashMap<>();
        activeTokens = new HashMap<>();
    }

    @Override
    public void addPermission(HttpServlet servlet, EnumSet<Group> groups) {
        permissions.put(servlet, groups);
    }

    @Override
    public boolean checkPermission(HttpServlet servlet, String userName) {
        EnumSet<Group> groups = permissions.get(servlet);
        User user = userDao.getByName(userName);
        return groups.contains(user == null ? Group.GUEST : user.getGroup());
    }

    @Override
    public boolean checkPermission(HttpServlet servlet, HttpServletRequest req) {
        return checkPermission(servlet, getAuthUserName(req));
    }

    private String getTocken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public String getAuthUserName(HttpServletRequest req) {
        return activeTokens.get(getTocken(req));
    }

    @Override
    public String login(String name, String password) {
        if (userDao.checkPassword(name, password)) {
            String userToken = UUID.randomUUID().toString();
            activeTokens.put(userToken, name);
            return userToken;
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest req) {
        activeTokens.remove(getTocken(req));
    }
}
