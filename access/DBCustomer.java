package access;

import controller.MemberLoginController;
import database.DBConnection;
import model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.sql.Statement;

import java.sql.*;

/**
 This class is used to access the customers in the database
 */
public class DBCustomer {
    /**
     gets all customers from database
     */
    //gets all customers from database
    public static ObservableList<Customer> getAllCustomers(){

        ObservableList<Customer> clist = FXCollections.observableArrayList();

        try{
            //sql statement
            String sql = "SELECT * FROM WJ0704i.customers;";
            //create a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //execute query and get results
            ResultSet rs = ps.executeQuery();
            //work through table one row at a time
            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                String customerCreateDate = rs.getString("Create_Date");
                String customerCreatedBy = rs.getString("Created_By");
                Timestamp customerLastUpdate = Timestamp.valueOf(rs.getString("Last_Update"));
                String customerLastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                //Create new customer object
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhoneNumber, customerCreateDate, customerCreatedBy, customerLastUpdate, customerLastUpdatedBy, divisionId);
                //Add newly created customer to list
                clist.add(customer);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }

    /**
     Adds new customer to the database
     */
    //Adds new customer to the database
    // Saving this just in case (String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String customerCreateDate, String customerCreatedBy, Timestamp customerLastUpdate, String customerLastUpdatedBy, int divisionID)
    public static void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String activeUserString, int customerDivisionID)
        {
        try{
            //SQL statement
            String sqlAddCustomer = "INSERT INTO customers VALUES(NULL,?,?,?,?,NOW(),?,NOW(), ?, ?)";
            //Create a PreparedStatement
            PreparedStatement psac = DBConnection.getConnection().prepareStatement(sqlAddCustomer);
            //add question marks
            psac.setString(1, customerName);
            psac.setString(2, customerAddress);
            psac.setString(3, customerPostalCode);
            psac.setString(4, customerPhoneNumber);
            psac.setString(5, activeUserString);
            psac.setString(6, activeUserString);
            psac.setInt(7, customerDivisionID);

            psac.execute();


        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     Deletes customer from the database
     */
    //deletes customer from database
    public static void deleteCustomer(int customerID) {
        try {

            //SQL statement
            String sqldc = "DELETE from customers where Customer_ID = ?";

            //Create a PreparedStatement
            PreparedStatement psdc = DBConnection.getConnection().prepareStatement(sqldc);
            //add question mark
            psdc.setInt(1, customerID);

            psdc.execute();


        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     updates customer in the database
     */
    //updates customer in database
    public static void updateCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String activeUserString, int customerDivisionID) {
        try {

            //SQL statement
            String sqluc = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Last_Update = NOW(), Phone = ?, Last_Updated_By = ?, Division_ID = ? where Customer_ID = ?";

            //Create a PreparedStatement
            PreparedStatement psuc = DBConnection.getConnection().prepareStatement(sqluc);
            //add question mark
            psuc.setString(1, customerName);
            psuc.setString(2, customerAddress);
            psuc.setString(3, customerPostalCode);
            psuc.setString(4,customerPhoneNumber);
            psuc.setString(5,activeUserString);
            psuc.setInt(6, customerDivisionID);
            psuc.setInt(7, customerID);


            psuc.execute();


        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

}
