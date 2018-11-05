package com.study.onlineshop.context;

import com.study.onlineshop.context.impl.ClassPathApplicationContext;
import com.study.onlineshop.context.impl.XmlBeanDefinitionReader;
import com.study.onlineshop.dao.jdbc.JdbcProductDao;

import javax.sql.DataSource;

public class Test {

    public static void main(String[] args) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader("C:\\Users\\acc\\IdeaProjects\\db2-onlineshop\\context.xml");
        ApplicationContext context = new ClassPathApplicationContext(reader);
        DataSource dataSource = context.getBean(org.apache.commons.dbcp2.BasicDataSource.class);
        JdbcProductDao dao = context.getBean(com.study.onlineshop.dao.jdbc.JdbcProductDao.class);
        System.out.println(dataSource);
        System.out.println(dao);
    }

}
