module com.example.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens PSS to javafx.fxml;
    exports PSS;
}