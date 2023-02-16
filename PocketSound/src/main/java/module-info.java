module com.example.pocketsound {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jlayer;


    opens com.example.pocketsound to javafx.fxml;
    exports com.example.pocketsound;
}