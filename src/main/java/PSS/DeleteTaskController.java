package PSS;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteTaskController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Tasks taskToBeDeleted;
    @FXML private TextField deleteTaskSearchTextField;
    @FXML private Text deleteTaskText;
    @FXML private Button deleteButton;
    @FXML private Button searchButton;
    @FXML
    public void initialize(){
        searchButton.disableProperty().bind(
                Bindings.isEmpty(deleteTaskSearchTextField.textProperty()));
    }
    public void findTask(ActionEvent event) throws IOException {
        taskToBeDeleted = ScheduleController.findTask(deleteTaskSearchTextField.getText());
        if(taskToBeDeleted != null){
            deleteTaskText.setText("Deleting '"+ taskToBeDeleted.getName() +  "'. Continue?");
            deleteTaskText.setVisible(true);
            deleteButton.setVisible(true);
        } else {
            showDialog("Error", "Task not found.");
        }

    }
    public void deleteTask(ActionEvent event) throws IOException {
        if (ScheduleController.deleteTask(taskToBeDeleted)==true){
            showDialog("Success", "Task successfully deleted.");
        } else {
            showDialog("Error", "Task cannot be deleted due to a time conflict.");
        }
        deleteTaskText.setVisible(false);
        deleteButton.setVisible(false);
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
