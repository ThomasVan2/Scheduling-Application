package wgu.software2_c195.Controller;

import wgu.software2_c195.DAO.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import wgu.software2_c195.Customer;
import wgu.software2_c195.Utility;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
/**This class controls the data of the add customer controller.*/
public class CostumerAddController implements Initializable {
    public TextField CostumerIdField;
    public Button saveBut;
    public Button cancelBut;
    public ComboBox StateComboBox;
    public TextField nameText;
    public TextField phoneText;
    public TextField adressText;
    public ComboBox CountryComboBox;
    public TextField zipCodeText;
    CustomerDao customerDao = new CustomerDAOImpI();
    CountriesDao countryDao = new CountriesDaoImpl();
    StatesDao statesDao = new StatesDaoImpl();
    Utility util = new Utility();

    public CostumerAddController() throws SQLException, ParseException {
    }
/** This method initializes the country combo box with the countries names.
 @param url a pointer to a resource.
 @param resourceBundle contains locale specific objects.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            CountryComboBox.setItems(countryDao.getAllCountriesName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        StateComboBox.setDisable(true);
    }
    /** This method save the inputted data and switches back to the all customers form.
     @param actionEvent click of button.
     @throws IOException throws exception in case of I/O exception.
     @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public void saveBut(ActionEvent actionEvent) throws IOException, SQLException, ParseException {

        String name = nameText.getText();
        String phone = phoneText.getText();
        String address = adressText.getText();
        String country = "";
            if (CountryComboBox.getValue() != null) {
                country = (String) CountryComboBox.getValue();
        }
        String state = comboBoxact(actionEvent);
        String zipCode = zipCodeText.getText();
        if(name.isEmpty() || phone.isEmpty() || address.isEmpty() || country.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
            Utility.errorDisplay("Please fill in the required information");
            return;
        }
        customerDao.insertCustomer(new Customer(customerDao.generateId(), name, address, zipCode, phone , Utility.getCurrentUtcTime() ,
                "User: get ID later" , Utility.getCurrentUtcTime(), "User",statesDao.getStateId(state)));

        util.screenSwitch(actionEvent, "AllCustomersForm.fxml");

    }
    /** This method switches back to all customers form without saving data.
     @param actionEvent click of cancel button. */
    public void cancelBut(ActionEvent actionEvent) throws IOException, SQLException {

        util.screenSwitch(actionEvent, "AllCustomersForm.fxml");

    }
    /** This method returns the selected state.
     @param actionEvent selection of state.
     @return Returns the selected state.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public String comboBoxact(ActionEvent actionEvent) throws SQLException {

        String state = "";

        if(StateComboBox.getValue() != null) {
             state = String.valueOf(StateComboBox.getValue());
        }
        return state;

    }
    /** This method gets the selected country and enables the state combo box.
     @param actionEvent selection of country.
     @throws SQLException throws am exception that provides information on a database access error or other errors.*/
    public void comboboxButCountry(ActionEvent actionEvent) throws SQLException {

        String country = (String) CountryComboBox.getValue();

        int countryId = countryDao.getCountryId(country);
        StateComboBox.setDisable(false);
        StateComboBox.setItems(statesDao.returnStates(countryId));

    }
}
