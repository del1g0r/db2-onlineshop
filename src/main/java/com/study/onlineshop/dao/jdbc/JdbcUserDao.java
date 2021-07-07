package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.dao.UserDao;
import com.study.onlineshop.dao.jdbc.mapper.UserCheckedRowMapper;
import com.study.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.study.onlineshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String GET_ALL_SQL = "SELECT id, name, group_name FROM \"user\"";
    private static final String CHECK_PWD_SQL = "SELECT pswhash = crypt(?, salt) is_checked, id, name, group_name FROM \"user\" WHERE name = ?";
    private static final String CREATE_SQL = "INSERT INTO \"user\" (name, salt, pswhash, group_name) SELECT ?, t.md5, crypt(?, t.md5), 'GUEST' FROM (SELECT gen_salt('md5') md5) t";
    private static final String GET_SQL = "SELECT id, name, group_name FROM \"user\" WHERE id = ?";
    private static final String GET_BY_NAME_SQL = "SELECT id, name, group_name FROM \"user\" WHERE name = ?";
    private static final String DELETE_SQL = "DELETE FROM \"user\" WHERE id = ?";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final UserCheckedRowMapper USER_CHECKED_ROW_MAPPER = new UserCheckedRowMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query(GET_ALL_SQL, USER_ROW_MAPPER);
        return users;
    }

    @Override
    public User getByName(String name) {
        User user = jdbcTemplate.queryForObject(GET_BY_NAME_SQL, new Object[]{name}, USER_ROW_MAPPER);
        return user;
    }

    @Override
    public User checkPassword(String name, String password) {
        User user = jdbcTemplate.queryForObject(CHECK_PWD_SQL, new Object[]{password, name}, USER_CHECKED_ROW_MAPPER);
        return user;
    }
}
