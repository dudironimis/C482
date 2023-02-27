module com.example.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.c482 to javafx.fxml;
    exports com.example.c482;
}