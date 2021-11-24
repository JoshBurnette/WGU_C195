package controller;

import access.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.*;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 This class is used to control the Customer Table View.
 */
public class CustomerTableController implements Initializable {


    public TableView customerTableView;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCodeCol;
    public TableColumn customerPhoneNumberCol;
    public TableColumn customerDivisionIdCol;
    public Label customerIdLabel;
    public TextField customerNameTxt;
    public TextField customerAddressTxt;
    public TextField customerPostalCodeTxt;
    public TextField customerPhoneNumberTxt;
    public ObservableList<Countries> allCountries = DBCountries.getAllCountries();
    public ObservableList<FirstLevelDivisions> USDivisions = DBDivisions.getUSDivisions();
    public ObservableList<FirstLevelDivisions> UKDivisions = DBDivisions.getUKDivisions();
    public ObservableList<FirstLevelDivisions> CanadaDivisions = DBDivisions.getCanadaDivisions();
    public ObservableList<Appointment> allAppts = DBAppointment.getAllAppointments();
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Label messageLabel;
    public Label customerDetailsLabel;
    public Label customersLabel;
    public Button deleteCustomerBtn;
    public Button viewAppointmentsBtn;
    public Button saveCustomerBtn;
    public Button clearDetailsBtn;
    public Button updateCustomerBtn;
    public Label customerIDlabel;
    public Label nameLabel;
    public Label addressLabel;
    public Label postalCodeLabel;
    public Label phoneNumberLabel;
    public Label countryLabel;
    public Label divisionLabel;
    boolean updateCustomer = false;
    Customer customerToUpdate;
    int customerToUpdateID;
    int selectedDivisionID = 0;
    Stage stage;
    Parent scene;
    String activeUsername;
    int userID;
    String username;
    String password;

    /**
     This is a "passing of the football" setter that sets the active user.
     */

