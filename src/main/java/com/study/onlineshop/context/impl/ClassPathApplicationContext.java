package com.study.onlineshop.context.impl;

import com.study.onlineshop.context.*;
import com.study.onlineshop.context.exception.BeanInstantiationException;
import com.study.onlineshop.context.pojo.Bean;
import com.study.onlineshop.context.pojo.BeanDefinition;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ClassPathApplicationContext implements ApplicationContext {

    private Map<String, Bean> beans;

    public ClassPathApplicationContext(BeanDefinitionReader reader) {
        List<BeanDefinition> beanDefinitions = reader.readBeanDefinitions();
        this.beans = injectRefDepenencies(beanDefinitions
                , injectValueDepenencies(beanDefinitions
                        , constructBeans(beanDefinitions))
        );
    }

    @Override
    public Object getBean(String id) {
        return beans.get(id);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        Iterator iterator = beans.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Bean bean = (Bean)entry.getValue();
            Object object = bean.getValue();
            if (object.getClass().equals(clazz))
                return (T) object;
        }
        return null;
    }

    @Override
    public <T> T getBean(String id, Class<T> clazz) {
        return (T) beans.get(id).getValue();
    }

    private Map<String, Bean> constructBeans(List<BeanDefinition> beanDefinitions) {
        try {
            Map<String, Bean> beans = new HashMap<>();
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class<?> clazz = Class.forName(beanDefinition.getClassName());
                Constructor<?> constructor = clazz.getConstructor();
                Object object = constructor.newInstance();
                Bean bean = new Bean();
                bean.setId(beanDefinition.getId());
                bean.setValue(object);
                beans.put(bean.getId(), bean);
            }
            return beans;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            throw new BeanInstantiationException("Can't construct bean", e);
        }
    }

    private Method getMethodByName(String methodName, Class clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                return method;
            }
        }
        throw new BeanInstantiationException("Method is not found: " + methodName, null);
    }

    private Map<String, Bean> injectValueDepenencies(List<BeanDefinition> beanDefinitions, Map<String, Bean> beans) {
        try {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Bean bean = beans.get(beanDefinition.getId());
                Object object = bean.getValue();
                Map<String, String> dependencies = beanDefinition.getValDependencies();
                Iterator iterator = dependencies.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    Method method = getMethodByName("set" + entry.getKey(), object.getClass());
                    method.invoke(object, entry.getValue());
                }
            }
            return beans;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new BeanInstantiationException("Can't inject value", e);
        }
    }

    private Map<String, Bean> injectRefDepenencies
            (List<BeanDefinition> beanDefinitions, Map<String, Bean> beans) {
        try {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Bean bean = beans.get(beanDefinition.getId());
                Object object = bean.getValue();
                Map<String, String> dependencies = beanDefinition.getRefDependencies();
                Iterator iterator = dependencies.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    Method method = getMethodByName("set" + entry.getKey(), object.getClass());
                    method.invoke(object, beans.get(entry.getValue()).getValue());
                }
            }
            return beans;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new BeanInstantiationException("Can't inject reference", e);
        }
    }
}