package PSS;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static PSS.ScheduleController.*;

public class EditTaskEditorController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Tasks editedTask;
    @FXML private Text editTaskTitle;
    @FXML private TextField taskNameTextField;
    @FXML private VBox recurringOptionsBox;
    @FXML private RadioButton transientButton, recurringButton, antiButton;
    @FXML private ChoiceBox<String> taskTypeChoiceBox;
    @FXML private DatePicker startDatePicker;
    @FXML private Spinner startTimeHourSpinner;
    @FXML private ChoiceBox<String> startTimeMinuteChoiceBox;
    @FXML private ChoiceBox<String> startTimeAMPMChoiceBox;
    @FXML private Spinner durationHourSpinner;
    @FXML private ChoiceBox<String> durationChoiceBox;
    @FXML private DatePicker endDatePicker;
    @FXML private ChoiceBox<String> frequencyChoiceBox;
    @FXML private Button editButton;
    @FXML private DialogPane createTaskDialog;
    private String[] transientTaskTypes = {"Visit", "Shopping", "Appointment"};
    private String[] recurringTaskTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"};
    private String[] antiTaskTypes = {"Cancellation"};
    private String[] minutes = {"00", "15", "30", "45"};
    private String[] AMPM = {"AM", "PM"};
    private String[] minute = {"0", "15", "30", "45"};
    private String[] frequency = {"Daily", "Weekly"};

    public void setTask(Tasks task){
        editedTask = task;
        System.out.println("success");
        setInputBoxes();
    }

    public void setInputBoxes(){
        //TODO: set title to task name using getter
        editTaskTitle.setText("Editing 'Task'");
        if (editedTask instanceof RecurringTasks){
            recurringOptionsBox.setVisible(true);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(recurringTaskTypes);
            taskTypeChoiceBox.setValue(recurringTaskTypes[0]);
        } else if (editedTask instanceof TransientTasks){
            recurringOptionsBox.setVisible(false);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(transientTaskTypes);
            taskTypeChoiceBox.setValue(transientTaskTypes[0]);
        } else { // TODO:  if (editedTask instanceof AntiTasks){
            recurringOptionsBox.setVisible(false);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(antiTaskTypes);
            taskTypeChoiceBox.setValue(antiTaskTypes[0]);
        }
        //TODO: use getters for task attributes instead of default values here
        taskNameTextField.setText("Task");
        editButton.disableProperty().bind(
                Bindings.isEmpty(taskNameTextField.textProperty()));
        startDatePicker.setValue(LocalDate.now());
        SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        SpinnerValueFactory<Integer> hour24Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        hourFactory.setValue(12);
        hour24Factory.setValue(1);
        startTimeHourSpinner.setValueFactory(hourFactory);
        startTimeMinuteChoiceBox.getItems().addAll(minutes);
        startTimeMinuteChoiceBox.setValue(minutes[0]);
        startTimeAMPMChoiceBox.getItems().addAll(AMPM);
        startTimeAMPMChoiceBox.setValue(AMPM[0]);
        durationHourSpinner.setValueFactory(hour24Factory);
        durationChoiceBox.getItems().addAll(minute);
        durationChoiceBox.setValue(minute[0]);
        endDatePicker.setValue(LocalDate.now());
        frequencyChoiceBox.getItems().addAll(frequency);
        frequencyChoiceBox.setValue(frequency[0]);
    }

    public void showDialog(String title, String content){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    public void editTask(ActionEvent event) throws IOException {
        if (editedTask instanceof RecurringTasks) {
            System.out.println("Task Class: Recurring");
            // TODO: pass parameters, change to edit
            if (createRecurringTask() == true){
                showDialog("Success", "Task successfully edited.");
                switchToEditTask(event);
            } else {
                showDialog("Error", "Task was not edited.");
            }
        } else if (editedTask instanceof TransientTasks) {
            System.out.println("Task Class: Transient");
            // TODO: pass parameters, change to edit
            if (createTransientTask() == true){
                showDialog("Success", "Task successfully edited.");
                switchToEditTask(event);
            } else {
                showDialog("Error", "Task was not edited.");
            }
        } else { // TODO: if (editedTask instance of AntiTasks
            System.out.println("Task Class: Anti");
            //TODO: pass parameters, change to edit
            if (createAntiTask() == true){
                showDialog("Success", "Task successfully edited.");
                switchToEditTask(event);
            } else {
                showDialog("Error", "Task was not edited.");
            }
        }
        System.out.println("Name: " + taskNameTextField.getText());
        System.out.println("Type: " + taskTypeChoiceBox.getValue());
        LocalDate startDate = startDatePicker.getValue();
        String formattedStartDate = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("Start Date: " + formattedStartDate);
        System.out.println("Time: " + startTimeHourSpinner.getValue() + ":" + startTimeMinuteChoiceBox.getValue() + " " + startTimeAMPMChoiceBox.getValue());
        System.out.println("Duration: " + durationHourSpinner.getValue() + ":" + durationChoiceBox.getValue());
        if (editedTask instanceof RecurringTasks) {
            LocalDate endDate = endDatePicker.getValue();
            String formattedEndDate = endDate.format(DateTimeFormatter.BASIC_ISO_DATE);
            System.out.println("End Date: " + formattedEndDate);
            System.out.println("Frequency: " + frequencyChoiceBox.getValue());
        }
    }
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEditTask(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("editTaskSearch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
