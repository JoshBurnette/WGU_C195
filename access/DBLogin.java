package access;

import controller.MemberLoginController;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 This class is used to access the users in the database
 */
public class DBLogin {

    /**
     gets all users from database
     */
    //gets all users from database
    public static ObservableList<User> getAllUsers(){
        //adds users into an array
        ObservableList<User> ulist = FXCollections.observableArrayList();

        try {
            //SQL statement
            String sqlLogin = "SELECT User_ID, User_Name, Password FROM WJ0704i.users";

            //Create a PreparedStatement
            PreparedStatement psli = DBConnection.getConnection().prepareStatement(sqlLogin);
            //execute query and get results
            ResultSet rs = psli.executeQuery();
            //work through table one row at a time
            while(rs.next()){
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                //create new user object
                User user = new User(userID, username, password);
                ulist.add(user);
                }
            }

            catch (SQLException exception){
                exception.printStackTrace();
            }

        return ulist;
    }
}
