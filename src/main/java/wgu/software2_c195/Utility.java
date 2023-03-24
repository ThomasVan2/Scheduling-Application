package wgu.software2_c195;

import wgu.software2_c195.DAO.AppointmentsDAO;
import wgu.software2_c195.DAO.AppointmentsDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
/**This class contains repeated used methods*/
public class Utility implements Initializable {
    AppointmentsDAOImpl appointmentsDAO = new AppointmentsDAOImpl();

    public Utility() throws SQLException, ParseException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method switches views.
     *
     * @param actionEvent action taken when needs to switch.
     * @param screen      address of view.
     * @throws IOException throws exception in case of I/O exception.
     */
    public void screenSwitch(ActionEvent actionEvent, String screen) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(screen));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * This method translates the given input.
     * The method uses keywords from the resourceBundle to translate.
     *
     * @param key    The word that needs translating.
     * @param locale The region.
     * @return The translated String.
     */
    public String languageSwitch(String key, Locale locale) {

        String translate = "";

        if (locale.getLanguage().equals("eng")) {

            ResourceBundle defaultMessages = ResourceBundle.getBundle("MessagesBundle_eng", locale);
            translate = defaultMessages.getString(key);
            return translate;
        } else if (locale.getLanguage().equals("fr")) {

            ResourceBundle defaultMessages = ResourceBundle.getBundle("MessagesBundle_fr", locale);
            translate = defaultMessages.getString(key);
            return translate;
        }

        return translate;

    }

    /**
     * This method formats a string to calendar
     *
     * @param toDate the string that needs formatted
     * @return Calendar the string that is formatted
     */
    public static Calendar calenderPick(String toDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = sdf.parse(toDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    /**
     * This method displays an error pop-up.
     *
     * @param message message that needs to be displayed.
     */
    public static void errorDisplay(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR, message);

        alert.setTitle("Error Dialog");
        alert.setContentText(message);
        alert.showAndWait();

    }

    /**
     * @return current UTC time
     * @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     */
    public static LocalDateTime getCurrentUtcTime() throws ParseException {  // handling ParseException

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime timeAndDate = LocalDateTime.now(ZoneOffset.UTC);

        String stringDateAndTime = DATE_TIME_FORMATTER.format(timeAndDate);

        LocalDateTime dateTime = LocalDateTime.parse(stringDateAndTime, DATE_TIME_FORMATTER);

        System.out.println(dateTime);

        return dateTime;

    }

    /**
     * This method takes the inputted date and time and converts that in UTC
     *
     * @param localDateTime the time that needs to be converted
     * @return UTC time
     */
    public static LocalDateTime toUtcTime(LocalDateTime localDateTime) {

        ZoneId myZoneId = ZoneId.systemDefault();

        ZonedDateTime usss = localDateTime.atZone(myZoneId).withZoneSameInstant(ZoneId.of("UTC"));

        return usss.toLocalDateTime();
    }

    /**
     * This method converts UTC time and date to the users local time.
     *
     * @param localDateTime the time and date that needs converting
     * @return the converted time
     */
    public static LocalDateTime UserTime(LocalDateTime localDateTime) { //put in other variable for est

        ZoneId myZoneId = ZoneId.systemDefault();

        ZonedDateTime usss = localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(myZoneId);

        return usss.toLocalDateTime();

    }

    /**
     * This method converts the EST business hours to the users local time
     *
     * @param value the that needs setting
     * @return the spinner values
     */
    static public SpinnerValueFactory<Integer> setSpinnerHH(int value) {

        int openingHour = EstToLocalTime(8);
        int closingHour = EstToLocalTime(22);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(openingHour, closingHour);
        valueFactory.setValue(value);
        return valueFactory;
    }

    /**
     * This method converts the EST business hours to the users local time
     *
     * @param value the that needs setting
     * @return the spinner values
     */
    static public SpinnerValueFactory<Integer> setSpinnermm(int value) {

        int lastHour = value;

        if (lastHour == 22) {

            SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
            return valueFactory1;
        }

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        valueFactory1.setValue(value);
        return valueFactory1;

    }

    /**
     * This method converts EST to the users local time.
     *
     * @param value the hour that needs converting
     * @return returns the converted hour
     */
    public static Integer EstToLocalTime(int value) {

        LocalDate date = LocalDate.now();
        LocalTime Hours = LocalTime.of(value, 0);
        ZoneId EST = ZoneId.of("US/Eastern");
        ZonedDateTime EstTime = ZonedDateTime.of(date, Hours, EST); //The inputted time is based on EST time
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        Instant EstToLocalTime = EstTime.toInstant();
        ZonedDateTime EstToLocalZDT = EstTime.withZoneSameLocal(localZoneId); //Use this to get from est to local again.
        ZonedDateTime getToLocalZDT = EstToLocalTime.atZone(localZoneId);

        int Hour = getToLocalZDT.getHour();

        return Hour;

    }

    /**
     * This method converts local time to EST time.
     *
     * @param value is the hour that needs converting.
     * @return the converted hour
     */
    public static Integer LocalToEst(int value) {

        LocalDate date = LocalDate.now();
        LocalTime Hours = LocalTime.of(value, 0);
        ZoneId EST = ZoneId.of("US/Eastern");
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime EstTime = ZonedDateTime.of(date, Hours, localZoneId); //The inputted time is based on EST time

        Instant EstToLocalTime = EstTime.toInstant();
        ZonedDateTime EstToLocalZDT = EstTime.withZoneSameLocal(localZoneId); //Use this to get from est to local again.
        ZonedDateTime getToLocalZDT = EstToLocalTime.atZone(EST);

        int hour = getToLocalZDT.getHour();
        System.out.println(hour);
        return hour;


    }

    /**
     * sets the minutes of the spinner
     *
     * @param value the minutes
     * @return the spinners value
     */
    public static SpinnerValueFactory<Integer> setSpinnerMinutes(int value) {

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
        return valueFactory1;

    }

    /**
     * This method check if there are overlapping appointments.
     *
     * @param localDateTimeStart the start time and date
     * @param localTimeEnd       the end time and date
     * @return returns false if the appointment overlaps
     */
    public Boolean overlapAppointments(LocalDateTime localDateTimeStart, LocalDateTime localTimeEnd) {

        LocalDate date1 = localDateTimeStart.toLocalDate();
        LocalTime start = localDateTimeStart.toLocalTime();
        LocalTime end = localTimeEnd.toLocalTime();

        for (int i = 0; i < appointmentsDAO.getAllAppointments().size(); i++) {

            LocalDate date2 = appointmentsDAO.getAllAppointments().get(i).getStart().toLocalDate();
            LocalTime start2 = appointmentsDAO.getAllAppointments().get(i).getStart().toLocalTime();
            LocalTime end2 = appointmentsDAO.getAllAppointments().get(i).getEnd().toLocalTime();

            if (date1.equals(date2) && !start.isAfter(end2) && !start2.isAfter(end)) {

                Utility.errorDisplay("Overlapping Appointment");
                return false;

            }


        }
        return true;
    }
    /**
     * This method check if there are overlapping appointments.
     *
     * @param localDateTimeStart the start time and date
     * @param localTimeEnd the end time and date
     * @return returns false if the appointment overlaps
     */
    public Boolean overlapModifyAppointment(LocalDateTime localDateTimeStart, LocalDateTime localTimeEnd, int appointmentId) {

        LocalDate date1 = localDateTimeStart.toLocalDate();
        LocalTime start = localDateTimeStart.toLocalTime();
        LocalTime end = localTimeEnd.toLocalTime();

        for (int i = 0; i < appointmentsDAO.getAllAppointments().size(); i++) {

            LocalDate date2 = appointmentsDAO.getAllAppointments().get(i).getStart().toLocalDate();
            LocalTime start2 = appointmentsDAO.getAllAppointments().get(i).getStart().toLocalTime();
            LocalTime end2 = appointmentsDAO.getAllAppointments().get(i).getEnd().toLocalTime();
            int appId = appointmentsDAO.getAllAppointments().get(i).getAppointmentId();
            if(appId != appointmentId) {
                if (date1.equals(date2) && !start.isAfter(end2) && !start2.isAfter(end)) {

                    Utility.errorDisplay("Overlapping Appointment");
                    return true;

                }

            }

        }
        return false;



    }

    /**
     * This method is a confirmation message pop up
     *
     * @param message message that needs to be displayed.
     * @return true if selection of OK button on pop up and false if cancel
     */
    public static boolean confDisplay(String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            return true;

        } else {

            return false;

        }

    }

}


