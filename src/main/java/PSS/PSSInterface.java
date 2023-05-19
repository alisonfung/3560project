package PSS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class PSSInterface extends Application {
    public static Schedule schedule;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PSSInterface.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PSS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        schedule = new Schedule();
//        ScheduleController controllerSchedule = new ScheduleController();
//        controllerSchedule.createTransientTask("Eating Breakfast", "Appointment", 10.10f, 1.0f, 20230721);
//        controllerSchedule.createTransientTask("Eating Lunch", "Appointment",10.20f, 2.0f, 20220721);
//        controllerSchedule.createTransientTask("Eating Dinner", "Appointment",10.90f, 3.0f, 20210721);
//        controllerSchedule.createRecurringTask("Study", "Class", 2.0f, 10.0f, 20230508, 20230601, 7);
//        controllerSchedule.createAntiTask("Don't Study", "Class", 2.0f, 10.0f, 20230515);
//        controllerSchedule.writeSchedule("writeTest.json", 20230501, "month");
        launch();
    }
}