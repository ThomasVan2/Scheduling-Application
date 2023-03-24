module wgu.software2_c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens wgu.software2_c195 to javafx.fxml;
    exports wgu.software2_c195;
    exports wgu.software2_c195.Controller;
    opens wgu.software2_c195.Controller to javafx.fxml;
}