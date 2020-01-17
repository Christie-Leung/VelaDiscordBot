package Commands.ComparingDateTime.Timezones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimezoneSql {

    private static Connection connection;

    public TimezoneSql() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "root");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    public static Connection getConn() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "root");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        createTable();
        return connection;
    }

    public static void createTable() {
        try(PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS timezones" +
                "(" +
                " user VARCHAR(256) NOT NULL," +
                " date TIMESTAMP NOT NULL," +
                " description VARCHAR(256) NOT NULL" +
                ");")) {
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(UserTimezone tz) {
        try(PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO schedules (user, date, description) " +
                        "VALUES (?,?,?);")) {
            setUserTimezone(ps, tz.getUser(), tz.getTimezone());
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String description) {
        try(PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM schedules WHERE description = ?;")) {
            ps.setString(1, description);
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserTimezone get(String description) {
        UserTimezone tz = new UserTimezoneImpl();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT user, date, description FROM schedules " +
                        "where description = ?;")) {
            ps.setString(1, description);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                tz.setUser(rs.getString("user"));
                tz.setTimezone(rs.getTimestamp("timezone"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return tz;
    }

    private static void setUserTimezone(PreparedStatement ps, String user, Timestamp timezone) throws SQLException {
        ps.setString(1, user);
        ps.setTimestamp(2, timezone);
    }

    public static List<String> getDate() {
        List<String> date = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT date FROM schedules; ")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                date.add(rs.getString("date"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static UserTimezone getFromDate(Timestamp localDateTime) {
        UserTimezone userTimezone = new UserTimezoneImpl();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT user, date, description FROM schedules " +
                        "where date = ?;")) {
            ps.setTimestamp(1, localDateTime);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                userTimezone.setUser(rs.getString("user"));
                userTimezone.setTimezone(rs.getTimestamp("timezone"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userTimezone;

    }

}