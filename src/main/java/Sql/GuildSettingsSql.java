package Sql;

import Commands.General.GuildsSettings;
import Commands.General.GuildsSettingsImpl;

import java.sql.*;

public class GuildSettingsSql {

    private static Connection connection;

    public GuildSettingsSql() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(Private.sqlURL, Private.sqlUsername, Private.sqlPass);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    public static void getConn() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(Private.sqlURL, Private.sqlUsername, Private.sqlPass);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private static void createTable() {
        try(PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS guildsSettings" +
                "(" +
                " guildId BIGINT NOT NULL," +
                " guildName VARCHAR(256) NOT NULL," +
                " guildPrefix VARCHAR(256) NOT NULL," +
                " guildWelcomeMsg VARCHAR(256)," +
                " guildMembers BIGINT" +
                ");")) {
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(GuildsSettings gs) {
        try(PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO guildsSettings (guildId, guildName, guildPrefix, guildWelcomeMsg, guildMembers) " +
                        "VALUES (?,?,?,?,?);")) {
            setGuildsSettings(ps, gs.getGuildId(), gs.getGuildName(), gs.getGuildPrefix(), gs.getGuildWelcomeMsg(), gs.getGuildMembers());
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(long id) {
        try(PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM guildsSettings WHERE guildId = ?;")) {
            ps.setLong(1, id);
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static GuildsSettings get(long id) {
        GuildsSettings gs = new GuildsSettingsImpl();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT guildId, guildName, guildPrefix, guildWelcomeMsg, guildMembers FROM guildsSettings " +
                        "WHERE guildName = ?;")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                gs.setGuildId(rs.getLong("guildId"));
                gs.setGuildName(rs.getString("guildName"));
                gs.setGuildPrefix(rs.getString("guildPrefix"));
                gs.setGuildWelcomeMsg(rs.getString("guildWelcomeMsg"));
                gs.setGuildMembers(rs.getInt("guildMembers"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return gs;
    }

    private static void setGuildsSettings(PreparedStatement ps, long guildId, String name, String prefix, String welcomeMsg, int members) throws SQLException {
        ps.setLong(1, guildId);
        ps.setString(2, name);
        ps.setString(3, prefix);
        ps.setString(4, welcomeMsg);
        ps.setInt(5, members);
    }

}