/*
 * Copyright (c) Gabriel Sterz 2022
 */

package de.sterzsolutions.ccmavenfx.util;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

/**
 *  database utility Class
 *
 */
public class DatabaseUtil {

    //Database Location and usertable name
    private static String url = "jdbc:sqlite:db/user.db";
    private static final String userTable = "users";

    //create the database File

    /**
     * creates a new Database-File at the given Location
     */
    public void createNewDatabase() {
        try {
            Connection conn = this.connect();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * creates the usertable with the given name
      */
    public void createNewTable() {
        // SQL statement for creating the users table
        String sql = "CREATE TABLE IF NOT EXISTS " + userTable + "(\n"
                + " id integer PRIMARY KEY,\n"
                + " username text NOT NULL,\n"
                + " pwhash text NOT NULL,\n"
                + " apikey text NOT NULL,\n"
                + " secret text NOT NULL\n"
                + ");";
        try{
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * connects to the local Database
     * @return the databse Connection
     */
    private Connection connect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Database Initialisation
     */
    public void dbInit(){
        DatabaseUtil dbUtil = new DatabaseUtil();
        dbUtil.createNewDatabase();
        dbUtil.createNewTable();
    }

    /**
     *
     * @param username The User you're looking for
     * @return true if it's there
     * @throws SQLException
     */
    public boolean userExists(String username) throws SQLException {

        String query = "SELECT * FROM " + userTable;

        try{
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                if(rs.getString("username").equals(username)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }


    public String getUserInfo(String username, String field) throws SQLException {

        String query = "SELECT * FROM " + userTable;
        String info = null;
        try{
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                if(rs.getString("username").equals(username)){
                    info = rs.getString(field);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return info;
    }

    /**
     * enters the new User and his credentials into the database-table
     *
     * @param username
     * @param password password in clear
     * @param apikey
     * @param secret
     * @throws SQLException
     */
    public void addUser(String username, String password, String apikey, String secret) throws SQLException {

        String query = "INSERT INTO users (username, pwhash, apikey, secret) VALUES (?, ?, ?, ?)";
        String pwHash = BCrypt.hashpw(password, BCrypt.gensalt(12));

        try{
            Connection connection = this.connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, pwHash);
            statement.setString(3, apikey);
            statement.setString(4, secret);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}


