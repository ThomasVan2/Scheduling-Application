package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.software2_c195.Utility;
import java.text.DateFormatSymbols;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
/**This class controls the data of the reports' controller.*/
public class ReportsController implements Initializable {
    public javafx.scene.control.TableView TableView;
    public TableColumn AppId;
    public TableColumn TitleCol;
    public TableColumn TypeCol;
    public TableColumn DescrtiptionCol;
    public TableColumn StartCol;
    public TableColumn EndCol;
    public TableColumn CustomerIdCol;
    public RadioButton ContactBut;
    public RadioButton MonthBut;
    public RadioButton OtherReportBut;
    public ComboBox Combo2;
    public ComboBox Combo1;
    public Button BackToMain;
    public Label reportLabel;
    AppointmentsDAO appointmentsDAO = new AppointmentsDAOImpl();
    ContactsDAO contactsDAO = new ContactsDAOImpl();
    ObservableList<String> months = FXCollections.observableArrayList(dfs.getMonths());
    private static final DateFormatSymbols dfs = new DateFormatSymbols();
    Utility util = new Utility();
    StatesDao statesDao = new StatesDaoImpl();
    CustomerDao customerDao = new CustomerDAOImpI();

    String contactName = "";

    public ReportsController() throws SQLException, ParseException {}
    /** This method populates the tableview in reports form.
     @param url a pointer to a resource.
     @param resourceBundle contains locale specific object. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        DescrtiptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("userStart"));
        EndCol.setCellValueFactory(new PropertyValueFactory<>("userEnd"));
        CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("costumerId"));
        TableView.setItems(appointmentsDAO.getAllAppointments());
        Combo1.setDisable(true);
        Combo2.setDisable(true);

    }
    /** This method controls the label, combo boxes and other radio buttons based on its purpose.
      Label is reset to blank, combo box 1 is enabled and filled with contacts IDs, combo box 2 is disabled and other radio buttons are unselected.
     @param actionEvent selection of the radio Button.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public void ContactAct(ActionEvent actionEvent) throws SQLException {
        reportLabel.setText("");
        Combo1.setDisable(false);
        Combo2.setDisable(true);
        MonthBut.setSelected(false);
        OtherReportBut.setSelected(false);
        Combo1.setItems(contactsDAO.ContactIds());
        Combo2.valueProperty().set(null);
    }
/** This method controls the label, combo boxes and other radio buttons based on its purpose.
  The report label is reset to blank, both combo boxes are enabled, combo box 1 is filled with appointment types and combo box 2 is filled with the months.
    @param actionEvent selecion of radio button.*/
    public void MonthAct(ActionEvent actionEvent) {
        reportLabel.setText("");
        Combo2.setPromptText("");
        Combo1.setDisable(false);
        Combo2.setDisable(false);
        ContactBut.setSelected(false);
        OtherReportBut.setSelected(false);
        Combo1.setItems(appointmentsDAO.Types());
        Combo2.setItems(months);
    }
/** This method controls the label, combo boxes and other radio buttons based on its purpose.
  one of the combo boxes is disabled, the other gives a selection of states and the label gives the amount of customers who live there.
  @param actionEvent selection the radio button.*/
    public void OtherReportAct(ActionEvent actionEvent) throws SQLException {
        reportLabel.setText("");
        Combo1.setDisable(false);
        Combo1.setItems(statesDao.getAllStatesName());
        Combo2.valueProperty().set(null);
        ContactBut.setSelected(false);
        MonthBut.setSelected(false);
        OtherReportBut.setSelected(true);
        Combo2.setDisable(true);

    }
    /** This method gets selected information of the combo boxes.
     This method fills the tableview and the report label with the result of the selected data.
     @param actionEvent selection of combo box 2.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing. */
    public void Combo2But(ActionEvent actionEvent) throws ParseException {
        if (MonthBut.isSelected()) {
            if(Combo1.getSelectionModel().getSelectedItem() != null && Combo2.getSelectionModel().getSelectedItem() != null) {
                String type = (String) Combo1.getSelectionModel().getSelectedItem();
                String month = (String) Combo2.getSelectionModel().getSelectedItem();
                TableView.setItems(appointmentsDAO.byTypeAndMonth(type, month));
                reportLabel.setText("Reports: " + appointmentsDAO.byTypeAndMonth(type, month).size());
            }
        }
    }
    /** This method gets selected information of the combo boxes.
     This method fills the tableview and the report label with the result of the selected data and based on the selection of the radio buttons.
     @param actionEvent selection of combo box 2.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing. */
    public void Combo1But(ActionEvent actionEvent) throws ParseException, SQLException {
        if (ContactBut.isSelected()) {
            if(Combo1.getSelectionModel().getSelectedItem() != null) {
                int id = (int) Combo1.getSelectionModel().getSelectedItem();
                Combo2.setPromptText(contactsDAO.ContactName(id));
                TableView.setItems(appointmentsDAO.selectedContact(id));
                reportLabel.setText("Reports: " + appointmentsDAO.selectedContact(id).size());
            }
        }
        if (MonthBut.isSelected()) {
            if(Combo1.getSelectionModel().getSelectedItem() != null && Combo2.getSelectionModel().getSelectedItem() != null){
                String type = (String) Combo1.getSelectionModel().getSelectedItem();
                String month = (String) Combo2.getSelectionModel().getSelectedItem();
                TableView.setItems(appointmentsDAO.byTypeAndMonth(type, month));
                reportLabel.setText("Reports: " + appointmentsDAO.byTypeAndMonth(type, month).size());
            }
        }
        if(OtherReportBut.isSelected()) {
            if(Combo1.getSelectionModel().getSelectedItem() != null){
                String state = (String) Combo1.getSelectionModel().getSelectedItem();
                reportLabel.setText("Customer(s) located: " + customerDao.customerInUsa(state).size());
            }
        }
    }
    /** This method switches back to appointments form.
      @param actionEvent click of back to main button.
     @throws IOException throws exception in case of I/O exception.*/
    public void BackToMainBut(ActionEvent actionEvent) throws IOException {
        util.screenSwitch(actionEvent, "AppointmentsForm.fxml");

    }
}
