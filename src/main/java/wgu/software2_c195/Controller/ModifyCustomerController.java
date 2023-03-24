package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import wgu.software2_c195.Customer;
import wgu.software2_c195.States;
import wgu.software2_c195.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
/**This class controls the data of the modify customer form.*/
public class ModifyCustomerController implements Initializable {
    public Button saveBut;
    public Button cancelBut;
    public ComboBox StateCombo;
    public TextField CustomerIdField;
    public TextField NameField;
    public TextField PhoneField;
    public TextField AddressField;
    public ComboBox CountryCombo;
    public TextField ZipcodeField;
    CustomerDao customerDao = new CustomerDAOImpI();
    CountriesDao countryDao = new CountriesDaoImpl();
    StatesDao statesDao = new StatesDaoImpl();
    Utility util = new Utility();
    private static Customer thePass = null;

    public ModifyCustomerController() throws SQLException, ParseException {
    }
/** This method sets the tableview with the passed down data.
 @param url a pointer to a resource.
 @param resourceBundle contains locale specific objects.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerIdField.setText(String.valueOf(thePass.getId()));
        NameField.setText(thePass.getName());
        PhoneField.setText(thePass.getPhonenumber());
        AddressField.setText(thePass.getAddress());
        ZipcodeField.setText(thePass.getPostalCode());
        try {
            CountryCombo.setValue(getCountryName());
            StateCombo.setValue(thePass.getState());
            StateCombo.setItems(setState());
            CountryCombo.setItems(countryDao.getAllCountriesName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /** This method receives data.
     This method receives data from the selected customer.
     @param pass the selected customer. */
    public static void passCustomer(Customer pass) {thePass = pass;}
    /** This method save the inputted data and switches back to the all customers form.
     @param actionEvent click of button.
     @throws IOException throws exception in case of I/O exception.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public void saveBut(ActionEvent actionEvent) throws IOException, ParseException, SQLException {
        String country= "";
        int customerId = Integer.parseInt(CustomerIdField.getText());
        String name = NameField.getText();
        String phone = PhoneField.getText();
        String address = AddressField.getText();
        if(CountryCombo.getValue() != null) {
            country = (String) CountryCombo.getValue();
        }
        String state = StateComboBut(actionEvent);
        String zipCode = ZipcodeField.getText();
        if(name.isEmpty() || phone.isEmpty() || address.isEmpty() || country.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
            Utility.errorDisplay("Please fill in the required information");
            return;
        }
        customerDao.updateCustomer(new Customer(customerId, name, address, zipCode, phone , Utility.getCurrentUtcTime() ,
                "" , Utility.getCurrentUtcTime(), "User",statesDao.getStateId(state)));

        util.screenSwitch(actionEvent, "AllCustomersForm.fxml");

    }
    /** This method switches back to all customers form without saving data.
     @param actionEvent click of cancel button. */
    public void cancelBut(ActionEvent actionEvent) throws IOException {

        util.screenSwitch(actionEvent, "AllCustomersForm.fxml");

    }
    /** This method returns the selected state.
      @param actionEvent selection of state.
     @return Returns the selected state.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public String StateComboBut(ActionEvent actionEvent) throws SQLException {

        String state = "";
        if(StateCombo.getValue() != null) {
            state = String.valueOf(StateCombo.getValue());
        }
        return state;

    }
    /** This method gets the selected country and enables the state combo box.
      @param actionEvent selection of country.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public void CountryComboBut(ActionEvent actionEvent) throws SQLException {

        String country = (String) CountryCombo.getValue();
        int countryId = countryDao.getCountryId(country);

        StateCombo.setItems(statesDao.returnStates(countryId));
        StateCombo.setPromptText("Choose State");

    }
    /** This method gets the countries name through use of passed country ID.
      @return Returns the countries name.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public String getCountryName() throws SQLException {

       String stateName = thePass.getState();
        States state = statesDao.getState(stateName);
        int countryId = state.getCountryId();
        String countryName = countryDao.getCountryName(countryId);
        return countryName;

    }
    /** This method sets the states based on the selected country.
      @return Returns the list of the countries states.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public ObservableList<String>  setState() throws SQLException {

        String stateName = thePass.getState();
        States state = statesDao.getState(stateName);
        int countryId = state.getCountryId();
        ObservableList<String> SetstateName = statesDao.returnStates(countryId);
        return SetstateName;

    }

}
