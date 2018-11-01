package com.study.onlineshop.dao.jdbc.mapper;

import com.study.onlineshop.entity.Group;
import com.study.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    // id, name, groupName
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Group.getByName(resultSet.getString("group_name"))
        );
        return user;
    }
}
