package controller;

import access.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Parent;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 This class is used to control the Total Appts view.
 */
public class TotalApptsController implements Initializable {
    public Label PSLabel;
    public Label DBLabel;
    public Label JanLabel;
    public Label FebLabel;
    public Label MarchLabel;
    public Label AprilLabel;
    public Label MayLabel;
    public Label JuneLabel;
    public Label JulyLabel;
    public Label AugLabel;
    public Label SeptLabel;
    public Label OctLabel;
    public Label NovLabel;
    public Label DecLabel;
    public Label apptTotalsLabel;
    public Label planL;
    public Label debriefL;
    public Label janL;
    public Label febL;
    public Label marL;
    public Label decL;
    public Label novL;
    public Label octL;
    public Label septL;
    public Label augL;
    public Label julyL;
    public Label aprilL;
    public Label mayL;
    public Label juneL;
    public Button backButton;
    Stage stage;
    Parent scene;

    int pTotal = 0;
    int oTotal = 0;
    int janTotal = 0;
    int febTotal = 0;
    int marchTotal = 0;
    int aprilTotal = 0;
    int mayTotal = 0;
    int juneTotal = 0;
    int julyTotal = 0;
    int augTotal = 0;
    int septTotal = 0;
    int octTotal = 0;
    int novTotal = 0;
    int decTotal = 0;

    /**
     method to set appt totals by type.
     */
    public void setApptTotalByType(){
        ObservableList<Appointment> allAppts = DBAppointment.getAllAppointments();
        for(int i = 0; i < allAppts.size(); i++){
            if(allAppts.get(i).getAppointmentType().equalsIgnoreCase("Phone")){
                pTotal++;
            }
            if(allAppts.get(i).getAppointmentType().equalsIgnoreCase("Online")){
                oTotal++;
            }
        }
    }

    /**
     method to set appt total by month.
     */
    public void setApptTotalByMonth(){
        ObservableList<Appointment> allAppts = DBAppointment.getAllAppointments();
        for(int i = 0; i < allAppts.size(); i++){
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 1){
                janTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 2){
                febTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 3){
                marchTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 4){
                aprilTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 5){
                mayTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 6){
                juneTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 7){
                julyTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 8){
                augTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 9){
                septTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 10){
                octTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 11){
                novTotal++;
            }
            if(allAppts.get(i).getAppointmentStart().getMonthValue() == 12){
                decTotal++;
            }
        }

    }

    /**
     Takes you back to the generate reports view.
     */
    public void onActionBack(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/GenerateReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     Initializes the page and sets the label texts.
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
                apptTotalsLabel.setText(rb.getString("AppointmentTotals"));
                apptTotalsLabel.setFont(Font.font(28.0));
                planL.setText(rb.getString("Phone"));
                debriefL.setText(rb.getString("Online"));
                janL.setText(rb.getString("January"));
                febL.setText(rb.getString("February"));
                marL.setText(rb.getString("March"));
                aprilL.setText(rb.getString("April"));
                mayL.setText(rb.getString("May"));
                juneL.setText(rb.getString("June"));
                julyL.setText(rb.getString("July"));
                augL.setText(rb.getString("August"));
                septL.setText(rb.getString("September"));
                octL.setText(rb.getString("October"));
                novL.setText(rb.getString("November"));
                decL.setText(rb.getString("December"));
                backButton.setText(rb.getString("Back"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //adds up how many appts are in each month and each type
        setApptTotalByMonth();
        setApptTotalByType();
        //sets all the labels with the added totals
        PSLabel.setText(String.valueOf(pTotal));
        DBLabel.setText(String.valueOf(oTotal));
        JanLabel.setText(String.valueOf(janTotal));
        FebLabel.setText(String.valueOf(febTotal));
        MarchLabel.setText(String.valueOf(marchTotal));
        AprilLabel.setText(String.valueOf(aprilTotal));
        MayLabel.setText(String.valueOf(mayTotal));
        JuneLabel.setText(String.valueOf(juneTotal));
        JulyLabel.setText(String.valueOf(julyTotal));
        AugLabel.setText(String.valueOf(augTotal));
        SeptLabel.setText(String.valueOf(septTotal));
        OctLabel.setText(String.valueOf(octTotal));
        NovLabel.setText(String.valueOf(novTotal));
        DecLabel.setText(String.valueOf(decTotal));
    }
}
