package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 This class is used to control main hub of the generated reports view
 */
public class GenerateReportsMainController implements Initializable {

    public Label generateReportsLabel;
    public Button totalNumberButton;
    public Button scheduleForEachContactButton;
    public Button AdditionalReportButton;
    public Button viewScheduleButton;
    Stage stage;
    Parent scene;

    /**
     Takes you to the view total appts view
     */
    //takes user to the view total appts view
    public void onActionViewTotalNumber(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TotalAppts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     Takes you to the view where you can view schedule filtered by contact ID.
     */
    //takes user to view where you can view schedule filtered by contact ID.
    public void onActionViewScheduleForEachContact(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ContactSchedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This takes you to the additional report view. This additional report provides you with the total number of customers saved in the database.
     */
    //takes user to the additional report view. Additional report provides the total number of customers.
    public void onActionViewAdditionalReport(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TotalCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This is used to translate to french if needed.
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
                generateReportsLabel.setText(rb.getString("GenerateReports"));
                generateReportsLabel.setFont(Font.font(32.0));
                totalNumberButton.setText(rb.getString("TotalNumberOfCustomerAppts"));
                scheduleForEachContactButton.setText(rb.getString("ScheduleForEachContact"));
                AdditionalReportButton.setText(rb.getString("Total#OfCustomers"));
                viewScheduleButton.setText(rb.getString("BackToSchedule"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     This button takes you back to the main Appointments schedule view.
     */
    //takes user to the Appointments schedule view
    public void onActionViewSchedule(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Schedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
