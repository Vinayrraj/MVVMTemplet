package com.vinay.library.data.source.remote;

public interface APIExtras {

    public interface Key {
        String GRANT_TYPE = "grant_type";
        String USERNAME = "Username";
        String PASSWORD = "Password";
        String SCOPE = "Scope";
        String CLIENT_ID = "client_id";
        String CLIENT_SECRET = "client_secret";
        String REFRESH_TOKEN = "refresh_token";
    }


    public interface Values {
        String APPLICATION_JSON = "application/json";
        String GRANT_TYPE_PASSWORD = "password";
        String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
        String SCOPE_DOCTOR = "Doctor";
        String SCOPE_PATIENT = "Patient";
        String BLANK = "";

    }
}
