package controller;

import access.DBAppointment;
import access.DBContacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 This class is used to control the Schedule that can be filtered by contact ID.
 */
public class ContactScheduleController implements Initializable {
    public ComboBox contactCombobox;
    public TableView appointmentTableView;
    public TableColumn appointmentIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIdCol;
    public Label schedByConLabel;
    public Button generateButton;
    public Button BackBtn;
    public Label selectLabel;
    Stage stage;
    Parent scene;
    ObservableList<Contact> allContacts = DBContacts.getAllContacts();


    /**
     Gets contact IDs from the database
     */
    //Gets contact IDs from the database
    public ObservableList<Integer> getAllContactIDs(){
        ObservableList<Integer> allContactIDs = FXCollections.observableArrayList();
        for(int i = 0; i < allContacts.size(); i++)
        {
            int c;
            c = allContacts.get(i).getId();
            allContactIDs.add(c);
        }
        return allContactIDs;
    }

    /**
     Goes back to the main generate reports view
     */
    //Takes user back to Generate Reports Main screen
    public void onActionBack(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/GenerateReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     Filters the appointment list by the selected contact ID.
     */
    //Filters the appointment list by the selected contact ID.
    public void onActionGenerate(ActionEvent actionEvent) {
        int contactID = (int) contactCombobox.getValue();
        //puts all appts from db into table
        appointmentTableView.setItems(DBAppointment.getAllContactAppointments(contactID));
        //sets each column
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

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
                schedByConLabel.setText(rb.getString("ScheduleByContact"));
                schedByConLabel.setFont(Font.font(20));
                selectLabel.setText(rb.getString("SelectContactID"));
                selectLabel.setLayoutX(600.0);
                generateButton.setText(rb.getString("Generate"));
                BackBtn.setText(rb.getString("Back"));
                appointmentIdCol.setText(rb.getString("ApptID"));
                titleCol.setText(rb.getString("Title"));
                descriptionCol.setText(rb.getString("Description"));
                typeCol.setText(rb.getString("Type"));
                startCol.setText(rb.getString("StartDate&Time"));
                endCol.setText(rb.getString("EndDate&Time"));
                customerIdCol.setText(rb.getString("CustomerID"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //puts all contact IDs in contact ID combobox
        contactCombobox.getItems().addAll(getAllContactIDs());

    }


}
