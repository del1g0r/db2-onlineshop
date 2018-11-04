package com.study.onlineshop.service;

import com.study.onlineshop.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User checkUser(String login, String password);
}
