package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
/**This class controls the data of the modify appointments form.*/
public class ModifyControllerAppointment implements Initializable {
    public Button saveBut;
    public Button cancelBut;
    public ComboBox CustomerBox;
    public TextField AppIdField;
    public ComboBox UserIdBox;
    public TextArea DescriptionField;
    public TextField LocationField;
    public DatePicker CalendarPicker;
    public Spinner BeginHH;
    public Spinner BeginMM;
    public Spinner EndMM;
    public Spinner EndHH;
    public TextField TitleField;
    public TextField CustomerIdField;
    public ComboBox ContactIdBox;
    public DatePicker endCalendar;
    public ComboBox TypeBox;
    Utility util = new Utility();
    UserDAO userDAO = new UserDAOlmpl();
    AppointmentsDAO appointmentsDAO = new AppointmentsDAOImpl();
    ContactsDAO contactsDAO = new ContactsDAOImpl();
    private static Appointments appPass = null;
    CustomerDao customerDao = new CustomerDAOImpI();

    public ModifyControllerAppointment() throws SQLException, ParseException {
    }

/** This method initializes the combo boxes and text fields with the passed through information.
 @param url a pointer to a resource.
 @param resourceBundle contains locale specific objects. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppIdField.setText(String.valueOf(appPass.getAppointmentId()));
        TitleField.setText(appPass.getTitle());
        TypeBox.setItems(appointmentsDAO.Types());
        TypeBox.setValue(appPass.getType());
        ContactIdBox.setItems(contactsDAO.ContactIds());
        ContactIdBox.setValue(appPass.getContactId());
        LocationField.setText(appPass.getLocation());
        CalendarPicker.setValue(getPassDate());
        endCalendar.setValue(getPassDate());
        BeginHH.setValueFactory(Utility.setSpinnerHH(appPass.getUserStart().getHour()));
        EndHH.setValueFactory(Utility.setSpinnerHH(appPass.getUserEnd().getHour()));
        BeginMM.setValueFactory(Utility.setSpinnermm(appPass.getUserStart().getMinute()));
        EndMM.setValueFactory(Utility.setSpinnermm(appPass.getUserEnd().getMinute()));
        CustomerBox.setItems(customerDao.CustomersNames());
        CustomerBox.setValue(customerDao.getCustomerName(appPass.getCostumerId()));
        UserIdBox.setItems(userDAO.getAllUsersId());
        UserIdBox.setValue(appPass.getUserId());
        CustomerIdField.setText(String.valueOf(appPass.getCostumerId()));
        DescriptionField.setText(appPass.getDescription());

    }
    /** This method receives data.
      This method receives data from the selected appointments.
     @param appointments the selected appointment. */
    public static void passAppiontment(Appointments appointments) {
        appPass = appointments;
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
        int appointmentId = Integer.parseInt(AppIdField.getText());

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
        String description = DescriptionField.getText();

        if(title.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Utility.errorDisplay("Please fill in required information"); return;
        }
        if(getTime.getTime((Integer) BeginHH.getValue(), (Integer) BeginMM.getValue()).isAfter(getTime.getTime((Integer) EndHH.getValue(), (Integer) EndMM.getValue())) || getTime.getTime((Integer) BeginHH.getValue(), (Integer) BeginMM.getValue()).equals(getTime.getTime((Integer) EndHH.getValue(), (Integer) EndMM.getValue()))){
            Utility.errorDisplay("Please fill in correct time or date");return;
        }

        if(CalendarPicker.getValue() != null) {
            begin = LocalDateTime.of(getDate.date(), getTime.getTime((Integer) BeginHH.getValue(), (Integer) BeginMM.getValue()));
            end = LocalDateTime.of(getDate.date(), getTime.getTime((Integer) EndHH.getValue(), (Integer) EndMM.getValue()));
        } else{Utility.errorDisplay("Please fill in correct time or date"); return;}

        if(!(appPass.getStart().equals(Utility.toUtcTime(begin)) && appPass.getEnd().equals(Utility.toUtcTime(end)))) {

            if(util.overlapModifyAppointment(Utility.toUtcTime(begin), Utility.toUtcTime(end), appointmentId)){
                return;
            }
        }

        appointmentsDAO.UpdateAppointment(new Appointments(appointmentId, title, description, location, type, Utility.toUtcTime(begin), Utility.toUtcTime(end),  Utility.getCurrentUtcTime(), "User",  Utility.getCurrentUtcTime(), "User", customerId, userId, contactId));
        util.screenSwitch(actionEvent, "AppointmentsForm.fxml");

    }
    /** This method switches back to appointments form without saving data.
     @param actionEvent click of cancel button. */
    public void cancelBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "AppointmentsForm.fxml");

    }
    /** This method gets the passed LocalDateTime parameter and cats it to LocalDate.
      @return date the received date in LocalDate.*/
    public LocalDate getPassDate() {

        LocalDateTime dateAndTime = appPass.getUserStart();
        LocalDate date = dateAndTime.toLocalDate();
        return date;
    }
    /** This method sets the customers ID field based on the name of the selected customer.
     @param actionEvent slection of the customer combo box. */
    public void CustomerBoxAct(ActionEvent actionEvent) {
        int id = 0;
        String costumerName = (String) CustomerBox.getSelectionModel().getSelectedItem();
        for (int i = 0; i < customerDao.getAllCustomers().size(); i++) {

            if (costumerName.equals(customerDao.getAllCustomers().get(i).getName())) {

                id = customerDao.getAllCustomers().get(i).getId();

            }
        }
        CustomerIdField.setText(String.valueOf(id));
    }
    /** Lambda expression is used to efficiently get a date variable from the calendar picker and cast it to LocalDate.
     @param Date the date from the calendar picker.
     @return LocalDate the cast of the Date type. */
    Date getDate = () -> {LocalDate date = CalendarPicker.getValue(); return date;};
    /** Lambda expression gets both values from the time spinners then combines it to a LocalTime variable and returns that variable.
     The lambda expression is used to create a LocalTime variable more concise.
     @param HH 1st spinner that represent the hours.
     @param MM 2nd spinner that represent the minutes.
     @return LocalTime the instance of the HH and MM. */
    Time getTime = (int HH, int MM) -> {LocalTime time = LocalTime.of(HH, MM); return  time;};

}
