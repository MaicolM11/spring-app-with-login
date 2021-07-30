package com.uptc.models;

public enum EUserRole {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + this.name();
    }
    
}
