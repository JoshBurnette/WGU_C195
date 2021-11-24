package controller;

//import com.sun.deploy.security.SelectableSecurityManager;
import access.DBAppointment;
import access.DBCustomer;
import access.DBLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import model.User;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 This class is used to control the Member Login view
 */
public class MemberLoginController  implements Initializable {
    public Button LoginButton;
    public TextField usernameTxt;
    public PasswordField passwordTxt;
    public Label wrongLoginLabel;
    public Label locationLabel;
    public Label memberLoginLabel;
    int logInAttempts = 1;
    Stage stage;
    Parent root;
    boolean success = false;
    ZoneId userZoneID = ZoneId.systemDefault();
    User activeUser;
    int activeUserID;
    String activeUsername;
    String activeUserPassword;
    private static File joshuaburnette;
    private static File Users;
    private static File IdeaProjects;
    private static File C195JB;
    private static File src;
    private static File login_activity;
    static File loginFile = new File("../C195JB/src/login_activity.txt");
    String zoneIdString = String.valueOf(userZoneID);



    /**
     This checks for valid login info entered by user and either logs them in to the system or provides an error message.
     It also appends to a list each and every login attempt.
     */
    //Login button either lets user log in or provides an error message
    public void onActionLogIn(ActionEvent actionEvent) throws IOException {
        //Grabs the date time everytime the login button is clicked.
        LocalDateTime loginAttemptDateTime = LocalDateTime.now();
        //convert to a string
        String loginAttemptDateTimeString = String.valueOf(loginAttemptDateTime);
        //gets all users from database
        ObservableList<User> userList = DBLogin.getAllUsers();
        //stores user typed text from username text field
        String typedUsername = usernameTxt.getText();
        //stores user typed text from password text field
        String typedPassword = passwordTxt.getText();
        //loops through the list of users in database
        for (int i = 0; i < userList.size(); i++) {
            //if a match is found in database for user entered username and password, loop breaks
            if (typedUsername.equals(userList.get(i).getUsername()) && typedPassword.equals(userList.get(i).getPassword())) {
                //allows a login
                success = true;
                //stores the current active user info
                activeUserID = userList.get(i).getUserID();
                activeUsername = userList.get(i).getUsername();
                activeUserPassword = userList.get(i).getPassword();

                break;
            }

        }
        //Writes to a txt file all the info of each login attempt
        writeToFileBufferedWriter("Log In Attempt: ");
        writeToFileBufferedWriter(loginAttemptDateTimeString);
        writeToFileBufferedWriter(" in ");
        writeToFileBufferedWriter(zoneIdString);
        writeToFileBufferedWriter(" by User: ");
        writeToFileBufferedWriter(typedUsername);
        writeToFileBufferedWriter(", with Password: ");
        writeToFileBufferedWriter(typedPassword);

        //match was found in database for user entered login info
        if (success == true) {
            System.out.println("Log In Successful");
            writeToFileBufferedWriter(". Log In Successful. \n");
            wrongLoginLabel.setText("");

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Schedule.fxml"));

            root = loader.load();
            ScheduleController scheduleController = loader.getController();
            //sets the active user in other controllers to be accessed later
            scheduleController.setActiveUser(activeUserID, activeUsername, activeUserPassword);

            //switches views
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } else if (success == false) { //no match found in database, login attempt fails
            System.out.println("Log In Failed");
            writeToFileBufferedWriter(". Log In Failed. \n");
            //displays a message to user attempting to login
            ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/Nat");
            wrongLoginLabel.setText(rb.getString("WrongUsernameOrPassword"));

        }
    }

    /**
     writes to a file using a string
     */
    //writes to a file using a string
    public static void writeToFileBufferedWriter(String msg) {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        try {
            fileWriter = new FileWriter(loginFile.getAbsoluteFile(), true); // true to append
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(msg);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     gets active user
     */
    //gets active user
    public User getActiveUser(){
        return activeUser;
    }


    /**
     Initializes Member Login view and translates to french if default locale is detected to be fr_CA
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //***CHANGE LANGUAGE***
        Locale frenchLocale = new Locale("fr", "CA");
        //TESTING
        //Locale.setDefault(frenchLocale);

        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/Nat");

        try {
            if (Locale.getDefault().getLanguage() == frenchLocale.getLanguage()) {
                System.out.println(rb.getString("hello") + " " + rb.getString("world"));
                memberLoginLabel.setText(rb.getString("MemberLogin"));
                LoginButton.setText(rb.getString("LogIn"));
                usernameTxt.setPromptText(rb.getString("Username"));
                passwordTxt.setPromptText(rb.getString("Password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        locationLabel.setText(String.valueOf(userZoneID));
    }
}
