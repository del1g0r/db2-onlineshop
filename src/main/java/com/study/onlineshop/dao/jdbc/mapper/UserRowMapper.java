package com.study.onlineshop.dao.jdbc.mapper;

import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Group.getByName(resultSet.getString("group_name")));
    }
}