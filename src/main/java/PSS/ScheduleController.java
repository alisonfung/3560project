package PSS;

import java.io.*;
import org.json.simple.parser.*;
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
        File file = new File(filename); // check for file with specified name exist
        if (file.exists()) {
            System.out.println("Exists");
            // need to check if file is json format before reading the file
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(filename));
                JSONObject jsonObject = (JSONObject)obj;
                String name = (String)jsonObject.get("Name"); // read each line one by one
                String course = (String)jsonObject.get("Course");
            } catch(Exception e) {
                e.printStackTrace();
            }
            // check for errors in the file after reading (tasks cannot overlap, or have invalid/inconsistent details)
        }
        else {
            System.out.println("Does not Exists");
        }
    }
}
