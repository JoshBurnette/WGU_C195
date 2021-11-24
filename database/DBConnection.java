package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 This class is used to connect to the database.
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ0704i";

    //This makes up my jdbc url
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String userName = "U0704i"; //Username
    private static final String password = "53688926491"; //Password


    /**
     Starts the connection to the database.
     */
    //starts DB connection
    public static Connection startConnection(){
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection successful!");
        }
        catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException: ");
            //System.out.println(e.getMessage());
            e.printStackTrace();

        }
        catch (SQLException e){
            System.out.println("SQLException: ");
            //System.out.println(e.getMessage());
            e.printStackTrace();

        }
        return conn;
    }

    /**
     Closes the connection to the database.
     */
    //closes DB connection
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection closed!");
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     gets connection
     */
    public static Connection getConnection() {
        return conn;
    }

}
