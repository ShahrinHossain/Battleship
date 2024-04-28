module com.example.bships {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bships to javafx.fxml;
    exports com.example.bships;
}