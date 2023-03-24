package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.AppointmentsDAOImpl;
import wgu.software2_c195.DAO.Query;
import wgu.software2_c195.DAO.UserDAOlmpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wgu.software2_c195.User;
import wgu.software2_c195.Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**This class controls the data of the control view*/
public class LogInController implements Initializable {
    public TextField UsernameField;
    public Button LoginBut;
    public Label TimezoneLabel;
    public javafx.scene.control.PasswordField PasswordField;
    public ComboBox LanguageBox;
    public Label Username;
    public Label Password;
    public Label Welcome;
    UserDAOlmpl userDAOlmpl = new UserDAOlmpl();
    Utility util = new Utility();
    ObservableList<String> languages = FXCollections.observableArrayList("English", "Français");
    AppointmentsDAOImpl appointmentsDAO = new AppointmentsDAOImpl();
     String incorrect = "Incorrect Password";
     String notFound = "User not Found";

    public LogInController() throws SQLException, ParseException{

    }
    /** This method verifies the login credentials.
      This method verifies the login, writes to the login_activity file if the attempt was successful or not and displays and error pop-up uf not successful.
     @param actionEvent click of login button.*/
    public void LoginButAction(ActionEvent actionEvent) throws Exception, IOException {

        String userName =  UsernameField.getText();
        String passWord = PasswordField.getText();
        User loginUser = userDAOlmpl.getUser(userName);
        FileWriter loginAttempts = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(loginAttempts);

        if (loginUser == null) {

            loginAttempts.write(System.lineSeparator() + "Login Unsuccessful: Username: Not Found | Time: " + Instant.now().toString() + " UTC");

            outputFile.close();

            Utility.errorDisplay(notFound);

            return;
        }
        String correctPassword = loginUser.getPassword();

        if(passWord.equals(correctPassword)) {

            util.screenSwitch(actionEvent, "AppointmentsForm.fxml" );

            appointmentsDAO.appointmentAlert();

            loginAttempts.write(System.lineSeparator() + "Login Successful: Username: " + userName + " | Time: " + Instant.now().toString() + " UTC");

            outputFile.close();

        } else {

            loginAttempts.write(System.lineSeparator() + "Login Unsuccessful: Username: " + userName + " | Time: " + Instant.now().toString() + " UTC");

            outputFile.close();

            Utility.errorDisplay(incorrect);

        }
    }
    /** This method initializes the login form with the needed information.
      This method will populate the language choice box with the languages: French or English and the method will translate the view to the users local language.
     @param url a pointer to a resource.
     @param resourceBundle contains locale specific objects.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LanguageBox.setItems(languages);

        if(Locale.getDefault().getLanguage().equals("fr")) {

           Locale locale = new Locale("fr");

            LanguageBox.setPromptText(util.languageSwitch(LanguageBox.getId(), locale));

            Username.setText(util.languageSwitch(Username.getId(), locale));
            Welcome.setText(util.languageSwitch(Welcome.getId(), locale));
            Password.setText(util.languageSwitch(Password.getId(), locale));
            LoginBut.setText(util.languageSwitch(LoginBut.getId(), locale));
            incorrect = util.languageSwitch(incorrect, locale);
            notFound = util.languageSwitch(notFound, locale);

        }

        TimezoneLabel.setText(String.valueOf(ZoneId.systemDefault()));
    }
/** This method changes the language in the login form.
  The method changes languages based on the selection in the combo box.
 @param actionEvent selection of language.*/
    public void LanguageBoxAct(ActionEvent actionEvent) {

        Locale locale = Locale.getDefault();

            if (LanguageBox.getSelectionModel().getSelectedItem().equals(languages.get(1))) {
                locale = new Locale("fr");
            } else if(LanguageBox.getSelectionModel().getSelectedItem().equals(languages.get(0))) {
                locale = new Locale("eng");
            }
                Username.setText(util.languageSwitch(Username.getId(), locale));
                Welcome.setText(util.languageSwitch(Welcome.getId(), locale));
                Password.setText(util.languageSwitch(Password.getId(), locale));
                LoginBut.setText(util.languageSwitch(LoginBut.getId(), locale));
                incorrect = util.languageSwitch(incorrect, locale);
                notFound = util.languageSwitch(notFound, locale);
        }

}


