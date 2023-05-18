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

import static PSS.PSSInterface.schedule;
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
    private final String[] transientTaskTypes = {"Visit", "Shopping", "Appointment"};
    private final String[] recurringTaskTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"};
    private final String[] antiTaskTypes = {"Cancellation"};
    private final String[] minutes = {"00", "15", "30", "45"};
    private final String[] AMPM = {"AM", "PM"};
    private final String[] minute = {"0", "15", "30", "45"};
    private final String[] frequency = {"Daily", "Weekly"};

    public void setTask(Tasks task){
        editedTask = task;
        setInputBoxes();
    }

    public void setInputBoxes(){
        editTaskTitle.setText("Editing '" + editedTask.getName()+ "'");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // show recurring task input boxes if needed, show correct task types
        if (editedTask instanceof RecurringTasks){
            recurringOptionsBox.setVisible(true);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(recurringTaskTypes);
            // get end date and frequency
            String endDateString = Integer.toString(((RecurringTasks)editedTask).getEndDate());
            LocalDate endDate = LocalDate.parse(endDateString, formatter);
            endDatePicker.setValue(endDate);
            frequencyChoiceBox.getItems().addAll(frequency);
            int frequencyIndex = 0;
            if (((RecurringTasks) editedTask).getFrequency() == 7){
                frequencyIndex = 1;
            }
            frequencyChoiceBox.setValue(frequency[frequencyIndex]);
        } else if (editedTask instanceof TransientTasks){
            recurringOptionsBox.setVisible(false);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(transientTaskTypes);
        } else if (editedTask instanceof AntiTasks){
            recurringOptionsBox.setVisible(false);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(antiTaskTypes);
        }
        taskTypeChoiceBox.setValue(editedTask.getType());
        taskNameTextField.setText(editedTask.getName());
        editButton.disableProperty().bind(
                Bindings.isEmpty(taskNameTextField.textProperty()));
        String startDateString = Integer.toString(editedTask.getStartDate());
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        startDatePicker.setValue(startDate);
        // spinner for start time
        SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        // hours for duration
        SpinnerValueFactory<Integer> hour24Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        // set hours for start time
        int hours = (int)Math.floor(editedTask.getStartTime());
        // get float portion of startTime
        float startMinutes = editedTask.getStartTime() - hours;
        int AMPMindex = 0;
        // subtract 12 if needed, set PM if needed
        if (hours > 12){
            AMPMindex = 1;
            hours -= 12;
        }
        // set spinner to startTime hour, AMPM box
        hourFactory.setValue(hours);
        startTimeHourSpinner.setValueFactory(hourFactory);
        startTimeAMPMChoiceBox.getItems().addAll(AMPM);
        startTimeAMPMChoiceBox.setValue(AMPM[AMPMindex]);
        // set minutes for start time
        int startMinutesIndex = 0;
        if (startMinutes == 0.25f){
            startMinutesIndex = 1;
        } else if (startMinutes == 0.50f) {
            startMinutesIndex = 2;
        } else if (startMinutes == 0.75f) {
            startMinutesIndex = 3;
        }
        startTimeMinuteChoiceBox.getItems().addAll(minutes);
        startTimeMinuteChoiceBox.setValue(minutes[startMinutesIndex]);
        // set hours for duration
        int durationHours = (int)Math.floor(editedTask.getDuration());
        hour24Factory.setValue(durationHours);
        durationHourSpinner.setValueFactory(hour24Factory);
        // set minutes for duration
        float durationMinutes = editedTask.getStartTime() - hours;
        int durationMinutesIndex = 0;
        if (durationMinutes == 0.25f){
            durationMinutesIndex = 1;
        } else if (durationMinutes == 0.50f) {
            durationMinutesIndex = 2;
        } else if (durationMinutes == 0.75f) {
            durationMinutesIndex = 3;
        }
        durationChoiceBox.getItems().addAll(minute);
        durationChoiceBox.setValue(minute[durationMinutesIndex]);
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
        // get all input parameters
        String name = taskNameTextField.getText();
        String type = taskTypeChoiceBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        int formattedStartDate = Integer.parseInt(startDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        // convert the startTime to the appropriate float
        float startTime = (float) (int) startTimeHourSpinner.getValue();
        if (startTimeAMPMChoiceBox.getValue() == "PM") {
            startTime += 12f;
        }
        if (startTimeMinuteChoiceBox.getValue() == "15") {
            startTime += 0.25f;
        } else if (startTimeMinuteChoiceBox.getValue() == "30") {
            startTime += 0.50f;
        } else if (startTimeMinuteChoiceBox.getValue() == "45") {
            startTime += 0.75f;
        }
        // convert the duration to the appropriate float
        float duration = (float) (int) durationHourSpinner.getValue();
        if (durationChoiceBox.getValue() == "15") {
            duration += 0.25f;
        } else if (durationChoiceBox.getValue() == "30") {
            duration += 0.50f;
        } else if (durationChoiceBox.getValue() == "45") {
            duration += 0.75f;
        }
        if (editedTask instanceof RecurringTasks) {
            // get additional recurring task input values
            LocalDate endDate = endDatePicker.getValue();
            int formattedEndDate = Integer.parseInt(endDate.format(DateTimeFormatter.BASIC_ISO_DATE));
            int frequency = 1;
            if (frequencyChoiceBox.getValue() == "Weekly"){
                frequency = 7;
            }
            if (schedule.editRecurringTask(editedTask.getName(), name, type, startTime, duration, formattedStartDate, formattedEndDate, frequency)){
                showDialog("Success", "Task successfully edited.");
                switchToEditTask(event);
                schedule.outputSchedule();
            } else {
                showDialog("Error", "Task was not edited. One or more inputs are invalid");
            }
        } else if (editedTask instanceof TransientTasks) {
            if (schedule.editTransientTask(editedTask.getName(), name, type, startTime, duration, formattedStartDate)){
                showDialog("Success", "Task successfully edited.");
                switchToEditTask(event);
                schedule.outputSchedule();
            } else {
                showDialog("Error", "Task was not edited. One or more inputs are invalid");
            }
        } else if (editedTask instanceof AntiTasks){
            //TODO: pass parameters, change to edit
            if (true){
                showDialog("Success", "Task successfully edited.");
                switchToEditTask(event);
                schedule.outputSchedule();
            } else {
                showDialog("Error", "Task was not edited. One or more inputs are invalid");
            }
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
