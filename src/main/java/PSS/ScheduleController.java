package PSS;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class ScheduleController {
    //TODO: create parameters
    public static boolean createTransientTask(){
        return true;
    }
    public static boolean createRecurringTask(){
        return true;
    }
    public static boolean createAntiTask(){
        return true;
    }

    public static void writeSchedule(String filename){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "value"); // need to insert the key and value names of tasks?
        try {
            FileWriter file = new FileWriter(filename);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(jsonObject);
    }

    public static void readSchedule (String filename) {

    }
}
