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
        launch();
    }
}