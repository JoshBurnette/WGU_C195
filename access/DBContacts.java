package access;

import database.DBConnection;
import model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.*;

/**
This class is used to access the contacts in the database
 */
public class DBContacts {
    /**
     method to get all contacts from database
     */
    //method to get all contacts from database
    public static ObservableList<Contact> getAllContacts() {
        //array list of all contacts
        ObservableList<Contact> clist = FXCollections.observableArrayList();

        try {
            //sql statement to get all contacts
            String sql = "SELECT * from contacts";
            //make a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //get results from MySQL
            ResultSet rs = ps.executeQuery();
            //adds contacts from database into observable list
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contact C = new Contact(contactId, contactName, contactEmail);
                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }
}
