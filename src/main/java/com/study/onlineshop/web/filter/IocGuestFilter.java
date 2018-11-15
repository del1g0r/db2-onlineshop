package com.study.onlineshop.web.filter;

import com.study.onlineshop.entity.Group;

import java.util.EnumSet;

public class IocGuestFilter extends IocFilter {

    public IocGuestFilter() {
        super(EnumSet.of(Group.GUEST, Group.USER, Group.ADMIN));
    }
}
