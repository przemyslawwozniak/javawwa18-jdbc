package pl.sda.javawwa18;

import java.sql.*;
import java.time.LocalDate;
import java.util.TimeZone;

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
                System.err.println("Exception occured while inserting into rentsName of ");
            }
        }
        catch(SQLException ex) {
            System.err.println("Exception occured while preparing statement");
        }
    }

    public static void addMovie(final String title, final String genre, final LocalDate releaseDate, final String description, final int noOfCopies, final Connection connection) {
        final String parametrizedQuery = "INSERT INTO movies (title, genre, releaseDate, description) VALUES(?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(parametrizedQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setDate(3, Date.valueOf(releaseDate));
            preparedStatement.setString(4, description);

            try {
                preparedStatement.executeUpdate();
                //tutaj nowy rekord jest juz dodany do tabeli movies
                if(noOfCopies > 0) {
                    final int movieId = getMovieIdForTitle(title, connection);
                    if(movieId > 0) {
                        System.out.println("INSERT into movies successful with ID = " + movieId);
                        //create copies
                        addCopies(movieId, noOfCopies, connection);
                        System.out.println(String.format("Added %d copies of '%s'", noOfCopies, title));
                    }
                    else {
                        System.err.println("Could not find ID for given movie");
                    }
                }
            }
            catch(SQLException ex2) {
                System.err.println("Exception occured while inserting into movies");
            }
        }
        catch(SQLException ex) {
            System.err.println("Exception occured while preparing statement");
        }
    }

    public static int getMovieIdForTitle(final String title, final Connection connection) {
        final String parametrizedQuery = "SELECT movieId FROM movies WHERE title = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(parametrizedQuery)) {
            preparedStatement.setString(1, title);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    return resultSet.getInt("movieId"); //tutaj jest poprawne wyjscie z metody
                }
            }
            catch(SQLException ex2) {
                System.err.println("Exception occured while executing prepared statement");
            }
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
        //uproszczona obsluga bledow
        System.out.println(String.format("MoviesRentalDbAccessService#getMovieIdForTitle(%s) - something went wrong... :(", title));
        return -1;
    }

    private static void addCopies(final int movieId, final int noOfCopies, final Connection connection) {
        for(int i = 0; i < noOfCopies; i++) {
            addCopy(movieId, connection);
        }
    }

    private static void addCopy(final int movieId, final Connection connection) {
        final String parametrizedQuery = "INSERT INTO copies(movieId) VALUES (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(parametrizedQuery)) {
            preparedStatement.setInt(1, movieId);

            try {
                preparedStatement.executeUpdate();
                System.out.println("INSERT into copies successful for movieId = " + movieId);
            }
            catch(SQLException ex2) {
                System.err.println("Exception occured while executing prepared statement");
            }
        }
        catch(SQLException ex) {
            System.err.println("Cannot establish connection to DB: " + ex);
        }
    }

}
