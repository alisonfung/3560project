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

    public static boolean verifyTask(Tasks task){
        boolean pass = true;

//        check all common attributes
//
//        verify name - use findTask() and make sure it returns null
//
//        verify start time
        if ((task.getStartTime() < 0) || (task.getStartTime() > 23.75)){
            pass = false;
        }

        //verify duration of task
        if ((task.getDuration() < 0.25) || (task.getDuration() > 23.75)){
            pass = false;
        }

        //checks specific to task type
        if (task instanceof TransientTasks){
            //check overlap with transient or recurring tasks
        }

        if (task instanceof RecurringTasks){
            //verify end date
            if (task.getStartDate() >= ((RecurringTasks) task).getEndDate()){
                pass = false;
            }

            //verify frequency


            //calculate occurrence
//            int i = 0;
//            int f = ((RecurringTasks) task).getFrequency();

            //loop through frequency and for each iteration, create a recurring task with date set to startDate + frequency

            //check overlap with recurring or transient tasks

        }

        if (task instanceof AntiTasks) {
            //check overlap with transient or antitasks

            //check overlap with recurring tasks
        }
        return pass;
    }
    public static Tasks findTask(String name){
        Tasks exampleTask = new Tasks("task", "transient", 0f, 0f, 0);
        return exampleTask;
    }
    public static boolean deleteTask(String name){
        return true;
    }

    public static boolean writeSchedule(String filename){
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("Name", "Go to doctor's appointment"); // Test purposes
        jsonObject.put("Type", "Visit");
        /* Set<String> keyset = jsonObject.keySet(); // iterate to get the key/pair values -- work in progress
        Iterator<String> keys = keyset.iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
        } */
        try {
            FileWriter file = new FileWriter(filename);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) { // IOException thrown when reading/accessing files fails at any point
            e.printStackTrace();
        }
        System.out.print("JSON File Created: " + jsonObject); // test to print out in terminal
        return true;
    }

    public static boolean readSchedule (String filename) {
//        JSONParser parser = new JSONParser();
//        // check for errors in the file after reading (tasks cannot overlap, or have invalid/inconsistent details)
//        if (createAntiTask() == false || createRecurringTask() == false || createTransientTask() == false) {
//            System.out.print("Error. Tasks overlap or have invalid/inconsistent details");
//        }
//        else {
//            try {
//                Object obj = parser.parse(new FileReader(filename));
//                JSONObject jsonObject = (JSONObject)obj;
//                Set<String> keyset = jsonObject.keySet();
//                Iterator<String> keys = keyset.iterator();
//                while (keys.hasNext()) {
//                    String key = keys.next();
//                    Object value = jsonObject.get(key);
//                    System.out.println( key +" : " + value); // print statement to make sure it gets key/value correctly
//                }
//            } catch (FileNotFoundException e) { // FileNotFoundException should throw an error if file is not found in directory files/folder
//                e.printStackTrace();
//            } catch (ParseException e) { // ParseException should throw an error if the file is not json type/format
//                e.printStackTrace();
//            } catch (IOException e) { // IOException thrown when reading/accessing files fails at any point
//                e.printStackTrace();
//            }
//
//        }
        return true;
    }

}
