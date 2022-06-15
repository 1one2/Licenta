package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    /*
    Statement statement = con.createStatement();
            String sql = "Create table if not exists testtable";
            statement.execute(sql);
            con.close();
     */
    private static Connection connection;

    public static void initConnection()
    {

        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
