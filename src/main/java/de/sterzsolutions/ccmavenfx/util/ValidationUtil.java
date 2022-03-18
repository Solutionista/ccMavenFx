/*
 * Copyright (c) Gabriel Sterz 2022
 */

package de.sterzsolutions.ccmavenfx.util;


import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class ValidationUtil {


    /**
     * checks whether the api/secret combination is valid
     * @param api
     * @param secret
     * @return
     * @throws IOException
     * @throws ExchangeException
     */
    public boolean validateBinanceApi(String api, String secret) throws IOException, ExchangeException {

        ExchangeSpecification exSpec = new BinanceExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(api);
        exSpec.setSecretKey(secret);
        Exchange binance = ExchangeFactory.INSTANCE.createExchange(exSpec);
        try{
            if(binance.getAccountService().getAccountInfo() != null){
            return true;
            }
        } catch (ExchangeException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param user user which has to be checked
     * @param password password as entered
     * @return true if password matches
     * @throws SQLException
     */
    public boolean validateUserLogin(String user, String password) throws SQLException {

        boolean yesOrNo = false;
        DatabaseUtil dbUtil = new DatabaseUtil();
        if (dbUtil.userExists(user)){
            if (BCrypt.checkpw(password, dbUtil.getUserInfo(user, "pwhash"))){
                System.out.println("It matches");
                yesOrNo = true;
            }
        }
        return yesOrNo;
    }

    /**
     * just to make sure, there`s no SQL Injection
     * @param string   String which should be checked
     * @return true if valid
     */
    private boolean validate(String string) {

        // No field separator!
        final String SPECIAL_CHARS = "@#$'?()~-_";
        if (string.contains(":")){
            return false;
        }
        if (string.length()>255){
            return false;
        }

        char[] chars = string.toCharArray();
        for(char c: chars ) {
            if (Character.isLetterOrDigit(c)) continue;
            if (SPECIAL_CHARS.indexOf(c) >= 0) continue;
            return false;
        }
        return true;
    }

}
