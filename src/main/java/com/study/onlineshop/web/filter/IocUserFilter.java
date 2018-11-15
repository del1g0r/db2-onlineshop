package com.study.onlineshop.web.filter;

import com.study.onlineshop.entity.Group;

import java.util.EnumSet;

public class IocUserFilter extends IocFilter {

    public IocUserFilter() {
        super(EnumSet.of(Group.ADMIN, Group.USER));
    }
}
