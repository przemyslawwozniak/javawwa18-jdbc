package pl.sda.javawwa18;

import java.sql.*;
import java.time.LocalDate;
import java.util.TimeZone;

public class ParametrizedQueryDemo {

    public static void main(String[] args) {
        final String urlWithTimeZone = args[0] + "?serverTimezone=" + TimeZone.getDefault().getID();

        try(Connection connection = DriverManager.getConnection(urlWithTimeZone, args[1], args[2])) {
            printMoviesReleasedBetween(LocalDate.of(1999, 1, 1), LocalDate.of(2015, 12, 31), connection);
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
    }

    public static void printMoviesReleasedBetween(final LocalDate from, final LocalDate to, final Connection connection) {
        final String parametrizedQuery = "SELECT title, releaseDate FROM movies WHERE releaseDate between ? AND ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(parametrizedQuery)) {
            preparedStatement.setDate(1, Date.valueOf(from));
            preparedStatement.setDate(2, Date.valueOf(to));

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    System.out.println(String.format("%s [%s]\n",
                            resultSet.getString("title"),
                            resultSet.getDate("releaseDate")));
                }
            }
            catch(SQLException ex2) {
                System.err.println("Exception occured while executing prepared statement");
            }
        }
        catch(SQLException ex) {
            System.err.println("Exception occured while preparing statement");
        }
    }

}
