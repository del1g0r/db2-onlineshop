package com.study.onlineshop.service.impl;

import com.study.onlineshop.entity.Group;
import com.study.onlineshop.service.PermissionService;

import javax.servlet.http.HttpServlet;
import java.util.EnumSet;
import java.util.HashMap;

public class DefaultPermissionService  implements PermissionService {

    private HashMap<HttpServlet, EnumSet<Group>> permissions;

    public DefaultPermissionService() {
        permissions = new HashMap<>();
    }
    @Override
    public void addPermission(HttpServlet servlet, EnumSet<Group> groups) {
        permissions.put(servlet, groups);
    }

    @Override
    public boolean checkPermission(HttpServlet servlet, Group group) {
        EnumSet<Group> groups = permissions.get(servlet);
        return groups.contains(group == null ? Group.GUEST : group);
    }
}
