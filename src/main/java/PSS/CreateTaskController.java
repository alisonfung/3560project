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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static PSS.PSSInterface.schedule;
import static PSS.ScheduleController.*;

public class CreateTaskController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField taskNameTextField;
    @FXML
    private VBox recurringOptionsBox;
    @FXML
    private RadioButton transientButton, recurringButton, antiButton;
    @FXML
    private ChoiceBox<String> taskTypeChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Spinner startTimeHourSpinner;
    @FXML
    private ChoiceBox<String> startTimeMinuteChoiceBox;
    @FXML
    private ChoiceBox<String> startTimeAMPMChoiceBox;
    @FXML
    private Spinner durationHourSpinner;
    @FXML
    private ChoiceBox<String> durationChoiceBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<String> frequencyChoiceBox;
    @FXML
    private Button createButton;
    private final String[] transientTaskTypes = {"Visit", "Shopping", "Appointment"};
    private final String[] recurringTaskTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"};
    private final String[] antiTaskTypes = {"Cancellation"};
    private final String[] minutes = {"00", "15", "30", "45"};
    private final String[] AMPM = {"AM", "PM"};
    private final String[] minute = {"0", "15", "30", "45"};
    private final String[] frequency = {"Daily", "Weekly"};

    /**
     * Initializes all input boxes to default values. Called upon scene load.
     */
    @FXML
    public void initialize() {
        taskNameTextField.setText("Task");
        // disable create button if task name input is empty
        createButton.disableProperty().bind(
                Bindings.isEmpty(taskNameTextField.textProperty()));
        taskTypeChoiceBox.getItems().addAll(transientTaskTypes);
        taskTypeChoiceBox.setValue(transientTaskTypes[0]);
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
    /**
     * Updates what input boxes and values are available according to the type of task.
     * Called when a different type of task is selected by the radio buttons.
     */
    public void displayTaskAttributes(ActionEvent event) {
        if (recurringButton.isSelected()) {
            recurringOptionsBox.setVisible(true);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(recurringTaskTypes);
            taskTypeChoiceBox.setValue(recurringTaskTypes[0]);
        } else if (transientButton.isSelected()) {
            recurringOptionsBox.setVisible(false);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(transientTaskTypes);
            taskTypeChoiceBox.setValue(transientTaskTypes[0]);
        } else if (antiButton.isSelected()) {
            recurringOptionsBox.setVisible(false);
            taskTypeChoiceBox.getItems().removeAll(taskTypeChoiceBox.getItems());
            taskTypeChoiceBox.getItems().addAll(antiTaskTypes);
            taskTypeChoiceBox.setValue(antiTaskTypes[0]);
        }
    }

    /**
     * Shows a dialog box informing user if action was successful.
     * @param title title of the dialog
     * @param content text of the dialog
     */
    public void showDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    /**
     * Gets all input values and creates a task.
     * Called when create button is clicked.
     */
    public void createTask(ActionEvent event) {
        String name = taskNameTextField.getText();
        String type = taskTypeChoiceBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        int formattedStartDate = Integer.parseInt(startDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        // convert the startTime to the appropriate float
        float startTime = (float) (int) startTimeHourSpinner.getValue();
        if (startTimeAMPMChoiceBox.getValue().equals("PM") && startTime < 12) {
            startTime += 12f;
        } else if (startTimeAMPMChoiceBox.getValue().equals("AM") && startTime == 12){
            startTime -= 12f;
        }
        if (startTimeMinuteChoiceBox.getValue().equals("15")) {
            startTime += 0.25f;
        } else if (startTimeMinuteChoiceBox.getValue().equals("30")) {
            startTime += 0.50f;
        } else if (startTimeMinuteChoiceBox.getValue().equals("45")) {
            startTime += 0.75f;
        }
        // convert the duration to the appropriate float
        float duration = (float) (int) durationHourSpinner.getValue();
        if (durationChoiceBox.getValue().equals("15")) {
            duration += 0.25f;
        } else if (durationChoiceBox.getValue().equals("30")) {
            duration += 0.50f;
        } else if (durationChoiceBox.getValue().equals("45")) {
            duration += 0.75f;
        }
        // create task based on type
        if (recurringButton.isSelected()) {
            // get additional recurring task input values
            LocalDate endDate = endDatePicker.getValue();
            int formattedEndDate = Integer.parseInt(endDate.format(DateTimeFormatter.BASIC_ISO_DATE));
            int frequency = 1;
            if (frequencyChoiceBox.getValue().equals("Weekly")){
                frequency = 7;
            }
            // show user dialog if successful
            if (createRecurringTask(name, type, startTime, duration, formattedStartDate, formattedEndDate, frequency)){
                showDialog("Success", "Task successfully created.");
                schedule.outputSchedule();
            } else {
                showDialog("Error", "Task was not created. One or more inputs are invalid.");
            }
        } else if (transientButton.isSelected()) {
            // show user dialog if successful
            if (createTransientTask(name, type, startTime, duration, formattedStartDate)) {
                showDialog("Success", "Task successfully created.");
                schedule.outputSchedule();
            } else {
                showDialog("Error", "Task was not created. One or more inputs are invalid");
            }
        } else if (antiButton.isSelected()) {
            System.out.println("Task Class: " + antiButton.getText());
            if (createAntiTask(name, type, startTime, duration, formattedStartDate)) {
                showDialog("Success", "Task successfully created.");
                schedule.outputSchedule();
            } else {
                showDialog("Error", "Task was not created. One or more inputs are invalid");
            }
        }
    }

    /**
     * Switches scene to home screen. Called when back button is clicked.
     */
    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
