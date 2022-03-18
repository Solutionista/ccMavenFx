
/*
 * Copyright (c) Gabriel Sterz 2022
 */

package de.sterzsolutions.ccmavenfx.util;

public class UserInfo {

    final String username;
    final String password;
    final String apiKey;
    final String secret;



    public UserInfo(String username, String password, String apiKey, String secret) {
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
        this.secret = secret;
    }

    @Override
    public String toString() {
        return username;
    }
}
