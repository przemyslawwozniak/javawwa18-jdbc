package pl.sda.javawwa18;

import java.sql.*;
import java.util.TimeZone;

public class MyFirstJdbcConnection {

    public static void main(String[] args) {
        final String urlWithTimeZone = args[0] + "?serverTimezone=" + TimeZone.getDefault().getID();
        final String query = "SELECT title, releaseDate FROM movies";
        //try-with-resources - 3 rozne (zalezne) zasoby
        try(Connection connection = DriverManager.getConnection(urlWithTimeZone, args[1], args[2]);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Connection to DB successfuly established :)");
            System.out.println("- - - - - - - - - - - - - - - -");
            System.out.println("PLEASE FIND AVAILABLE MOVIES IN OUR COLLECTION BELOW:");
            while(resultSet.next()) {
                System.out.println(String.format("%s [%s]\n",
                        resultSet.getString("title"),
                        resultSet.getDate("releaseDate")));
            }
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
    }

}
