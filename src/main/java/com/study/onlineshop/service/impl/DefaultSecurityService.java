package com.study.onlineshop.service.impl;

import com.study.onlineshop.entity.Session;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultSecurityService implements SecurityService {

    private HashMap<String, Session> sessions = new HashMap<>();
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    int sessionAge;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setSessionAge(int age) {
        sessionAge = age;
    }

    @Override
    public int getSessionAge() {
        return sessionAge;
    }

    Session getOrCreateSession(User user) {
        synchronized (this) {
            for (Map.Entry<String, Session> entry : sessions.entrySet()) {
                Session session = entry.getValue();
                if (user.getId() == session.getUser().getId()) {
                    session.setExpireTime(LocalDateTime.now().plusSeconds(sessionAge));
                    return session;
                }
            }
            Session session = new Session();
            session.setUser(user);
            session.setToken(UUID.randomUUID().toString());
            session.setExpireTime(LocalDateTime.now().plusSeconds(sessionAge));
            session.setPurchases(new ArrayList<>());
            sessions.put(session.getToken(), session);
            return session;
        }
    }

    @Override
    public Session login(String login, String password) {
        User user = userService.checkUser(login, password);
        if (user != null) {
            return getOrCreateSession(user);
        }
        return null;
    }

    @Override
    public void logout(String token) {
        sessions.remove(token);
    }

    @Override
    public Session getSession(String token) {
        Session session = sessions.get(token);
        if (session != null) {
            if (session.getExpireTime().isAfter(LocalDateTime.now())) {
                return session;
            }
            sessions.remove(token);
        }
        return null;
    }
}