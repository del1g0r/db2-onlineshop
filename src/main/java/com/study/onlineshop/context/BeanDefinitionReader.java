package com.study.onlineshop.context;

import com.study.onlineshop.context.pojo.BeanDefinition;

import java.util.List;

public interface BeanDefinitionReader {

    List<BeanDefinition> readBeanDefinitions();
}
