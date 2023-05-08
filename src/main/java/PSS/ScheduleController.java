package PSS;

import java.io.IOException;
import java.io.*;
import java.util.*;

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
    public static Tasks findTask(String name){
        Tasks exampleTask = new Tasks("task", 0f, 0f, 0);
        return exampleTask;
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
            System.out.println("File Exists");
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(filename));
                JSONObject jsonObject = (JSONObject)obj;
                Scanner scan = new Scanner(filename);
                System.out.print((String)jsonObject.get(scan.nextLine())); // read each line one by one
                // String name = (String)jsonObject.get("Name"); example code to get text from file
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) { // ParseException should throw an error if the file is not json type/format
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // check for errors in the file after reading (tasks cannot overlap, or have invalid/inconsistent details)
        }
        else {
            System.out.println("File Does Not Exist");
        }
    }

    public static void readSchedule (Schedule filename) {

    }
}
