package com.study.onlineshop.service;

import com.study.onlineshop.entity.Group;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;

public interface PermissionService {

    void addPermission(HttpServlet servlet, EnumSet<Group> groups);

    boolean checkPermission(HttpServlet servlet, Group group);

}
