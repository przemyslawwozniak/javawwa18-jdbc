package pl.sda.javawwa18;

import java.sql.*;
import java.time.LocalDate;

public class MoviesRentalDbAccessService {

    public static void rentMovie(final int customerId, final int copyId, final Connection connection) {
        final String parametrizedQuery = "INSERT INTO rents (customerId, copyId, rentPricePerDay, borrowedDate) VALUES(?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(parametrizedQuery)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, copyId);
            preparedStatement.setDouble(3, 7.5);
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

            try {
                preparedStatement.executeUpdate();
                System.out.println("INSERT into rents successful");
            }
            catch(SQLException ex2) {
                System.err.println("Exception occured while inserting into rents");
            }
        }
        catch(SQLException ex) {
            System.err.println("Exception occured while preparing statement");
        }
    }

}
