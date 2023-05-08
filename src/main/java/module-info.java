module _3560project {
    requires javafx.controls;
    requires javafx.fxml;



    opens PSS to javafx.fxml;
    exports PSS;
}