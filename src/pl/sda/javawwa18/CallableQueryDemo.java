package pl.sda.javawwa18;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class CallableQueryDemo {
    /*
        Metoda demonstracyjna jak wywolywac procedury dla przykladu procedury:

        getRentsForCustomer
        IN custId
        OUT in_rent
        OUT returned
     */
    public static void printRentsForCustomer(final int customerId, final Connection connection) {
        final String callableQuery = "{call getRentsForCustomer(?, ?, ?)}";
        try(CallableStatement callableStatement = connection.prepareCall(callableQuery)) {
            callableStatement.setInt(1, customerId);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.registerOutParameter(3, Types.INTEGER);

            try {
                callableStatement.execute();

                final int inRent = callableStatement.getInt(2);
                final int returned = callableStatement.getInt(3);

                System.out.println(String.format("Customer with ID = %d has %d copies in rent and returned %d copies", customerId, inRent, returned));
            }
            catch(SQLException ex2) {
                System.err.println("Error while calling procedure getRentsForCustomer");
            }
        }
        catch(SQLException ex) {
            System.err.println("Could not call procedure getRentsForCustomer");
        }
    }

}
