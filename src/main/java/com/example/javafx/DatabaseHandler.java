package com.example.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ": " + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO "  + Const.USER_TABLE + "(" + Const.USERS_FIRSTNAME + ","
                                        + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + ","
                                        + Const.USERS_PASSWORD + "," + Const.USERS_LOCATION + ","
                                        + Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, user.getFirstName());
        prSt.setString(2, user.getLastName());
        prSt.setString(3, user.getUserName());
        prSt.setString(4, user.getPassword());
        prSt.setString(5, user.getLocation());
        prSt.setString(6, user.getGender());
        prSt.executeUpdate();
    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM "  + Const.USER_TABLE + " WHERE "  + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, user.getUserName());
        prSt.setString(2, user.getPassword());
        resSet = prSt.executeQuery();
        return resSet;
    }

}
