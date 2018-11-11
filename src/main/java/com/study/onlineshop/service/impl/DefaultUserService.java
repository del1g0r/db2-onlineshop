package com.study.onlineshop.service.impl;

import com.study.onlineshop.dao.UserDao;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {

    private UserDao userDao;

    private UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User checkUser(String login, String password) {
        if (userDao.checkPassword(login, password)) {
            return userDao.getByName(login);
        }
        return null;
    }
}