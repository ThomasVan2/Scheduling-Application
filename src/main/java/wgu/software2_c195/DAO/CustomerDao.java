package wgu.software2_c195.DAO;

import javafx.collections.ObservableList;
import wgu.software2_c195.Customer;

import java.sql.SQLException;
/**This is the interface of CustomerDAOImpl*/
public interface CustomerDao {
    /** This method retrieves a customer based on the customers name.
      @param customerName name of customer
     @return Customer*/
    public Customer getCustomer(String customerName);
    /** This method returns a list of all customers.
      @return list of all customers*/
    public ObservableList<Customer> getAllCustomers();
    /** This method insert a customer into the database.
      @param customer customer to insert.
     @throws SQLException throws an exception that provides information on a database access error or other errors*/
    public void insertCustomer(Customer customer) throws SQLException;
    /** This method deletes a customer from the database based on its id.
      @param customerId id of the customer. */
    public void deleteCostumer(int customerId) throws SQLException;
    /** This method generates an id.
      @return returns the generated id. */
    public int generateId();
    /** This method returns a list with all the names of the customers.
      @return list of names.*/
    public ObservableList<String>CustomersNames();
    /** This method updates a customer information in the database.
      @param customer the new customer information.
     @throws SQLException throws an exception that provides information on a database access error or other errors*/
    void updateCustomer(Customer customer) throws SQLException;
    /** This method gets the customer name based on its id.
      @param id the associated id.
     @return the associated name*/
    String getCustomerName(int id);
    /** This method returns a list of customers based on their state.
      @param state the state of the customers.
     @return list of customers in that state.
     @throws SQLException throws an exception that provides information on a database access error or other errors*/
    ObservableList<Customer> customerInUsa(String state) throws SQLException;


}
