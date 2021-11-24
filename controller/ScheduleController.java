package controller;

import access.DBAppointment;
import access.DBContacts;
import access.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import model.Appointment;
import model.Contact;
import controller.GenerateReportsMainController;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;


/**
 This class is used to control the main Schedule/Appointments view AND
 THIS CLASS CONTAINS THE LAMBDA CODE***
 */
public class ScheduleController implements Initializable {

    public TableView appointmentTableView;
    public TableColumn appointmentIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIdCol;
    public RadioButton monthlyRadioBtn;
    public RadioButton weeklyRadioBtn;
    public RadioButton allRadioBtn;
    public TextField titleTxt;
    public TextField locationTxt;
    public Label appointmentID;
    public TextField typeTxt;
    public TextField startDateAndTimeTxt;
    public TextField endDateAndTimeTxt;
    public TextField customerIDTxt;
    public TextField userIDTxt;
    public TextField descriptionTxt;
    public ComboBox contactComboBox;
    public Label messageLabel;
    public Label appointmentLabel;
    public Label appointmentDetailsLabel;
    public Button exitBtn;
    public Button viewCustomerBtn;
    public Button updateAppointmentBtn;
    public Button deleteAppointmentBtn;
    public Button generateReportsBtn;
    public Label apptDetIDLabel;
    public Label apptDetTitleLabel;
    public Label apptDetDescriptionLabel;
    public Label apptDetLocationLabel;
    public Label apptDetContactLabel;
    public Label apptDetTypeLabel;
    public Label apptDetStartLabel;
    public Label apptDetEndLabel;
    public Label apptDetCustIDLabel;
    public Label apptDetUserIDLabel;
    public Button saveAppointmentBtn;
    public Button clearDetailsBtn;
    int selectedContactId = 0;
    boolean updateAppointment = false;
    int selectedApptID;
    public ObservableList<Contact> allContacts = DBContacts.getAllContacts();
    Appointment apptToUpdate;
    ZonedDateTime loginTime = ZonedDateTime.now();
    ZonedDateTime apptLocalTime;
    Stage stage;
    Parent root;
    Parent scene;
    int activeUserID;
    String activeUsername;
    String activeUserPassword;
    boolean bizHours = true;
    public ToggleGroup view;

    int userID;
    String username;
    String password;

    ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

    /**
     converts a local time to UTC
     */
    //converts a local time to UTC
    public static Timestamp timeToUTC(Timestamp ts){
        return new Timestamp(ts.getTime() - Calendar.getInstance().getTimeZone().getOffset(ts.getTime()));
    }

