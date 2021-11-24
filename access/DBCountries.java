package access;

import database.DBConnection;
import model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 This class is used to access the countries in the database
 */
public class DBCountries {
    /**
     method to get all countries from database
     */
    //method to get all countries from database
    public static ObservableList<Countries> getAllCountries(){
        //array list of all countries
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try{
            //sql statement to get all countries
            String sql = "SELECT * from countries";
            //make a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //get results from MySQL
            ResultSet rs = ps.executeQuery();
            //adds countries from database into observable list
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }


}
