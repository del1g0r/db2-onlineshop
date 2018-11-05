package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.dao.UserDao;
import com.study.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.study.onlineshop.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcUserDao implements UserDao {

    private static final String GET_ALL_SQL = "SELECT id, name, group_name FROM \"user\"";
    private static final String CHECK_PWD_SQL = "SELECT pswhash = crypt(?, salt) is_checked FROM \"user\" WHERE name = ?";
    private static final String CREATE_SQL = "INSERT INTO \"user\" (name, salt, pswhash, group_name) SELECT ?, t.md5, crypt(?, t.md5), 'GUEST' FROM (SELECT gen_salt('md5') md5) t";
    private static final String GET_SQL = "SELECT id, name, group_name FROM \"user\" WHERE id = ?";
    private static final String GET_BY_NAME_SQL = "SELECT id, name, group_name FROM \"user\" WHERE name = ?";
    private static final String DELETE_SQL = "DELETE FROM \"user\" WHERE id = ?";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private Properties properties;

    public JdbcUserDao(Properties properties) {
        this.properties = properties;
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL)) {

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement prepareGetByNameStatement(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_BY_NAME_SQL);
        statement.setString(1, name);
        return statement;
    }

    @Override
    public User getByName(String name) {
        try (Connection connection = getConnection();
             PreparedStatement statement = prepareGetByNameStatement(connection, name);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement prepareCheckPasswordStatement(Connection connection, String name, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CHECK_PWD_SQL);
        statement.setString(1, password);
        statement.setString(2, name);
        return statement;
    }

    @Override
    public boolean checkPassword(String name, String password) {
        try (Connection connection = getConnection();
             PreparedStatement statement = prepareCheckPasswordStatement(connection, name, password);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getBoolean("is_checked");
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = properties.getProperty("url");
        String name = properties.getProperty("name");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, name, password);
    }
}
