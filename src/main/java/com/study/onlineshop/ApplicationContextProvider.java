package com.study.onlineshop;

import com.study.onlineshop.context.ApplicationContext;

public class ApplicationContextProvider {

    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
