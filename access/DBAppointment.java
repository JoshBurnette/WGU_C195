package access;

import com.mysql.cj.x.protobuf.MysqlxResultset;
import controller.MemberLoginController;
import database.DBConnection;
import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

/**
 This class is used to access the appointments in the Database.
 */
public class DBAppointment {


    /**
     gets all appointments in the database from appointments table
     */
    //gets all appointments in the database from appointments table
    public static ObservableList<Appointment> getAllAppointments(){
        //List for all appointments to be added
        ObservableList<Appointment> aList = FXCollections.observableArrayList();

        try{
            //SQL statement
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            //loops through all appts in DB and continues to add appointment objects to observable list.
            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentType = rs.getString("Type");
                Timestamp appointmentStartTS = rs.getTimestamp("Start");
                Timestamp appointmentEndTS = rs.getTimestamp("End");
                LocalDateTime appointmentStart = appointmentStartTS.toLocalDateTime();
                LocalDateTime appointmentEnd = appointmentEndTS.toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart, appointmentEnd, customerID, userID);
                //add appt
                aList.add(appointment);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return aList;
    }


    /**
     adds new appt to database
     */
    //adds new appt to database
    public static void addAppointment(String appTitle, String appDesc, String appLocation, int appContact, String appType, Timestamp appStart, Timestamp appEnd, int custID, int userID, String username){
        try{
            //SQL statement
            String sqlAddAppt = "INSERT INTO appointments VALUES(NULL,?,?,?,?,?,?,NOW(),?,NOW(), ?, ?, ?, ?)";
            //Create a PreparedStatement
            PreparedStatement psaa = DBConnection.getConnection().prepareStatement(sqlAddAppt);
            //add question marks
            psaa.setString(1, appTitle);
            psaa.setString(2, appDesc);
            psaa.setString(3, appLocation);
            psaa.setString(4, appType);
            psaa.setTimestamp(5, appStart);
            psaa.setTimestamp(6, appEnd);
            psaa.setString(7, username);
            psaa.setString(8,username);
            psaa.setInt(9, custID);
            psaa.setInt(10, userID);
            psaa.setInt(11, appContact);

            psaa.execute();


        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     updates appt in database
     */
    //updates appt in database
    public static void updateAppointment(String appTitle, String appDesc, String appLocation, int appContact, String appType, Timestamp appStart, Timestamp appEnd, int custID, int userID, int appID, String username){
        try{
            //SQL statement
            String sqlua = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? where Appointment_ID = ?";

            //Create a PreparedStatement
            PreparedStatement psua = DBConnection.getConnection().prepareStatement(sqlua);
            //add question marks
            psua.setString(1, appTitle);
            psua.setString(2, appDesc);
            psua.setString(3, appLocation);
            psua.setString(4, appType);
            psua.setTimestamp(5, appStart);
            psua.setTimestamp(6, appEnd);
            psua.setString(7, username);
            psua.setInt(8, custID);
            psua.setInt(9, userID);
            psua.setInt(10, appContact);
            psua.setInt(11, appID);

            psua.execute();


        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     Delete appointment in database
     */
    //Delete appointment
    public static void deleteAppointment(int appointmentID) {
        try {

            //SQL statement
            String sqldc = "DELETE from appointments where Appointment_ID = ?";

            //Create a PreparedStatement
            PreparedStatement psdc = DBConnection.getConnection().prepareStatement(sqldc);
            //add question mark
            psdc.setInt(1, appointmentID);

            psdc.execute();


        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     gets all appointments in the database from appointments table that match a user entered customer ID
     */
    //gets all appointments in the database from appointments table that match a user entered customer ID
    public static ObservableList<Appointment> getAllCustomersAppointments(int enteredCustomerID){

        ObservableList<Appointment> caList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments where Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, enteredCustomerID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentType = rs.getString("Type");
                Timestamp appointmentStartTS = rs.getTimestamp("Start");
                Timestamp appointmentEndTS = rs.getTimestamp("End");
                LocalDateTime appointmentStart = appointmentStartTS.toLocalDateTime();
                LocalDateTime appointmentEnd = appointmentEndTS.toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart, appointmentEnd, customerID, userID);
                caList.add(appointment);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return caList;
    }

    /**
     gets all appointments in the database from appointments table that match a user entered contact ID
     */
    //gets all appointments in the database from appointments table that match a user entered contact ID
    public static ObservableList<Appointment> getAllContactAppointments(int enteredContactID){

        ObservableList<Appointment> caList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments where Contact_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, enteredContactID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                int appointmentContact = rs.getInt("Contact_ID");
                String appointmentType = rs.getString("Type");
                Timestamp appointmentStartTS = rs.getTimestamp("Start");
                Timestamp appointmentEndTS = rs.getTimestamp("End");
                LocalDateTime appointmentStart = appointmentStartTS.toLocalDateTime();
                LocalDateTime appointmentEnd = appointmentEndTS.toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart, appointmentEnd, customerID, userID);
                caList.add(appointment);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return caList;
    }


}
