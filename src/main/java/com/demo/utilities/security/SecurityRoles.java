package com.demo.utilities.security;

public enum SecurityRoles {

    ADMIN("ADMIN"), USER("USER");

    private String name;

    private SecurityRoles(String name){

        this.name = name;

    }

    public String getName() {
        return name;
    }

}
