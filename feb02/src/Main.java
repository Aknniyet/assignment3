import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        try {

            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";


            Properties authorization = new Properties();
            authorization.put("user", "postgres");
            authorization.put("password", "1224");


            Connection connection = DriverManager.getConnection(url, authorization);

            String sql = "INSERT INTO orders (customer_name, customer_surname, email, name, price)" +
                    " VALUES ('John', 'Andersen', 'john0101@example.com', 'AirPods', 2106000)";
            String sql1 = "UPDATE orders SET email = 'akjdejkde@example.com' WHERE order_id = 4;";
            String sql2 = "DELETE FROM orders WHERE order_id = 2";

            Statement statement2 = connection.createStatement();
            statement2.executeUpdate(sql);
            statement2.executeUpdate(sql1);
            statement2.executeUpdate(sql2);


            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);


            ResultSet table = statement.executeQuery("SELECT * FROM orders");

            table.first();
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst();
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }

                System.out.println();
            }

            if (table != null) { table.close(); }
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}