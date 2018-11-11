package com.study.onlineshop.dao.jdbc;

import com.study.onlineshop.entity.Product;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class JdbcProductDaoITest {
    @Test
    public void testGetAll() throws Exception {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setUrl("jdbc:postgresql://localhost/db2_onlineshop");
        source.setUser("postgres");
        source.setPassword("123456");

        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        jdbcProductDao.setSource(source);

        List<Product> products = jdbcProductDao.getAll();
        for (Product product : products) {
            assertNotNull(product.getName());
            assertNotNull(product.getCreationDate());
        }
    }
}