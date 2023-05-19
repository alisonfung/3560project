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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import static PSS.PSSInterface.schedule;

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

    public void viewSchedule(ActionEvent event) throws IOException, ParseException {
        String chosenLength = lengthChoiceBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        String formattedStartDate = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dateFormat.parse(formattedStartDate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Calculate the endDate based on day, week or month
        if (chosenLength.equals("Day")) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        else if (chosenLength.equals("Week")) {
            c.add(Calendar.DAY_OF_MONTH, 7);
        }
        else if (chosenLength.equals("Month")) {
            c.add(Calendar.MONTH, 1);
        }
        Date endDate = c.getTime();
        Vector<Tasks> taskVector = schedule.getTaskList(date, endDate);
        if (taskVector != null){ // task list not null
            String scheduleTextSet = "Scheduled Tasks:\n================\n";

            // Output tasks sorted by date
            for (Tasks task : taskVector) {
                // skip anti tasks, skip cancelled recurring instances
                if (task instanceof AntiTasks) {
                    continue;
                } else if (task instanceof RecurringTasksOccurrence && ((RecurringTasksOccurrence) task).getAntiTask() != null){
                    continue;
                }
                scheduleTextSet += "Task: " + task.getName() + "\n";
                scheduleTextSet += "Type: " + task.getType() + "\n";
                scheduleTextSet += "Start Time: " + task.getStartTime() + "\n";
                scheduleTextSet += "Duration: " + task.getDuration() + "\n";
                scheduleTextSet += "Start Date: " + task.getStartDate() + "\n";
                scheduleTextSet += "\n";
            }

            // TODO: display schedule
                scheduleText.setText(scheduleTextSet);

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
