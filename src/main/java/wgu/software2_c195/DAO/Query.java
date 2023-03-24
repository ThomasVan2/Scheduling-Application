package wgu.software2_c195.DAO;

import java.sql.*;

import static wgu.software2_c195.DAO.DBconnection.connection;
/**This class is used to make queries*/
public class Query {
   private static String query;
   private static Statement stmt;
   private static ResultSet result;
/** This method makes the query.
  The method will create a query based on the input.
 @param q the queried string*/
   public static void makeQuery(String q) {
       query = q;
       try{
           stmt = connection.createStatement();

            if(query.toLowerCase().startsWith("select")){
                result=stmt.executeQuery(q);
            }
            if (query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update")) {
                stmt.executeUpdate(q);
            }
       } catch (SQLException e) {
           System.out.println("error");
       }

   }
   /** This method is used to get the result from the operation on the database*/
   public static ResultSet getResult() {
       return result;
   }

}
