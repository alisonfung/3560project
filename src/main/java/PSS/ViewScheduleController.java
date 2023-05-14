package PSS;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewScheduleController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private DatePicker startDatePicker;
    @FXML private ChoiceBox<String> lengthChoiceBox;
    @FXML private Text scheduleText;
    private String[] length = {"Day", "Week", "Month"};
    @FXML public void initialize(){
        startDatePicker.setValue(LocalDate.now());
        lengthChoiceBox.getItems().addAll(length);
        lengthChoiceBox.setValue(length[0]);
    }

    public void viewSchedule(ActionEvent event) throws IOException {
        LocalDate startDate = startDatePicker.getValue();
        String formattedStartDate = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        Integer intStartDate = Integer.parseInt(formattedStartDate);
        System.out.println(intStartDate);
        // TODO: get TaskList
        //tasklist = ScheduleController.getTaskList();
        if (true){ // task list not null
            // TODO: display schedule
                scheduleText.setText("Schedule here");
        } else {
            showDialog("Error", "No tasks found..");
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
