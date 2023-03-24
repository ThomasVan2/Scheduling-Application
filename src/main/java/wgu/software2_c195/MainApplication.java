package wgu.software2_c195;

import wgu.software2_c195.DAO.DBconnection;
import wgu.software2_c195.DAO.Query;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**This class creates an appointment app.
 @author Thomas van Eekelen
 JavaDocs are found in the javadocSoftware2 folder. */
public class MainApplication extends Application {
/** This method sets a stage. This method will set the main stage. */

 @Override
    public void start(Stage PrimaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Log in form.fxml"));
        PrimaryStage.setTitle("Scheduler");
        PrimaryStage.setScene(new Scene(root,442, 338));
        PrimaryStage.show();
    }
    /** This is the main method. This is the first method that gets called when you run your java program. */
    public static void main(String[] args) throws SQLException{
        launch();
    }
}