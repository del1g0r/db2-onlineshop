package com.study.onlineshop;

import com.study.onlineshop.context.ApplicationContext;

public class ApplicationContextProvider {

    public static final ApplicationContextProvider INSTANCE = new ApplicationContextProvider();

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
