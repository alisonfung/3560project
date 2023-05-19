module _3560project {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.json;

    opens PSS to javafx.fxml;
    exports PSS;
}