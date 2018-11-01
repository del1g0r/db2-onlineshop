package com.study.onlineshop.dao;

import com.study.onlineshop.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getByName(String name);

    boolean checkPassword(String name, String password);
}
