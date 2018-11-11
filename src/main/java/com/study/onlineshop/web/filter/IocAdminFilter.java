package com.study.onlineshop.web.filter;

import com.study.onlineshop.entity.Group;

import java.util.EnumSet;

public class IocAdminFilter extends IocFilter {

    public IocAdminFilter() {
        super(EnumSet.of(Group.ADMIN));
    }
}
