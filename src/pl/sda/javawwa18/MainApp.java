package pl.sda.javawwa18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TimeZone;

public class MainApp {

    public static void main(String[] args) {
        final String urlWithTimeZone = args[0] + "?serverTimezone=" + TimeZone.getDefault().getID();

        try(Connection connection = DriverManager.getConnection(urlWithTimeZone, args[1], args[2])) {
            MoviesRentalDbAccessService.rentMovie(4, 12, connection);
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
    }

}
