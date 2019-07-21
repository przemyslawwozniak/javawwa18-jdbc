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
            //MoviesRentalDbAccessService.addMovie("Smierc w Wenecji", "Drama", LocalDate.of(2018, 5, 5), "Nowy zajebisty film :)", 10, connection);
            CallableQueryDemo.printRentsForCustomer(4, connection);
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
    }

}