    //"passing of the football" setter that sets the active user
    public void setActiveUser(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     This button takes you to the schedule TableView (ScheduleController and Schedule.fxml)
     */
    //This button takes you to the schedule TableView (ScheduleController and Schedule.fxml)
    public void onActionViewSchedule(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Schedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     Uses text fields to add a new customer to database or update customer in database
     */
    //Uses text fields to add a new customer to database or update customer in database
    public void onActionSaveCustomer(ActionEvent actionEvent) {
        //gets user input from text fields
        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String customerPostalCode = customerPostalCodeTxt.getText();
        String customerPhoneNumber = customerPhoneNumberTxt.getText();
        int customerDivisionID = selectedDivisionID;
        activeUsername = username;
        //checks to see if user entered text in at least one of the text fields, if left completely empty, returns
        if(customerName.isEmpty() && customerAddress.isEmpty() && customerPostalCode.isEmpty() && customerPhoneNumber.isEmpty()){
            return;
        }
        //checks to see if user selected a division ID from the combobox, if not, returns
        if(selectedDivisionID == 0){
            return;
        }
        else {
            if(updateCustomer == false) {
                //adds new customer to database
                DBCustomer.addCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber, activeUsername, customerDivisionID);
            }
            else if(updateCustomer == true){
                //updates existing customer in database
                DBCustomer.updateCustomer(customerToUpdateID, customerName, customerAddress, customerPostalCode, customerPhoneNumber, activeUsername, customerDivisionID);
                updateCustomer = false;
            }
            //updates customer table
            customerTableView.setItems(DBCustomer.getAllCustomers());
            //clears text fields
            customerIdLabel.setText("");
            customerNameTxt.clear();
            customerAddressTxt.clear();
            customerPostalCodeTxt.clear();
            customerPhoneNumberTxt.clear();
            countryComboBox.getItems().clear();
            countryComboBox.getItems().addAll(getAllCountryNames());
            divisionComboBox.getItems().clear();
            selectedDivisionID = 0;
        }
    }


    /**
     Deletes a selected customer from database and updates the tableview
     */
    //Deletes selected customer from the database, updates the tableview and displays message in interface that confirms deletion
    public void onActionDelete(ActionEvent actionEvent) throws InterruptedException {
        String selectedCustomerName;
        boolean customerDeleted = false;
        //timer and task to clear the custom delete message
        Timer messageTimer = new java.util.Timer();
        TimerTask clearMessage = new TimerTask() {
            @Override
            public void run() {
                messageLabel.setVisible(false);
            }
        };
        //checks to see that a customer has been selected from the table
        if (customerTableView.getSelectionModel().getSelectedItem() != null){
            //set selected customer to new customer object
            Customer selectedCustomer = (Customer) customerTableView.getSelectionModel().getSelectedItem();
            selectedCustomerName = selectedCustomer.getCustomerName();
            //See if selected customer has any appts, if so, delete those first
            for(int i = 0; i < allAppts.size(); i++){
                if(allAppts.get(i).getCustomerID() == selectedCustomer.getCustomerID()){
                    DBAppointment.deleteAppointment(allAppts.get(i).getAppointmentID());
                }
            }
            //deletes customer from database
            DBCustomer.deleteCustomer(selectedCustomer.getCustomerID());
            customerTableView.setItems(DBCustomer.getAllCustomers());
            //displays message on interface to confirm deletion
            messageLabel.setVisible(true);
            messageLabel.setText(selectedCustomerName + " has been deleted.");
            customerDeleted = true;

        }
        if(customerDeleted == true){
            //clears the custom delete message after 5 secs (5000 ms)
            messageTimer.schedule(clearMessage, 5000);
        }


    }

    /**
     fills in all the textfields with the info from the selected customer
     */
    //fills in all the textfields with the info from the selected customer
    public void onActionUpdateCustomer(ActionEvent actionEvent) throws IOException {
        //creates an empty list of FirstLevelDivisions to be used later
        ObservableList<FirstLevelDivisions> divisionsForUpdate = FXCollections.observableArrayList();
        updateCustomer = true;
        customerToUpdate = (Customer) customerTableView.getSelectionModel().getSelectedItem();
        //gets country IDs and Division IDs and returns the corresponding country names and division names to fill in comboboxes
        String countryIdString = null;
        int divisionID = customerToUpdate.getDivisionID();
        String divisionIdString = null;
        if(divisionID <= 54){
            //country is US and division is a state in US
            divisionsForUpdate = DBDivisions.getUSDivisions();
            for(int i = 0; i < divisionsForUpdate.size(); i++){
                if(divisionsForUpdate.get(i).getDivisionID() == divisionID){
                    divisionIdString = divisionsForUpdate.get(i).getDivisionName();
                }
            }

            countryIdString = "U.S";
        }
        else if(divisionID > 55 && divisionID < 99){
            //country is Canada and divisions are provinces
            countryIdString = "Canada";
            divisionsForUpdate = DBDivisions.getCanadaDivisions();
            for(int i = 0; i < divisionsForUpdate.size(); i++) {
                if (divisionsForUpdate.get(i).getDivisionID() == divisionID) {
                    divisionIdString = divisionsForUpdate.get(i).getDivisionName();

                }
            }

        }
        else {
            //or lastly the country is the UK and the divisions are whatever they have in the UK
            countryIdString = "UK";
            divisionsForUpdate = DBDivisions.getUKDivisions();
            for(int i = 0; i < divisionsForUpdate.size(); i++) {
                if (divisionsForUpdate.get(i).getDivisionID() == divisionID) {
                    divisionIdString = divisionsForUpdate.get(i).getDivisionName();

                }
            }

        }
        //actually fills in the textfields with the information
        customerToUpdateID = customerToUpdate.getCustomerID();
        customerIdLabel.setText(String.valueOf(customerToUpdateID));
        customerNameTxt.setText(customerToUpdate.getCustomerName());
        customerAddressTxt.setText(customerToUpdate.getCustomerAddress());
        customerPostalCodeTxt.setText(customerToUpdate.getCustomerPostalCode());
        customerPhoneNumberTxt.setText(customerToUpdate.getCustomerPhoneNumber());
        countryComboBox.setValue(countryIdString);
        divisionComboBox.setValue(divisionIdString);

    }

    /**
     clears all the textfields on the form
     */
    //clears all the textfields on the form
    public void onActionClearDetails(ActionEvent actionEvent) {
        customerIdLabel.setText("");
        customerNameTxt.clear();
        customerAddressTxt.clear();
        customerPostalCodeTxt.clear();
        customerPhoneNumberTxt.clear();
        updateCustomer = false;
        countryComboBox.getItems().clear();
        countryComboBox.getItems().addAll(getAllCountryNames());
        divisionComboBox.getItems().clear();


    }

    /**
     Populates Division ComboBox based on user selection of Country ComboBox
     */
    //Populates Division ComboBox based on user selection of Country ComboBox
    public void onActionComboBox(ActionEvent actionEvent){

        if(countryComboBox.getSelectionModel().getSelectedItem() != null) {
            Object selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            String c = selectedCountry.toString();
            if (c.equalsIgnoreCase("U.S")) {
                divisionComboBox.setItems(getUSDivisionNames());
            } else if (c.equalsIgnoreCase("UK")) {
                divisionComboBox.setItems(getUKDivisionNames());
            } else if (c.equalsIgnoreCase("Canada")) {
                divisionComboBox.setItems(getCanadaDivisionNames());
            }

        }
    }
    /**
     Gets country names from the database
     */
    //Gets country names from the database
    public ObservableList<String> getAllCountryNames(){
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();
        for(int i = 0; i < allCountries.size(); i++)
        {
            String c;
            c = allCountries.get(i).getName();
            allCountryNames.add(c);
        }
        return allCountryNames;
    }


    /**
     Gets US Division names from the database
     */
    //Gets US Division names from the database
    public ObservableList<String> getUSDivisionNames(){
        ObservableList<String> USDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < USDivisions.size(); i++)
        {
            String d;
            d = USDivisions.get(i).getDivisionName();
            USDivisionNames.add(d);
        }
        return USDivisionNames;
    }

