package Commands.ComparingDateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleSql {

    private static Connection connection;

    public ScheduleSql() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    public static Connection getConn() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        createTable();
        return connection;
    }

    private static void createTable() {
        try(PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS schedules" +
                "(" +
                " nameID BIGINT NOT NULL," +
                " user VARCHAR(256) NOT NULL," +
                " date TIMESTAMP NOT NULL," +
                " description VARCHAR(256) NOT NULL," +
                " serverID BIGINT" +
                ");")) {
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(ScheduledEvent se) {
        try(PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO schedules (nameID, user, date, description, serverID) " +
                        "VALUES (?,?,?,?,?);")) {
            setScheduledEvent(ps, se.getNameID(), se.getName(), se.getTimestamp(), se.getDescription(), se.getServerID());
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

    public static ScheduledEvent get(String description) {
        ScheduledEvent scheduledEvent = new ScheduledEventImpl();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT nameID, user, date, description, serverID FROM schedules " +
                        "WHERE description = ?;")) {
            ps.setString(1, description);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                scheduledEvent.setNameID(rs.getLong("nameID"));
                scheduledEvent.setName(rs.getString("user"));
                scheduledEvent.setTimestamp(rs.getTimestamp("date"));
                scheduledEvent.setDescription(rs.getString("description"));
                scheduledEvent.setServerID(rs.getLong("serverID"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return scheduledEvent;
    }


    public static List<ScheduledEvent> scheduledEvent() {
        List<ScheduledEvent> scheduledEvents = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT nameID, user, date, description, serverID FROM schedules")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ScheduledEvent se = new ScheduledEventImpl();
                se.setNameID(rs.getLong("nameID"));
                se.setName(rs.getString("user"));
                se.setTimestamp(rs.getTimestamp("date"));
                se.setDescription(rs.getString("description"));
                se.setServerID(rs.getLong("serverID"));
                scheduledEvents.add(se);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return scheduledEvents;
    }

    private static void setScheduledEvent(PreparedStatement ps, long nameID, String name, Timestamp localDateTime, String description, long serverID) throws SQLException {
        ps.setLong(1, nameID);
        ps.setString(2, name);
        ps.setTimestamp(3, localDateTime);
        ps.setString(4, description);
        ps.setLong(5, serverID);
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

    public static ScheduledEvent getFromDate(Timestamp localDateTime) {
        ScheduledEvent scheduledEvent = new ScheduledEventImpl();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT nameID, user, date, description, serverID FROM schedules " +
                        "where date = ?;")) {
            ps.setTimestamp(1, localDateTime);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                scheduledEvent.setNameID(rs.getLong("nameID"));
                scheduledEvent.setName(rs.getString("user"));
                scheduledEvent.setTimestamp(rs.getTimestamp("date"));
                scheduledEvent.setDescription(rs.getString("description"));
                scheduledEvent.setServerID(rs.getLong("serverID"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return scheduledEvent;

    }

}