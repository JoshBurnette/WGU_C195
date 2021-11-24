package controller;

import access.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
 This class is used to control the Total Customers view.
 */
public class TotalCustomersController implements Initializable {
    public Button backButton;
    public Label custTotalLabel;
    Stage stage;
    Parent scene;
    public Label customerTotalLabel;
    int totalCustomers = 0;

    /**
     gets the total number of customers in database.
     */

    //gets total number of customers
    public int getTotalCustomers(){
        totalCustomers = DBCustomer.getAllCustomers().size();
        return totalCustomers;
    }

    /**
     Takes you back to the generate reports view.
     */
    //takes user back to generate reports main screen
    public void onActionBack(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/GenerateReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     Initializes the page and sets the label text.
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
                custTotalLabel.setText(rb.getString("Total#OfCustomers"));
                backButton.setText(rb.getString("Back"));



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //runs the method to add up the total number of customers
        getTotalCustomers();
        //sets the text with that number
        customerTotalLabel.setText(String.valueOf(totalCustomers));

    }
}
