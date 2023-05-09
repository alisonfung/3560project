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

import static PSS.ScheduleController.*;
import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class CreateTaskController {
    private Stage stage;
    private Scene scene;
    private Parent root;
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
    @FXML private Button createButton;
    @FXML private DialogPane createTaskDialog;
    private String[] transientTaskTypes = {"Visit", "Shopping", "Appointment"};
    private String[] recurringTaskTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"};
    private String[] antiTaskTypes = {"Cancellation"};
    private String[] minutes = {"00", "15", "30", "45"};
    private String[] AMPM = {"AM", "PM"};
    private String[] minute = {"0", "15", "30", "45"};
    private String[] frequency = {"Daily", "Weekly"};

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

    public void showDialogue(String title, String content){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    public void createTask(ActionEvent event){
        if (recurringButton.isSelected()) {
            System.out.println("Task Class: " + recurringButton.getText());
            //TODO: pass parameters
            if (createRecurringTask() == true){
                showDialogue("Success", "Task successfully created.");
            } else {
                showDialogue("Error", "Task was not created.");
            }
        } else if (transientButton.isSelected()) {
            System.out.println("Task Class: " + transientButton.getText());
            //TODO: pass parameters
            if (createTransientTask() == true){
                showDialogue("Success", "Task successfully created.");
            } else {
                showDialogue("Error", "Task was not created.");
            }
        } else if (antiButton.isSelected()) {
            System.out.println("Task Class: " + antiButton.getText());
            //TODO: pass parameters
            if (createAntiTask() == true){
                showDialogue("Success", "Task successfully created.");
            } else {
                showDialogue("Error", "Task was not created.");
            }
        }
        System.out.println("Name: " + taskNameTextField.getText());
        System.out.println("Type: " + taskTypeChoiceBox.getValue());
        LocalDate startDate = startDatePicker.getValue();
        String formattedStartDate = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("Start Date: " + formattedStartDate);
        System.out.println("Time: " + startTimeHourSpinner.getValue() + ":" + startTimeMinuteChoiceBox.getValue() + " " + startTimeAMPMChoiceBox.getValue());
        System.out.println("Duration: " + durationHourSpinner.getValue() + ":" + durationChoiceBox.getValue());
        if (recurringButton.isSelected()) {
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
}
