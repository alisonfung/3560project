package PSS;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ExportScheduleController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TextField exportScheduleTextField;
    @FXML private Button exportButton;
    @FXML
    public void initialize(){
        exportButton.disableProperty().bind(
                Bindings.isEmpty(exportScheduleTextField.textProperty()));
    }
    public void exportSchedule(){
        if (ScheduleController.writeSchedule(exportScheduleTextField.getText())){
            showDialog("Success", "Schedule was successfully exported.");
        } else {
            showDialog("Error", "File name invalid.");
        }
    }
    public void showDialog(String title, String content){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
