package com.study.onlineshop.service.impl;

import com.study.onlineshop.entity.Session;
import com.study.onlineshop.entity.User;
import com.study.onlineshop.service.SecurityService;
import com.study.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {

    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;
    @Value("${web.sessionAge}")
    private int sessionAge;

    public DefaultSecurityService() {
        super();
    }

    @Override
    public void setSessionAge(int age) {
        sessionAge = age;
    }

    @Override
    public int getSessionAge() {
        return sessionAge;
    }

    synchronized Session getOrCreateSession(User user) {
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}