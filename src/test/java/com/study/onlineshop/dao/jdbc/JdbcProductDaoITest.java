package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.entity.Product;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class JdbcProductDaoITest {
    @Test
    public void testGetAll() throws Exception {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/db2_onlineshop");
        dataSource.setUser("postgres");
        dataSource.setPassword("123456");

        JdbcTemplate jdbcTemplate = new  JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        jdbcProductDao.jdbcTemplate = jdbcTemplate;

        List<Product> products = jdbcProductDao.getAll();
        for (Product product : products) {
            assertNotNull(product.getName());
            assertNotNull(product.getCreationDate());
        }
    }
}