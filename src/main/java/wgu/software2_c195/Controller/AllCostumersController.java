package wgu.software2_c195.Controller;

import wgu.software2_c195.Appointments;
import wgu.software2_c195.DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.software2_c195.Customer;
import wgu.software2_c195.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
/**This class controls the data of the all customers form.*/
public class AllCostumersController implements Initializable {
    public Button updateBut;
    public Button addBut;
    public Button deleteBut;
    public Button appBut;
    public TableView costumerTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn phoneCol;
    public TableColumn addressCol;
    public TableColumn stateCol;
    public TableColumn postalCol;
    Utility util = new Utility();
    CustomerDao customerDao = new CustomerDAOImpI();
    AppointmentsDAO appointmentsDAO = new AppointmentsDAOImpl();
    public AllCostumersController() throws SQLException, ParseException {
    }
    /** This method sets the tableview with the customers' information.
     @param url a pointer to a resource.
     @param resourceBundle contains locale specific objects.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        costumerTable.setItems(customerDao.getAllCustomers());

    }
    /** This method switched to customer modify form.
     Switches to product modify button by clicking on customer update button and sends selected customer data to the form.
     @param actionEvent action of clicking on button.
     @throws IOException throws exception in case of I/O exception.*/
    public void updateBut(ActionEvent actionEvent) throws IOException {
        Customer customer = (Customer) costumerTable.getSelectionModel().getSelectedItem();

        if(customer == null) {
            Utility.errorDisplay("Nothing Selected");

            return;
        }

        ModifyCustomerController.passCustomer(customer);
        util.screenSwitch(actionEvent, "ModifyCustomer.fxml");

    }
    /** This method switches to the add customer form.
     @param actionEvent click of Add button.
     @throws IOException throws exception in case of I/O exception. */
    public void addBut(ActionEvent actionEvent) throws IOException, SQLException {

        int test = customerDao.generateId();
        System.out.println(test);
        util.screenSwitch(actionEvent, "CustomerAddForm.fxml");
    }
    /** This method deletes the selected appointment.
     Displays pop-up message if nothing selected and verifies if a customer is associated with an appointment, if so displays an error pop-up message.
     @param actionEvent click of delete button. */
    public void deleteBut(ActionEvent actionEvent) throws IOException, SQLException {

        Customer customer = (Customer) costumerTable.getSelectionModel().getSelectedItem();

        if(customer == null) {

            Utility.errorDisplay("Nothing Selected");

            return;

        }

        if(appointmentsDAO.customerAppointment(customer.getId()) != null) {

            if(Utility.confDisplay("Customer: " + customer.getName() + " | ID: " + customer.getId() + " has an appointment scheduled. Delete appointment and customer?")) {
             Appointments app = appointmentsDAO.customerAppointment(customer.getId());
             customerDao.deleteCostumer(customer.getId());
             appointmentsDAO.deleteAppointment(app.getAppointmentId());
            } else {
                return;
            }

        } else {

            if (Utility.confDisplay("Delete: " + customer.getName() + " | ID: " + customer.getId())) {
                customerDao.deleteCostumer(customer.getId());
                util.screenSwitch(actionEvent, "AllCustomersForm.fxml");
            } else {
                return;
            }
        }
    }
/** This method switches screens back to the appointments form.
  @param actionEvent click of appointments button.
  @throws IOException throws exception in case of I/O exception. */
    public void appBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "AppointmentsForm.fxml");

    }
}
