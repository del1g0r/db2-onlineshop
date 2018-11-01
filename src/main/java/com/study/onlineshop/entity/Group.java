package com.study.onlineshop.entity;

public enum Group {

    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Group getByName(String name) {
        Group[] groups = values();
        for (Group group : groups) {
            if (group.name.equals(name)) {
                return group;
            }
        }
        throw new IllegalArgumentException("The group is not found: " + name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
