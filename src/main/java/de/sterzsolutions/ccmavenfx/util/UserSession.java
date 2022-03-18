/*
 * Copyright (c) Gabriel Sterz 2022
 */

package de.sterzsolutions.ccmavenfx.util;

/**
 *  user singleton
 *
 */
public class UserSession {

    private static UserSession INSTANCE = new UserSession("","","");

    public String user;
    public String apiKey;
    public String secret;



    private UserSession(String user, String apiKey, String secret){
        this.user = user;
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public static UserSession getInstance() {
        return INSTANCE;
    }

    // Getter and Setter Section
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
}



