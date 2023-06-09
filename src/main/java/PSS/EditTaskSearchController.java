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

public class EditTaskSearchController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TextField editTaskSearchTextField;
    @FXML private Button searchButton;
    public void initialize(){
        searchButton.disableProperty().bind(
                Bindings.isEmpty(editTaskSearchTextField.textProperty()));
    }

    public void findTask(ActionEvent event) throws IOException {
        Tasks getTask = ScheduleController.findTask(editTaskSearchTextField.getText());
        if(getTask != null){
            // switch to edit task editor, pass Task
            switchToTaskEditor(event, getTask);
        } else {
            showDialog("Error", "Task not found.");
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
    public void switchToTaskEditor(ActionEvent event, Tasks task) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editTaskEditor.fxml"));
        root = loader.load();
        EditTaskEditorController etec = loader.getController();
        etec.setTask(task);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
