package wgu.software2_c195.DAO;
import javafx.collections.ObservableList;
import wgu.software2_c195.Appointments;

import java.sql.SQLException;
import java.text.ParseException;

/**This is the interface of AppointmentsDAOImpl*/
public interface AppointmentsDAO  {
    /** This method retrieves all appointments from the database and sets them in a list.
      @return a list of all appointments*/
    public ObservableList<Appointments> getAllAppointments();
    /** This method returns an appointment based in an appointment id
      @return appointment*/
    public Appointments getAppointment(int appointmentId);
    /** This method generates an ID.
      @return Id*/
    public int generateAppId();
    /** This method adds an appointment.
      @param appointments the appointment that needs to be added.
     @throws SQLException throws an exception that provides information on a database access error or other errors.*/
    public void addAppointment(Appointments appointments) throws SQLException;
    /** This method deletes an appointment based on the id.
     * @param appointmentId the associated appointment id.
      @throws SQLException throws an exception that provides information on a database access error or other errors.*/
    public void deleteAppointment(int appointmentId) throws SQLException;
    /** This method returns a list of appointment types.
      @return list of types.*/
    ObservableList<String> Types();
    /** This method upates an appointment.
      @param appointments the appointment that needs  updating.
     @throws SQLException throws an exception that provides information on a database access error or other errors.*/
    void UpdateAppointment(Appointments appointments) throws SQLException;
    /** This method returns the appointments within the coming month.
      @return list of appointments within a months time.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.*/
    ObservableList<Appointments> getMonthlyAppointments() throws ParseException;
    /** This method returns the appointments within the coming week.
     @return list of appointments within a weeks time.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.*/
    ObservableList<Appointments> getWeeklyAppointments() throws ParseException;
    /** This method displays a pop-up if there is an appointment within 15 minutes.*/
    public void appointmentAlert();
    /** This method retuns a list of appointments associated with a contact.
      @param id associated id from the contact.
      @return a list of appointments.*/
    ObservableList<Appointments> selectedContact(int id);
    /** This method returns a list of appointments based on a type and a month.
      @param Month the month
     @param Type the type of appointment
     @return list of appointments
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.*/
    ObservableList<Appointments> byTypeAndMonth(String Type, String Month) throws ParseException;
    /** This method returns appointments based on types.
      @param Type type of appointments.
     @return list of associated appointment.*/
    ObservableList<Appointments>getTypes(String Type);
    /** This method returns a boolean if the customer id is present in the all appointments list.
      @param id id of customer.
     @return Boolean*/
    public Appointments customerAppointment(int id);

}
