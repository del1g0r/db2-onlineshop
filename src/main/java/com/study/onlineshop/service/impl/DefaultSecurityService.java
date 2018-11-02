package com.study.onlineshop.service.impl;

import com.study.onlineshop.dao.UserDao;
import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.SecurityService;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.UUID;

public class DefaultSecurityService implements SecurityService {

    private UserDao userDao;
    private HashMap<String, String> activeTokens;

    public DefaultSecurityService(UserDao userDao) {
        this.userDao = userDao;
        activeTokens = new HashMap<>();
    }

    @Override
    public String getUserNameByToken(String token) {
        return activeTokens.get(token);
    }

    @Override
    public User getUserByToken(String token) {
        return userDao.getByName(getUserNameByToken(token));
    }

    @Override
    public Group getGroupByToken(String token) {
        User user = getUserByToken(token);
        return user == null ? Group.GUEST : user.getGroup();
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
        activeTokens.remove(RequestParser.getToken(req.getCookies()));
    }
}
