package Sql;

import Commands.Utilities.MovieList.MovieList;
import Commands.Utilities.MovieList.MovieListImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserSql {


    private static Connection connection;

    public UserSql() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(Private.sqlURL, Private.sqlUsername, Private.sqlPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    public static void getConn() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(Private.sqlURL, Private.sqlUsername, Private.sqlPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    public static void createTable() {
        try (PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS userList" +
                "(" +
                " userID VARCHAR(256)," +
                " title VARCHAR(256) NOT NULL," +
                " list LONGTEXT NOT NULL" +
                ");")) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLists(String userID) {
        List<String> tables = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT title FROM userList " +
                        "where userID = ?;")) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tables.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public static List<String> getItems(String userID, String title) {
        List<String> items = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT list FROM userList " +
                        "where (userID = ? and title = ?);")) {
            ps.setString(1, userID);
            ps.setString(2, title);
            ResultSet rs = ps.executeQuery();
            String item = "";
            if (rs.next()) {
                item = rs.getString("list");
            }
            items = Arrays.asList(item.split("`\\s*"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void addList(String userID, String title, String list) {

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO userList (userID, title, list) " +
                        "VALUES (?, ?, ?);")) {
            ps.setString(1, userID);
            ps.setString(2, title);
            ps.setString(3, list);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void replaceList(String userID, String title, String list) {

        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE userList " +
                        "SET list = ?" +
                        "WHERE (userID = ? and title = ?);")) {
            ps.setString(1, list);
            ps.setString(2, userID);
            ps.setString(3, title);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delList(String userID, String title) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM userList WHERE (userID = ? and title = ?);")) {
            ps.setString(1, userID);
            ps.setString(2, title);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
