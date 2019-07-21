package pl.sda.javawwa18;

import java.sql.*;
import java.util.TimeZone;

public class MyFirstJdbcConnection {

    public static void main(String[] args) {
        final String urlWithTimeZone = args[0] + "?serverTimezone=" + TimeZone.getDefault().getID();
        //final String query = "SELECT title, releaseDate FROM movies";
        final String query2 = "SELECT rentId, customerId, copyId, borrowedDate FROM rents WHERE status = 'Returned'";
        //try-with-resources - 3 rozne (zalezne) zasoby
        try(Connection connection = DriverManager.getConnection(urlWithTimeZone, args[1], args[2]);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query2)) {
            System.out.println("Connection to DB successfuly established :)");
            System.out.println("- - - - - - - - - - - - - - - -");
            //System.out.println("PLEASE FIND AVAILABLE MOVIES IN OUR COLLECTION BELOW:");
            while(resultSet.next()) {
                /*System.out.println(String.format("%s [%s]\n",
                        resultSet.getString("title"),
                        resultSet.getDate("releaseDate")));*/
                //if(!resultSet.getString("status").equals("In rent"))
                System.out.println(String.format("%d - %d - %d - %s\n",
                        resultSet.getInt("rentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getInt("copyId"),
                        resultSet.getDate("borrowedDate")));
            }
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
    }

}
