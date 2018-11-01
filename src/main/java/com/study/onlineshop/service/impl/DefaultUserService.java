package com.study.onlineshop.service.impl;

import com.study.onlineshop.dao.UserDao;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {

    private UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}