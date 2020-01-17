package Commands.Utilities.MovieList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieListSql {


    private static Connection connection;

    public MovieListSql() {

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

    public static void createTable() {
        try(PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS movieList" +
                "(" +
                " position INT," +
                " movie VARCHAR(256) NOT NULL" +
                ");")) {
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static MovieList get(String movie) {
        MovieList movieList = new MovieListImpl();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT position, movie FROM movieList " +
                        "where movie = ?;")) {
            ps.setString(1, movie);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                movieList.setPosition(rs.getInt("position"));
                movieList.setMovie(rs.getString("movie"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public static void add(String movie) {
        int pos = MovieListSql.movieList().size();

        try(PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO movieList (position, movie) " +
                        "VALUES (?, ?);")) {
            ps.setInt(1, pos + 1);
            ps.setString(2, movie);
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String movie) {
        try(PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM movieList WHERE movie = ?;")) {
            ps.setString(1, movie);
            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<MovieList> movieList() {
        List<MovieList> scheduledEvents = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(
                "SELECT position, movie FROM movieList")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                MovieList ml = new MovieListImpl();
                ml.setPosition(rs.getInt("position"));
                ml.setMovie(rs.getString("movie"));
                scheduledEvents.add(ml);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return scheduledEvents;
    }
}
