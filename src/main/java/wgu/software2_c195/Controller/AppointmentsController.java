package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.AppointmentsDAO;
import wgu.software2_c195.DAO.AppointmentsDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.software2_c195.Appointments;
import wgu.software2_c195.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

/**This class controls the data of the appointments form.*/
public class AppointmentsController implements Initializable {
    public Button addBut;
    public Button updateBut;
    public Button deleteBut;
    public Button costumerBut;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn costumerCol;
    public TableColumn userIdCol;
    public TableView appTable;
    public RadioButton WeeklyBut;
    public RadioButton MonthlyBut;
    public RadioButton AllTimeBut;
    Utility util = new Utility();
    AppointmentsDAO appointmentsDAO = new AppointmentsDAOImpl();

    public AppointmentsController() throws SQLException, ParseException {
    }
    /** This method populates the tableview in appointments form.
      @param url a pointer to a resource.
     @param resourceBundle contains locale specific object. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("userStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("userEnd"));
        costumerCol.setCellValueFactory(new PropertyValueFactory<>("costumerId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        AllTimeBut.setSelected(true);
        appTable.setItems(appointmentsDAO.getAllAppointments());

    }
    /** This method switches to the add appointment form.
      @param actionEvent click of Add button.
     @throws IOException throws exception in case of I/O exception. */

    public void addBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "AddAppointmentForm.fxml");
    }
    /** This method switches to update appointments form.
      @param actionEvent click of Update button.
     @throws IOException throws exception in case of I/O exception. */
    public void updateBut(ActionEvent actionEvent) throws IOException {

        Appointments appointments = (Appointments) appTable.getSelectionModel().getSelectedItem();

        if(appointments == null){

            Utility.errorDisplay("Nothing Selected");

            return;

        }

        ModifyControllerAppointment.passAppiontment(appointments);
        util.screenSwitch(actionEvent, "ModifyAppointmentForm.fxml");

    }
    /** This method deletes the selected appointment.
     Displays pop-up message if nothing selected.
      @param actionEvent click of delete button.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public void deletebut(ActionEvent actionEvent) throws SQLException {

        Appointments app = (Appointments) appTable.getSelectionModel().getSelectedItem();

        if (app == null) {

            Utility.errorDisplay("Nothing Selected");

            return;

        }

        if(Utility.confDisplay("Delete Appointment: Appointment ID: " + app.getAppointmentId() + " | Appointment Type: " + app.getType())) {

            appointmentsDAO.deleteAppointment(app.getAppointmentId());
        }

    }
    /** This method switches to the AllCustomers form.
      @param actionEvent click of Customers Button.
     @throws IOException throws exception in case of I/O exception.*/
    public void costumerBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "AllCustomersForm.fxml");

    }
    /** This method shows only the appointments within a weeks time.
      @param actionEvent selection of weekly radio button.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing. */
    public void WeeklyButAct(ActionEvent actionEvent) throws ParseException {
        MonthlyBut.setSelected(false);
        AllTimeBut.setSelected(false);
        appTable.setItems(appointmentsDAO.getWeeklyAppointments());

    }
    /** This method shows only the appointments within a months time.
     @param actionEvent selection of monthly radio button.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.*/
    public void MonthlyButAct(ActionEvent actionEvent) throws ParseException {
        WeeklyBut.setSelected(false);
        AllTimeBut.setSelected(false);
        appTable.setItems(appointmentsDAO.getMonthlyAppointments());

    }
    /** This method shows all appointments.
      @param actionEvent selection of all-time radio button.*/
    public void AllTimeButAct(ActionEvent actionEvent) {
        WeeklyBut.setSelected(false);
        MonthlyBut.setSelected(false);
        appTable.setItems(appointmentsDAO.getAllAppointments());

    }
    /** This method switches to the reports form.
      @param actionEvent click of reports button.
     @throws IOException throws exception in case of I/O exception.*/
    public void ReportsBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "ReportsForm.fxml");

    }
}