    /**
     This is a "Pass the football" method for setting the active user.
     */
    //"pass the football" method for setting the active user
    public void setActiveUser(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     Either adds new appt or updates appt depending on the updateAppointment boolean.
     Also displays custom messages
     */
    //Either adds new appt or updates appt depending on the updateAppointment boolean
    public void onActionSaveAppointment(ActionEvent actionEvent) {
        //gets user input from text fields
        String appTitle = titleTxt.getText();
        String appDesc = descriptionTxt.getText();
        String appLocation = locationTxt.getText();
        String appType = (String) typeTxt.getText();
        int selectedContactId = getSelectedContactID();
        String appStartStr = startDateAndTimeTxt.getText();
        LocalDateTime appStartLDT = LocalDateTime.parse(appStartStr);
        Timestamp appStart = Timestamp.valueOf(appStartLDT);
        String appEndStr = endDateAndTimeTxt.getText();
        LocalDateTime appEndLDT = LocalDateTime.parse(appEndStr);
        Timestamp appEnd = Timestamp.valueOf(appEndLDT);
        int custID = Integer.parseInt(customerIDTxt.getText());
        int userID = Integer.parseInt(userIDTxt.getText());
        activeUsername = username;
        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/Nat");

        //Timer and Task are to clear a custom message
        Timer messageTimer = new java.util.Timer();
        TimerTask clearMessage = new TimerTask() {
            @Override
            public void run() {
                messageLabel.setVisible(false);
            }
        };

        //Checks for ovarlapping appts for customers
        //First get all appointments from DB and add them to a list
        ObservableList<Appointment> customerAppointments = DBAppointment.getAllCustomersAppointments(custID);
        for(int i = 0; i < customerAppointments.size(); i++){
            //the following if statement is looking for a few different scenarios that would result in an overlapping appt
            //if the user is updating an appt then it will ignore that specific appt in the DB when checking for overlaps
            if((!(String.valueOf((customerAppointments.get(i).getAppointmentID())).equalsIgnoreCase(appointmentID.getText())))
                    && (customerAppointments.get(i).getAppointmentStart().isBefore(appEndLDT))
                    && (customerAppointments.get(i).getAppointmentStart().isAfter(appStartLDT))
                    || (!(String.valueOf((customerAppointments.get(i).getAppointmentID())).equalsIgnoreCase(appointmentID.getText())))
                    && ((customerAppointments.get(i).getAppointmentEnd().isAfter(appStartLDT))
                    && (customerAppointments.get(i).getAppointmentEnd().isBefore(appEndLDT)))
                    || (!(String.valueOf((customerAppointments.get(i).getAppointmentID())).equalsIgnoreCase(appointmentID.getText())))
                    && ((customerAppointments.get(i).getAppointmentStart().isBefore(appStartLDT))
                    && (customerAppointments.get(i).getAppointmentEnd().isAfter(appEndLDT))
                    || (!(String.valueOf((customerAppointments.get(i).getAppointmentID())).equalsIgnoreCase(appointmentID.getText())))
                    && (customerAppointments.get(i).getAppointmentStart().isEqual(appStartLDT))
                    || (!(String.valueOf((customerAppointments.get(i).getAppointmentID())).equalsIgnoreCase(appointmentID.getText())))
                    && (customerAppointments.get(i).getAppointmentEnd().isEqual(appEndLDT)))){
                //display message
                messageLabel.setVisible(true);
                messageLabel.setText(rb.getString("CustomerID") + " " + custID + " " + rb.getString("AlreadyHasAnApptAtThatTime."));
                //clear message
                messageTimer.schedule(clearMessage, 5000);
                return;

            }

        }


        //checks to see if appt is during business hours (8:00am-10:00pm EST)
        //converts user entered start time to UTC
        Timestamp utcStart = timeToUTC(appStart);
        //gets hour of start time in UTC
        int utcStartHour = utcStart.getHours();
        //Same things for end time
        Timestamp utcEnd = timeToUTC(appEnd);
        int utcEndHour = utcEnd.getHours();
        //Same idea now with minutes for the end time... cause an appt could end right at 10p EST but not 10:01
        int utcEndMin = utcEnd.getMinutes();
        //EST is 4 hours earlier than UTC so biz hours are 12 - 2
        if( ((utcStartHour < 12) && utcStartHour > 1)|| ( (utcEndHour < 4) && (utcEndHour > 2) ) || ((utcEndHour == 2) && (utcEndMin > 0))){
            messageLabel.setVisible(true);
            messageLabel.setText(rb.getString("Sorry,BusinessHoursAre800-2200EST"));
            messageTimer.schedule(clearMessage, 5000);
            return;
        }


        //checks to see if user entered text in some of the text fields, if these are left completely empty, returns
        if(appTitle.isEmpty() && appType.isEmpty() && appDesc.isEmpty() && appLocation.isEmpty() && appStartStr.isEmpty() && appEndStr.isEmpty() && custID == 0){
            return;
        }
        //confirms that a contact ID was selected from combobox, if not, returns
        if(selectedContactId == 0){
            return;
        }
        else {
            if(updateAppointment == false) {
                //adds new appointment to database
                DBAppointment.addAppointment(appTitle, appDesc, appLocation, selectedContactId, appType, appStart, appEnd, custID, userID, activeUsername);
            }
            else if(updateAppointment == true){
                //updates existing appt in database
                selectedApptID = apptToUpdate.getAppointmentID();
                DBAppointment.updateAppointment(appTitle, appDesc, appLocation, selectedContactId, appType, appStart, appEnd, custID, userID, selectedApptID, activeUsername);
                updateAppointment = false;
            }
            //updates appointment table
            allRadioBtn.selectedProperty().set(true);
            appointmentTableView.setItems(DBAppointment.getAllAppointments());
            //clears text fields
            appointmentID.setText("");
            titleTxt.clear();
            descriptionTxt.clear();
            locationTxt.clear();
            contactComboBox.getItems().clear();
            contactComboBox.getItems().addAll(getAllContactNames());
            typeTxt.clear();
            startDateAndTimeTxt.clear();
            endDateAndTimeTxt.clear();
            customerIDTxt.clear();
            userIDTxt.clear();
        }

    }

    /**
     clears text fields
     */
    //clears text fields
    public void onActionClearDetails(ActionEvent actionEvent) {
        appointmentID.setText("");
        titleTxt.clear();
        descriptionTxt.clear();
        locationTxt.clear();
        contactComboBox.getItems().clear();
        contactComboBox.getItems().addAll(getAllContactNames());
        typeTxt.clear();
        startDateAndTimeTxt.clear();
        endDateAndTimeTxt.clear();
        customerIDTxt.clear();
        userIDTxt.clear();
        updateAppointment = false;
    }



    /**
     fills in text fields with information from selected Appt
     */
    //fills in text fields with information from selected Appt
    public void onActionUpdateAppointment(ActionEvent actionEvent) {
        //change boolean value so when save button is clicked, appt is updated instead of created.
        updateAppointment = true;
        // creates an appointment object to pull info from
        apptToUpdate = (Appointment) appointmentTableView.getSelectionModel().getSelectedItem();
        //fill text fields with info from selected appointment
        appointmentID.setText(String.valueOf(apptToUpdate.getAppointmentID()));
        titleTxt.setText(apptToUpdate.getAppointmentTitle());
        descriptionTxt.setText(apptToUpdate.getAppointmentDescription());
        locationTxt.setText(apptToUpdate.getAppointmentLocation());
        contactComboBox.setValue(getSelectedContactName());
        typeTxt.setText(apptToUpdate.getAppointmentType());
        startDateAndTimeTxt.setText(String.valueOf(apptToUpdate.getAppointmentStart()));
        endDateAndTimeTxt.setText(String.valueOf(apptToUpdate.getAppointmentEnd()));
        customerIDTxt.setText(String.valueOf(apptToUpdate.getCustomerID()));
        userIDTxt.setText(String.valueOf(apptToUpdate.getUserID()));

    }


    /**
     Deletes appointment from DB. Updates table. Displays message.
     */
    //Deletes appointment from DB. Updates table. Displays message.
    public void onActionDeleteAppointment(ActionEvent actionEvent) {
        int selectedAppointmentID;
        String selectedAppointmentType;
        boolean appointmentDeleted = false;
        //Timer and Task are to display a custom message confirming deletion
        Timer messageTimer = new java.util.Timer();
        TimerTask clearMessage = new TimerTask() {
            @Override
            public void run() {
                messageLabel.setVisible(false);
            }
        };
        //checks to see that the user selected an appt from table
        if (appointmentTableView.getSelectionModel().getSelectedItem() != null){
            //creates a new appt object with info from user selected appt
            Appointment selectedAppointment = (Appointment) appointmentTableView.getSelectionModel().getSelectedItem();
            //gets info needed for custom message
            selectedAppointmentID = selectedAppointment.getAppointmentID();
            selectedAppointmentType = selectedAppointment.getAppointmentType();
            //deletes appt from DB
            DBAppointment.deleteAppointment(selectedAppointment.getAppointmentID());
            //updates table
            appointmentTableView.setItems(DBAppointment.getAllAppointments());
            //displays custom message in interface
            messageLabel.setVisible(true);
            messageLabel.setText("Appointment has been deleted. ID: " + selectedAppointmentID + ", Type: " + selectedAppointmentType);
            appointmentDeleted = true;

        }
        //clears custom message after 5 secs (5000 ms)
        if(appointmentDeleted == true){
            messageTimer.schedule(clearMessage, 5000);
        }
    }

    /**
     takes you to the CustomerTable
     */
    //takes you to the CustomerTable
    public void onActionViewCustomerTable(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerTable.fxml"));

        root = loader.load();
        CustomerTableController customerTableController = loader.getController();
        //sets the active user in other controllers to be accessed later
        customerTableController.setActiveUser(userID, username, password);

        //switches views
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     exits program
     */
    //exits program
    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     Gets contact IDs from the database
     */
    //Gets contact IDs from the database
    public int getSelectedContactID(){
        int c = 0;
        for(int i = 0; i < allContacts.size(); i++)
        {
            if(contactComboBox.getValue() == allContacts.get(i).getName()){
                c = allContacts.get(i).getId();
            }
        }
        return c;
    }

    /**
     Gets contact names from the database
     */
    //Gets contact names from the database
    public ObservableList<String> getAllContactNames(){
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        for(int i = 0; i < allContacts.size(); i++)
        {
            String c;
            c = allContacts.get(i).getName();
            allContactNames.add(c);
        }
        return allContactNames;
    }

    /**
     Gets one contact name from the database
     */
    //Gets contact name from the database
    public String getSelectedContactName(){
        String c = null;
        for(int i = 0; i < allContacts.size(); i++)
        {
            if(apptToUpdate.getAppointmentContact() == allContacts.get(i).getId()){
                c = allContacts.get(i).getName();
            }
        }
        return c;
    }

    /**
     takes you the main hub screen to view reports
     */
    //takes user to main hub screen to view reports
    public void onActionViewGenReports(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/GenerateReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     Makes a list of all appointments in the current month and displays them in table
     */
    //Makes a list of all appointments in the current month and displays them in table
    public void onActionViewMonthly(ActionEvent actionEvent) {
        monthlyAppointments.clear();
        ZonedDateTime currentDate = ZonedDateTime.now();
       for(int i = 0; i < DBAppointment.getAllAppointments().size(); i++){
           if(currentDate.getYear() == DBAppointment.getAllAppointments().get(i).getAppointmentStart().getYear()
                   && currentDate.getMonth().equals(DBAppointment.getAllAppointments().get(i).getAppointmentStart().getMonth())){
               monthlyAppointments.add(DBAppointment.getAllAppointments().get(i));
           }
       }


       appointmentTableView.setItems(monthlyAppointments);


    }

    /**
     creates list of appointments for the current week and displays them in table
     */
    //creates list of appointments for the current week and displays them in table
    public void onActionViewWeekly(ActionEvent actionEvent) {
        weeklyAppointments.clear();
        ZonedDateTime currentDate = ZonedDateTime.now();
        for(int i = 0; i < DBAppointment.getAllAppointments().size(); i++){
            if(currentDate.getYear() == DBAppointment.getAllAppointments().get(i).getAppointmentStart().getYear()
                    //&& currentDate.getMonth().equals(DBAppointment.getAllAppointments().get(i).getAppointmentStart().getMonth())
                    && DBAppointment.getAllAppointments().get(i).getAppointmentStart().getDayOfYear() <= (currentDate.getDayOfYear() + 6)
                    && DBAppointment.getAllAppointments().get(i).getAppointmentStart().getDayOfYear() >= currentDate.getDayOfYear()){
                weeklyAppointments.add(DBAppointment.getAllAppointments().get(i));
            }
        }
        appointmentTableView.setItems(weeklyAppointments);
    }

    /**
     shows all appts in table
     */
    //shows all appts in table
    public void onActionViewAll(ActionEvent actionEvent) {
        appointmentTableView.setItems(DBAppointment.getAllAppointments());
    }

    /**
     Initializes the appt table and fills it.
     Also checks to see if there are any appts within 15 mins, if so, displays a message.
     ***USES LAMBDA TO DISPLAY MESSAGE IF USER HAS AN APPT IN 15 MINS OR LESS***
     ***USES LAMBDA TO DISPLAY MESSAGE IF USER HAS NO UPCOMING APPTS***
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //***CHANGE LANGUAGE***

        Locale frenchLocale = new Locale("fr", "CA");
        //Testing
        //Locale.setDefault(frenchLocale);

        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/Nat");

        try {
            if (Locale.getDefault().getLanguage() == frenchLocale.getLanguage()) {
                //set text here
                saveAppointmentBtn.setText(rb.getString("SaveAppointment"));
                saveAppointmentBtn.setLayoutX(930.0);
                clearDetailsBtn.setText(rb.getString("ClearDetails"));
                updateAppointmentBtn.setText(rb.getString("UpdateAppointment"));
                deleteAppointmentBtn.setText(rb.getString("DeleteAppointment"));
                deleteAppointmentBtn.setLayoutX(265.0);
                messageLabel.setLayoutX(450.0);
                appointmentLabel.setText(rb.getString("Appointments"));
                appointmentDetailsLabel.setText(rb.getString("AppointmentDetails"));
                allRadioBtn.setText(rb.getString("ViewAll"));
                monthlyRadioBtn.setText(rb.getString("MonthlyView"));
                weeklyRadioBtn.setText(rb.getString("WeeklyView"));
                apptDetIDLabel.setText(rb.getString("AppointmentID"));
                apptDetTitleLabel.setText(rb.getString("Title"));
                apptDetDescriptionLabel.setText(rb.getString("Description"));
                apptDetLocationLabel.setText(rb.getString("Location"));
                apptDetTypeLabel.setText(rb.getString("Type"));
                apptDetStartLabel.setText(rb.getString("StartDate&Time"));
                apptDetEndLabel.setText(rb.getString("EndDate&Time"));
                apptDetCustIDLabel.setText(rb.getString("CustomerID"));
                apptDetUserIDLabel.setText(rb.getString("UserID"));
                appointmentIdCol.setText(rb.getString("ApptID"));
                titleCol.setText(rb.getString("Title"));
                descriptionCol.setText(rb.getString("Description"));
                locationCol.setText(rb.getString("Location"));
                typeCol.setText(rb.getString("Type"));
                startCol.setText(rb.getString("StartDate&Time"));
                endCol.setText(rb.getString("EndDate&Time"));
                customerIdCol.setText(rb.getString("CustomerID"));

                viewCustomerBtn.setText(rb.getString("GoToCustomerTable"));
                generateReportsBtn.setText(rb.getString("GenerateReports"));
                generateReportsBtn.setLayoutX(215.0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Toggle Group for monthly or weekly view
        view = new ToggleGroup();
        //puts all contact IDs in contact ID combobox
        contactComboBox.getItems().addAll(getAllContactNames());
        //puts all appts from db into table
        appointmentTableView.setItems(DBAppointment.getAllAppointments());
        //sets each column
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        //userID not displayed in table

        //check to see if there are any appts within 15 mins of login, display custom message
        //Timer and Task are to display and hide custom message
        Timer messageTimer = new java.util.Timer();
        TimerTask clearMessage = new TimerTask() {
            @Override
            public void run() {
                messageLabel.setVisible(false);
            }
        };
        for (int i = 0; i < DBAppointment.getAllAppointments().size(); i++) {
            LocalDate apptLocalDate = DBAppointment.getAllAppointments().get(i).getAppointmentStart().toLocalDate();
            LocalDate loginLocalDate = LocalDate.now();
            if (apptLocalDate.equals(loginLocalDate)) {
                apptLocalTime = DBAppointment.getAllAppointments().get(i).getAppointmentStart().atZone(ZoneId.systemDefault());
                ZonedDateTime timeCheck = apptLocalTime.minusMinutes(15);
                if(loginTime.isAfter(timeCheck) && loginTime.isBefore(apptLocalTime)){
                    JBLambda apptMessage;
                    apptMessage = () -> messageLabel.setText(rb.getString("YouHaveAnAppointmentIn15MinutesOrLess."));
                    apptMessage.run();
                    break;
                }
            }
            else {
                JBLambda noApptMessage;
                noApptMessage = () -> messageLabel.setText(rb.getString("YouHaveNoUpcomingAppointments."));
                noApptMessage.run();
                 }

        }
        messageTimer.schedule(clearMessage, 10000);
    }


}

/**
 ***Interface for Lambda
 */
//interface for lambda
interface JBLambda{
    void run();
}
