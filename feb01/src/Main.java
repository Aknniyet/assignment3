import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        try {

            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";


            Properties authorization = new Properties();
            authorization.put("user", "postgres");
            authorization.put("password", "1224");


            Connection connection = DriverManager.getConnection(url, authorization);


            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);


            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose some operation:");
                System.out.println("1 - Create");
                System.out.println("2 - Read");
                System.out.println("3 - Update");
                System.out.println("4 - Delete");
                System.out.println("5 - Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createData(statement);
                        break;
                    case 2:
                        readData(statement);
                        break;
                    case 3:
                        updateData(statement);
                        break;
                    case 4:
                        deleteData(statement);
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (Exception e) {
            System.err.println("Error while accessing DataBase!");
            e.printStackTrace();
        }
    }

    private static void createData(Statement statement) throws SQLException {

        statement.executeUpdate("INSERT INTO orders (customer_name, customer_surname, email, name, price) VALUES ('Alpha', 'Beta', 'dhfjkbc@example.com', 'IPhone', 300000)");
        System.out.println("User created successfully!");
    }


    private static void readData(Statement statement) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
        displayResultSet(resultSet);
    }

    private static void updateData(Statement statement) throws SQLException {

        statement.executeUpdate("UPDATE orders SET price = 100000 WHERE name = 'AirPods'");
        System.out.println("User was updated successfully!");
    }

    private static void deleteData(Statement statement) throws SQLException {

        statement.executeUpdate("DELETE FROM orders WHERE name = 'AirPods'");
        System.out.println("User was deleted successfully!");
    }

    private static void displayResultSet(ResultSet resultSet) throws SQLException {
        resultSet.first();
        for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
            System.out.print(resultSet.getMetaData().getColumnName(j) + "\t\t");
        }
        System.out.println();

        resultSet.beforeFirst();
        while (resultSet.next()) {
            for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
                System.out.print(resultSet.getString(j) + "\t\t");
            }
            System.out.println();
        }
    }
}
