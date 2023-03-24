package wgu.software2_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.software2_c195.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**Customer database implement class*/
public class CustomerDAOImpI implements CustomerDao{
   ObservableList<Customer>allCustomers;
    ObservableList<Customer>US;
    static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    /** This method connects to the database and sets the data into a list.
     @throws SQLException throws am exception that provides information on a database access error or other errors. */
    public CustomerDAOImpI() throws SQLException {

        allCustomers = FXCollections.observableArrayList();

        DBconnection.openConnection();
        String sqlStatement = "select * from customers";
        Customer customerResult;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {

            int costumerId = result.getInt("Customer_ID");
            String costumerNameG = result.getString("Customer_Name");
            String costumerAddress = result.getString("Address");
            String costumerPostalCode = result.getString("Postal_Code");
            String phoneNumber = result.getString("Phone");
            int divisionId = result.getInt("Division_ID");
            String createDate = result.getString("Create_Date");
            String createdBy = result.getString("Created_By");
            String lastUpdate = result.getString("Last_Update");
            String lastUpdateBy = result.getString("Last_Updated_By");
            LocalDateTime createDateCalendar = LocalDateTime.parse(result.getString("Create_Date"), DATE_TIME_FORMATTER);
            LocalDateTime lastUpdateCalendar = LocalDateTime.parse(result.getString("Last_Update"), DATE_TIME_FORMATTER);

            customerResult = new Customer(costumerId, costumerNameG, costumerAddress, costumerPostalCode,phoneNumber, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy, divisionId);
            allCustomers.add(customerResult);
        }

        DBconnection.closeConnection();

    }

    @Override
    /**{@inheritDoc}*/
    public Customer getCustomer(String customerName){

        Customer costumer = null;
        
        for(int i = 0; i < allCustomers.size(); i++) {
            
            if(allCustomers.get(i).getName().equals(customerName)) {
                
                costumer = allCustomers.get(i);

            }
            
        }

        return costumer;
    }
    @Override
    /**{@inheritDoc}*/
    public ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }


    @Override
    /**{@inheritDoc}*/
    public void insertCustomer(Customer customer) throws SQLException {

        DBconnection.openConnection();
       String sqlStatement = "insert into customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID )" + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBconnection.connection.prepareStatement(sqlStatement);
        ps.setInt(1, customer.getId());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getAddress());
       ps.setString( 4, customer.getPostalCode());
        ps.setString(5, customer.getPhonenumber());
        ps.setString(6, String.valueOf(customer.getLastUpdate()));
        ps.setString(7, "User");
        ps.setString(8, String.valueOf(customer.getCreateData()));
        ps.setString(9, "User");
        ps.setInt(10, customer.getDivisionId());
        ps.execute();
    }
    @Override
    /**{@inheritDoc}*/
    public void deleteCostumer(int customerId) throws SQLException {
        for(int i = 0; i < getAllCustomers().size(); i++) {
            if(getAllCustomers().get(i).getId() == customerId) {
                getAllCustomers().remove(i);
            }
        }
        DBconnection.openConnection();
        String sqlStatement = "delete FROM customers WHERE Customer_ID = '" + customerId + "'";
        PreparedStatement ps = DBconnection.connection.prepareStatement(sqlStatement);
        ps.execute();

    }
    @Override
    /**{@inheritDoc}*/
    public int generateId(){

        int newId = 0;

        for(int i = 0; i < allCustomers.size(); i++) {

            newId = allCustomers.get(i).getId();
        }
        return  newId + 1;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<String> CustomersNames(){

        ObservableList<String>names = FXCollections.observableArrayList();

        for(int i = 0; i < getAllCustomers().size(); i++) {

            String name = getAllCustomers().get(i).getName();

            names.add(name);
        }
        return names;
    }

    @Override
    /**{@inheritDoc}*/
    public void updateCustomer(Customer customer) throws SQLException {

        int customerId = customer.getId();
        DBconnection.openConnection();
        String sqlStatement = "update customers SET Customer_Name = ? ,Address = ? ,Postal_Code = ? ,Phone = ? , Last_Update = ? ,Last_Updated_By = ? ,Division_ID = ?  WHERE Customer_ID = '" + customerId + "'";
        PreparedStatement ps = DBconnection.connection.prepareStatement(sqlStatement);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhonenumber());
        ps.setString(5, String.valueOf(customer.getLastUpdate()));
        ps.setString(6, "User");
        ps.setInt(7, customer.getDivisionId());
        ps.execute();
    }
    @Override
    /**{@inheritDoc}*/
    public String getCustomerName(int id) {

        String name = "";
        for(int i = 0; i < allCustomers.size(); i++) {

            if(allCustomers.get(i).getId() == id) {

                name = allCustomers.get(i).getName();

            }

        }
        return name;
    }

    @Override
    /**{@inheritDoc}*/
    public ObservableList<Customer> customerInUsa(String state) throws SQLException {
        US = FXCollections.observableArrayList();

        for(int i = 0; i < allCustomers.size(); i++) {

            if(allCustomers.get(i).getState().equals(state)) {

                US.add(allCustomers.get(i));

            }

        }
        return US;
    }

}
