package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import wgu.software2_c195.Appointments;
import wgu.software2_c195.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
/**This class controls the data of the add appointments form.*/
public class AddAppointmentController implements Initializable {
    public Button saveBut;
    public Button cancelBut;
    public ComboBox UserIdBox;
    public TextField TitleField;
    public TextField LocationField;
    public DatePicker BeginDateField;
    public Spinner BeginHH;
    public Spinner BeginMM;
    public Spinner EndtimeMM;
    public Spinner EndTimeHH;
    public ComboBox CustomerBox;
    public AnchorPane UserIdField;
    public TextField CustomerIdField;
    public TextField AppointmentId;
    public ComboBox ContactIdBox;
    public ComboBox TypeBox;
    public DatePicker EndDateField;
    public TextArea DescriptionBox;
    CustomerDao customerDao = new CustomerDAOImpI();
    UserDAO userDAO = new UserDAOlmpl();
    ContactsDAO contactsDAO = new ContactsDAOImpl();
    AppointmentsDAO appointmentsDAO = new AppointmentsDAOImpl();
    Utility util = new Utility();
    public AddAppointmentController() throws SQLException, ParseException {
    }
    /** This method populates the choice boxes with appointment data.
      @param url a pointer to a resource.
     @param resourceBundle  contains locale specific objects. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentId.setText(String.valueOf(appointmentsDAO.generateAppId()));
        CustomerBox.setItems(customerDao.CustomersNames());
        UserIdBox.setItems(userDAO.getAllUsersId());
        ContactIdBox.setItems(contactsDAO.ContactIds());
        TypeBox.setItems(appointmentsDAO.Types());
        BeginHH.setValueFactory(Utility.setSpinnerHH(0));
        EndTimeHH.setValueFactory(Utility.setSpinnerHH(0));
        BeginMM.setValueFactory(Utility.setSpinnermm(0));
        EndtimeMM.setValueFactory(Utility.setSpinnermm(0));
    }
    /** This method save the inputted data and switches back to the appointments form.
      @param actionEvent click of button.
     @throws IOException throws exception in case of I/O exception.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public void saveBut(ActionEvent actionEvent) throws IOException, ParseException, SQLException {

        String customerName="";
        int userId =0;
        String type ="";
        int contactId=0;
        LocalDateTime begin= null;
        LocalDateTime end= null;
        int appointmentId = Integer.parseInt(AppointmentId.getText());

        if(CustomerBox.getSelectionModel().getSelectedItem() != null) {
            customerName = (String) CustomerBox.getSelectionModel().getSelectedItem();
        } else{Utility.errorDisplay("Please fill in required information"); return;}

        int customerId = Integer.parseInt(CustomerIdField.getText());

        if(UserIdBox.getSelectionModel().getSelectedItem() != null) {
            userId = (int) UserIdBox.getSelectionModel().getSelectedItem();
        }  else{Utility.errorDisplay("Please fill in required information"); return;}

        String title = TitleField.getText();

        if (TypeBox.getSelectionModel().getSelectedItem() != null) {
            type = (String) TypeBox.getSelectionModel().getSelectedItem();
        }  else{Utility.errorDisplay("Please fill in required information"); return;}

        if(ContactIdBox.getSelectionModel().getSelectedItem() != null) {
            contactId = (int) ContactIdBox.getSelectionModel().getSelectedItem();
        }  else{Utility.errorDisplay("Please fill in required information"); return;}

        String location = LocationField.getText();
        String description = DescriptionBox.getText();

        if(title.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Utility.errorDisplay("Please fill in required information"); return;
        }
        if(getTime.getTime((Integer) BeginHH.getValue(), (Integer) BeginMM.getValue()).isAfter(getTime.getTime((Integer) EndTimeHH.getValue(), (Integer) EndtimeMM.getValue())) || getTime.getTime((Integer) BeginHH.getValue(), (Integer) BeginMM.getValue()).equals(getTime.getTime((Integer) EndTimeHH.getValue(), (Integer) EndtimeMM.getValue()))){
            Utility.errorDisplay("Please fill in correct time or date");return;
        }

        if(BeginDateField.getValue() != null) {
            begin = LocalDateTime.of(getDate.date(), getTime.getTime((Integer) BeginHH.getValue(), (Integer) BeginMM.getValue()));
            end = LocalDateTime.of(getDate.date(), getTime.getTime((Integer) EndTimeHH.getValue(), (Integer) EndtimeMM.getValue()));
        } else{Utility.errorDisplay("Please fill in correct time or date"); return;}

        if(util.overlapAppointments(Utility.toUtcTime(begin), Utility.toUtcTime(end)) == false){return;};

        appointmentsDAO.addAppointment(new Appointments(appointmentId, title, description, location, type, Utility.toUtcTime(begin), Utility.toUtcTime(end),  Utility.getCurrentUtcTime(), "User",  Utility.getCurrentUtcTime(), "User", customerId, userId, contactId));

        util.screenSwitch(actionEvent, "AppointmentsForm.fxml");

    }
    /** This method switches back to appointments form without saving data.
      @param actionEvent click of cancel button.
     @throws IOException throws exception in case of I/O exception.*/
    public void cancelBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "AppointmentsForm.fxml");

    }
    /** This method sets the customers ID field based on the name of the selected customer.
      @param actionEvent slection of the customer combo box.
     @throws SQLException throws an exception that provides information on a database access error or other errors. */
    public void CostumerBoxAct(ActionEvent actionEvent) throws SQLException {
        int id = 0;
        String costumerName = (String) CustomerBox.getSelectionModel().getSelectedItem();
        for (int i = 0; i < customerDao.getAllCustomers().size(); i++) {

            if (costumerName.equals(customerDao.getAllCustomers().get(i).getName())) {

                id = customerDao.getAllCustomers().get(i).getId();

            }
        }
        CustomerIdField.setText(String.valueOf(id));
    }
    /** This method sets the end date calendar picker to the same date as the start date.
      @param actionEvent selection of begin calendar. */
    public void BeginDateFieldAct(ActionEvent actionEvent) {
        LocalDate date = BeginDateField.getValue();
        String textDate = String.valueOf(date);
        EndDateField.setPromptText(textDate);
    }
    /** Lambda expression is used to efficiently get a date variable from the calendar picker and cast it to LocalDate.
     @param Date the date from the calendar picker.
     @return LocalDate the cast of the Date type. */
    Date getDate = () -> {LocalDate date = BeginDateField.getValue(); return date;};
    /** Lambda expression gets both values from the time spinners then combines it to a LocalTime variable and returns that variable.
     The lambda expression is used to create a LocalTime variable more concise.
     @param HH 1st spinner that represent the hours.
     @param MM 2nd spinner that represent the minutes.
     @return LocalTime the instance of the HH and MM. */
    Time getTime = (int HH, int MM) -> {LocalTime time = LocalTime.of(HH, MM); return  time;};
/** This method sets EST business hours to the users local time zone within the spinners.
  @param mouseEvent the clicking through the spinner. */
    public void SpinnerHH(MouseEvent mouseEvent) {

      int hour = Utility.LocalToEst((int) BeginHH.getValue());

      if(hour == 22) {

          BeginMM.setValueFactory(Utility.setSpinnerMinutes(hour));

      } else if ( hour == 21) {

          BeginMM.setValueFactory(Utility.setSpinnermm((Integer) BeginMM.getValue()));

      }

    }
    /** This method sets EST business hours to the users local time zone within the spinners.
     @param mouseEvent the clicking through the spinner. */
    public void EndSpinnerHH(MouseEvent mouseEvent) {

        int hour = Utility.LocalToEst((int) EndTimeHH.getValue());

        if(hour == 22) {

            EndtimeMM.setValueFactory(Utility.setSpinnerMinutes(hour));

        } else if (hour == 21) {

            EndtimeMM.setValueFactory(Utility.setSpinnermm((Integer) EndtimeMM.getValue()));

        }
    }
}