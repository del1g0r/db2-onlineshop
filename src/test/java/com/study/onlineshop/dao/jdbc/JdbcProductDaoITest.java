package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.entity.Product;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class JdbcProductDaoITest {
    @Test
    public void testGetAll() throws Exception {
        Properties properties = new Properties();
        properties.put("url", "jdbc:postgresql://localhost/db2_onlineshop");
        properties.put("name", "postgres");
        properties.put("password", "123456");

        JdbcProductDao jdbcProductDao = new JdbcProductDao(properties);
        List<Product> products = jdbcProductDao.getAll();

        for (Product product : products) {
            assertNotNull(product.getName());
            assertNotNull(product.getCreationDate());
        }
    }

}