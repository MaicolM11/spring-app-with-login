package com.uptc.utils;

public class Url {
    public static final String DOMAIN = "http://localhost:3000";
    
    public static final String EMPLOYE_RESOURCE = "/api/employees";
    public static final String AUTH_RESOURCE = "/auth";
    
    public static final String COMFIRM_TOKEN = DOMAIN + AUTH_RESOURCE + "/confirm?token=";
}
