package access;

import database.DBConnection;
import model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 This class is used to access the Divisions in the database
 */
public class DBDivisions {
    /**
     Gets US divisions from database
     */
    //Gets US divisions from database
    public static ObservableList<FirstLevelDivisions> getUSDivisions(){
        //array list of all US divisions
        ObservableList<FirstLevelDivisions> dlistUS = FXCollections.observableArrayList();

        try{
            //sql statement to get all divisions
            String sql = "SELECT Division_ID, Division FROM WJ0704i.first_level_divisions WHERE Country_ID = 1";
            //make a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //get results from MySQL
            ResultSet rs = ps.executeQuery();
            //adds divisions from database into observable list
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                FirstLevelDivisions D = new FirstLevelDivisions(divisionId, divisionName);
                dlistUS.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return dlistUS;

    }

    /**
     Gets UK divisions from database
     */
    //Gets UK divisions from database
    public static ObservableList<FirstLevelDivisions> getUKDivisions(){
        //array list of all UK divisions
        ObservableList<FirstLevelDivisions> dlistUK = FXCollections.observableArrayList();

        try{
            //sql statement to get all divisions
            String sql = "SELECT Division_ID, Division FROM WJ0704i.first_level_divisions WHERE Country_ID = 2";
            //make a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //get results from MySQL
            ResultSet rs = ps.executeQuery();
            //adds divisions from database into observable list
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                FirstLevelDivisions D = new FirstLevelDivisions(divisionId, divisionName);
                dlistUK.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return dlistUK;

    }

    /**
     Gets Canada divisions from database
     */
    //Gets Canada Divisions from database
    public static ObservableList<FirstLevelDivisions> getCanadaDivisions(){
        //array list of all Canada divisions
        ObservableList<FirstLevelDivisions> dlistCanada = FXCollections.observableArrayList();

        try{
            //sql statement to get all divisions
            String sql = "SELECT Division_ID, Division FROM WJ0704i.first_level_divisions WHERE Country_ID = 3";
            //make a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //get results from MySQL
            ResultSet rs = ps.executeQuery();
            //adds divisions from database into observable list
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                FirstLevelDivisions D = new FirstLevelDivisions(divisionId, divisionName);
                dlistCanada.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return dlistCanada;

    }

    /**
     Gets ALL divisions from database
     */
    //Gets ALL divisions from database
    public static ObservableList<FirstLevelDivisions> getAllDivisions(){
        //array list of all divisions
        ObservableList<FirstLevelDivisions> dlist = FXCollections.observableArrayList();

        try{
            //sql statement to get all divisions
            String sql = "SELECT Division_ID, Division FROM WJ0704i.first_level_divisions";
            //make a PreparedStatement
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //get results from MySQL
            ResultSet rs = ps.executeQuery();
            //adds divisions from database into observable list
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                FirstLevelDivisions D = new FirstLevelDivisions(divisionId, divisionName);
                dlist.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return dlist;

    }

}
