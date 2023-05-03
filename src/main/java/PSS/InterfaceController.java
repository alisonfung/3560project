package PSS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private HBox endDateBox;
    @FXML
    private RadioButton transientButton, recurringButton, antiButton;

    public void displayRecurringAttributes(ActionEvent event) {
        if (recurringButton.isSelected()) {
            endDateBox.setOpacity(1);
        } else if (transientButton.isSelected()){
            endDateBox.setOpacity(0);
        }  else if (antiButton.isSelected()){
            endDateBox.setOpacity(0);
        }
    }
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCreateTask(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("createTask.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}