package PSS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewTaskController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TextField viewTaskSearchTextField;
    @FXML private Text taskNameText;
    @FXML private Text typeText;
    @FXML private Text startDateText;
    @FXML private Text startTimeText;
    @FXML private Text durationText;
    @FXML private Text frequencyText;
    @FXML private Text endDateText;
    @FXML private HBox taskNameBox;
    @FXML private VBox attributesBox;
    @FXML private VBox recurringBox;
    private Tasks viewedTask;

    public void viewTask(ActionEvent event) throws IOException {
        viewedTask = ScheduleController.findTask(viewTaskSearchTextField.getText());
        taskNameBox.setVisible(false);
        attributesBox.setVisible(false);
        recurringBox.setVisible(false);
        if (viewedTask != null){
            // TODO: display task attributes
            taskNameBox.setVisible(true);
            attributesBox.setVisible(true);
            recurringBox.setVisible(false);
            taskNameText.setText(viewedTask.getName());
            typeText.setText("Type: " + viewedTask.getType());
            startDateText.setText("Start Date: " + viewedTask.getStartDate());
            startTimeText.setText("Start Time: " + viewedTask.getStartTime());
            durationText.setText("Duration: " + viewedTask.getDuration());
            if (viewedTask instanceof RecurringTasks) {
                frequencyText.setText("Frequency: "+ ((RecurringTasks) viewedTask).getFrequency());
                endDateText.setText("End Date: " + ((RecurringTasks) viewedTask).getEndDate());
                recurringBox.setVisible(true);
            }

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
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