    /**
     Gets UK Division names from the database
     */
    //Gets UK Division names from the database
    public ObservableList<String> getUKDivisionNames(){
        ObservableList<String> UKDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < UKDivisions.size(); i++)
        {
            String d;
            d = UKDivisions.get(i).getDivisionName();
            UKDivisionNames.add(d);
        }
        return UKDivisionNames;
    }

    /**
     Gets Canada Division names from the database
     */
    //Gets Canada Division names from the database
    public ObservableList<String> getCanadaDivisionNames(){
        ObservableList<String> CanadaDivisionNames = FXCollections.observableArrayList();
        for(int i = 0; i < CanadaDivisions.size(); i++)
        {
            String d;
            d = CanadaDivisions.get(i).getDivisionName();
            CanadaDivisionNames.add(d);
        }
        return CanadaDivisionNames;
    }

    /**
     gets user selected division ID from the combobox
     */
    //gets user selected division ID from the combobox
    public int onActionDivComboBox(ActionEvent actionEvent){
        if(divisionComboBox.getSelectionModel().getSelectedItem() != null) {
            Object selectedDivision = divisionComboBox.getSelectionModel().getSelectedItem();

            String d = selectedDivision.toString();
            for (int i = 0; i < DBDivisions.getAllDivisions().size(); i++) {
                if (d.equalsIgnoreCase(DBDivisions.getAllDivisions().get(i).getDivisionName())) {
                    selectedDivisionID = DBDivisions.getAllDivisions().get(i).getDivisionID();
                    break;
                }

            }
        }
        return selectedDivisionID;
    }



    /**
     This populates the Customer TableView with all of the current customers
     */
    //This populates the Customer TableView with all of the current customers
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //***CHANGE LANGUAGE***

        Locale frenchLocale = new Locale("fr", "CA");
        //Testing
        //Locale.setDefault(frenchLocale);

        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/Nat");

        try {
            if (Locale.getDefault().getLanguage() == frenchLocale.getLanguage()) {
                //set text here
                saveCustomerBtn.setText(rb.getString("SaveCustomer"));
                saveCustomerBtn.setLayoutX(950.0);
                clearDetailsBtn.setText(rb.getString("ClearDetails"));
                clearDetailsBtn.setLayoutX(1080.0);
                updateCustomerBtn.setText(rb.getString("UpdateCustomer"));
                deleteCustomerBtn.setText(rb.getString("DeleteCustomer"));
                deleteCustomerBtn.setLayoutX(175.0);
                messageLabel.setLayoutX(400.0);
                customersLabel.setText(rb.getString("Customers"));
                customerDetailsLabel.setText(rb.getString("CustomerDetails"));
                viewAppointmentsBtn.setText(rb.getString("GoToAppointmentTable"));
                customerIDlabel.setText(rb.getString("CustomerID"));
                nameLabel.setText(rb.getString("Name"));
                addressLabel.setText(rb.getString("Address"));
                postalCodeLabel.setText(rb.getString("PostalCode"));
                phoneNumberLabel.setText(rb.getString("PhoneNumber"));
                countryLabel.setText(rb.getString("Country"));
                divisionLabel.setText(rb.getString("Division"));
                customerIdCol.setText(rb.getString("CustomerID"));
                customerNameCol.setText(rb.getString("Name"));
                customerAddressCol.setText(rb.getString("Address"));
                customerPostalCodeCol.setText(rb.getString("PostalCode"));
                customerPhoneNumberCol.setText(rb.getString("PhoneNumber"));
                customerDivisionIdCol.setText(rb.getString("DivisionID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //populates the country name combobox with all country names from database
        countryComboBox.getItems().addAll(getAllCountryNames());
        //get customers in table
        customerTableView.setItems(DBCustomer.getAllCustomers());
        //set columns
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        //hides custom message label
        messageLabel.setVisible(false);
    }
}
