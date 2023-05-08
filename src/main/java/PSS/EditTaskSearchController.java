package PSS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTaskSearchController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TextField editTaskSearchTextField;
    public void findTask(ActionEvent event) throws IOException {
        Tasks getTask = ScheduleController.findTask(editTaskSearchTextField.getText());
        if(getTask != null){
            // switch to edit task editor, pass Task
            switchToTaskEditor(event, getTask);
        }

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
