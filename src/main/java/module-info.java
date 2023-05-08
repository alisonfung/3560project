module _3560project {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;



    opens PSS to javafx.fxml;
    exports PSS;
}