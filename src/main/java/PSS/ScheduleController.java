package PSS;

import java.io.IOException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONException;
import org.json.simple.parser.*;
import org.json.JSONArray;
import org.json.JSONObject;


import static PSS.PSSInterface.schedule;

public class ScheduleController {

    private static RecurringTasksOccurrence checkForOccurrence(AntiTasks antiTask)
    {
        Date date = antiTask.getJavaStartDate();
        RecurringTasksOccurrence exampleTask = new RecurringTasksOccurrence("", "", 0.0f, 0.0f, 0);
        Vector<Tasks> list = schedule.getTaskList(date, date);
        for(int count = 0; count < list.size(); count++)
        {
            Tasks currentTask = list.get(count);
            if(currentTask instanceof RecurringTasksOccurrence)
            {
                System.out.println(currentTask.getDuration());
                System.out.println(antiTask.getDuration());
            }
        }

        return exampleTask;
    }

    public static boolean createTransientTask(String name, String type, Float startTime,
                                              Float duration, int startDate){
        TransientTasks newTask = new TransientTasks(name, type, startTime, duration, startDate);
        // TODO: use verifyTask()
        //if (verifyTask(newTask)){
        //
        schedule.createTransientTask(newTask);
        return true;
        //} else return false;
    }

    public static boolean createRecurringTask(String name, String type, Float startTime,
                                              Float duration, int startDate,
                                              int endDate, int frequency){
        RecurringTasks newTask = new RecurringTasks(name, type, startTime, duration, startDate, endDate, frequency);
        // TODO: use verifyTask()
        // if(verifyTask(newTask){
        schedule.createRecurringTask(newTask);
        return true;
        //} else return false;
    }

    public static boolean createAntiTask(String name, String type, Float startTime,
                                         Float duration, int startDate){
        AntiTasks newTask = new AntiTasks(name, type, startTime, duration, startDate);
        // TODO: use verifyTask()
        if(verifyTask(newTask)){
            AntiTasks exampleAntiTask = schedule.createAntiTask(newTask);
            return true;
        }
        else{
            return false;
        }
    }

    /*public static boolean editTransientTask(String searchName, String name, String type, Float startTime,
                                            Float duration, int startDate){
        // TODO: verify edits
        return schedule.editTransientTask(searchName, name, type, startTime,
                duration, startDate);

    }*/

    public static boolean editTransientTask(String searchName, String name, String type, Float startTime, Float duration, int startDate) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof TransientTasks) {
            TransientTasks transientTask = (TransientTasks) task;
            int currentStartDate = transientTask.getStartDate();

            if (currentStartDate == startDate) {
                // If the start date is not changed, simply update the task details
                transientTask.updateTask(name, type, startTime, duration, startDate);
            } else {
                // Update the task details and add it to the new date vector
                deleteTask(searchName);
                createTransientTask(name, type, startTime, duration, startDate);
            }

            return true;
        }
        return false;
    }

    /*public static boolean editRecurringTask(String searchName, String name, String type,
                                            Float startTime, Float duration, int startDate,
                                            int endDate, int frequency){
        // TODO: verify edits
        return schedule.editRecurringTask(searchName, name, type, startTime,
                duration, startDate, endDate, frequency);

    }*/

    public static boolean editRecurringTask(String searchName, String name, String type, Float startTime, Float duration, int startDate, int endDate, int frequency) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof RecurringTasks) {
            deleteTask(searchName);
            RecurringTasks newTask = new RecurringTasks(name, type, startTime, duration, startDate, endDate, frequency);
            schedule.createRecurringTask(newTask);
            return true;
        }
        return false;
    }

    /*public static boolean editAntiTask(String searchName, String name, String type, Float startTime,
                                            Float duration, int startDate){
        // TODO: verify edits, add in schedule.editAntiTask
        //return schedule.editAntiTask(searchName, name, type, startTime, duration, startDate);
        return true;

    }*/

    public static boolean editAntiTask(String searchName, String name, String type, Float startTime, Float duration, int startDate) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof AntiTasks) {
            AntiTasks antiTask = (AntiTasks) task;
            int currentStartDate = antiTask.getStartDate();

            if (currentStartDate == startDate) {
                // If the start date is not changed, simply update the task details
                antiTask.updateTask(name, type, startTime, duration, startDate);
            } else {
                // Update the task details and add it to the new date vector
                deleteTask(searchName);
                createAntiTask(name, type, startTime, duration, startDate);
            }

            return true;
        }
        return false;
    }


    public static boolean verifyTask(Tasks task){
        //check all common attributes

        //verify start time
        if ((task.getStartTime() < 0) || (task.getStartTime() > 23.75)){
            System.out.println("invalid start time");
            return false;
        }

        //verify duration of task
        if ((task.getDuration() < 0.25) || (task.getDuration() > 23.75)){
            System.out.println("invalid duration");
            return false;
        }

       //checks specific to task type
        if (task instanceof TransientTasks){
            //check overlap with transient or recurring tasks

        }

        if (task instanceof RecurringTasks){
            //verify end date
            if (task.getStartDate() >= ((RecurringTasks) task).getEndDate()){
                System.out.println("invalid end date");
                return false;
            }

            //verify frequency here


            //calculate occurrence
//            int i = 0;
//            int f = ((RecurringTasks) task).getFrequency();

            //loop through frequency and for each iteration, create a recurring task with date set to startDate + frequency
            //check overlap with recurring or transient tasks

        }

        if (task instanceof AntiTasks) {
            AntiTasks antiTask = (AntiTasks) task;
            //check overlap with transient or antitasks
            //check overlap with recurring tasks
            if (checkForOccurrence(antiTask) == null){
                return false;
            }
        }
        System.out.println("all checks passed");
        return true;
    }
    public static Tasks findTask(String name){
        return schedule.findTask(name);
    }
    public static boolean deleteTask(String name) {
        Tasks task = schedule.findTask(name);
        if (task != null && task instanceof AntiTasks) {
            AntiTasks antiTask = (AntiTasks) task;
            Date startDate = antiTask.getJavaStartDate();
            Date endDate = antiTask.getJavaEndDate();
            Vector<Tasks> taskList = schedule.getTaskList(startDate, endDate);
            boolean hasOverlap = false;

            for (Tasks otherTask : taskList) {
                if (otherTask instanceof TransientTasks) {
                    Date taskStartDate = otherTask.getJavaStartDate();
                    Date taskEndDate = otherTask.getJavaEndDate();
                    if (startDate.before(taskEndDate) && endDate.after(taskStartDate)) {
                        hasOverlap = true;
                        break;
                    }
                }
                else if (otherTask instanceof RecurringTasksOccurrence) {
                    RecurringTasksOccurrence occurrence = (RecurringTasksOccurrence) otherTask;
                    if (occurrence.getAntiTask() == null) {
                        Date taskStartDate = otherTask.getJavaStartDate();
                        Date taskEndDate = otherTask.getJavaEndDate();
                        if (startDate.before(taskEndDate) && endDate.after(taskStartDate)) {
                            hasOverlap = true;
                            break;
                        }
                    }
                }
            }
            if (hasOverlap) {
                return false; // Abort deletion, as overlap exists
            }
        }
        return schedule.deleteTask(name);
    }


    public static boolean writeSchedule(String filename, int startDate, String type) {
        try {
            // check if the filename is valid
            String pattern = "^[\\w\\-. ]+$"; // Allow letters, numbers, underscores, hyphens, periods, and spaces
            if (!filename.matches(pattern)) {
                return false;
            }

            // Gets user startDate
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date currentDate = dateFormat.parse(String.valueOf(startDate));
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            // Calculate the endDate based on day, week or month
            if (type.equalsIgnoreCase("day")) {
                c.add(Calendar.DAY_OF_MONTH, 1);
            }
            else if (type.equalsIgnoreCase("week")) {
                c.add(Calendar.DAY_OF_MONTH, 7);
            }
            else if (type.equalsIgnoreCase("month")) {
                c.add(Calendar.MONTH, 1);
            }
            else {
                throw new IllegalArgumentException("Invalid type. Must be day, week, or month.");
            }
            Date endDate = c.getTime();
            //System.out.println("Current Date:" + currentDate);  //test to see if it gets startDate & endDate based on day, week, or month
            //System.out.println("End Date:" + endDate);

            // call getTaskList for all tasks based on user's choice of day, week, or month
            Vector<Tasks> taskVector = schedule.getTaskList(currentDate, endDate);
            JSONArray jsonArray = new JSONArray(); // create a new JSON array

            for (Tasks task : taskVector) {
                // skip if the task is an anti-task
                if (task instanceof AntiTasks) {
                    continue;
                }

                // check if the task is a recurring task occurrence
                if (task instanceof RecurringTasksOccurrence) {
                    RecurringTasksOccurrence occurrence = (RecurringTasksOccurrence) task;

                    // check if the occurrence has an associated anti-task
                    if (occurrence.getAntiTask() != null) {
                        continue; // skip adding the occurrence to the array
                    }
                }

                // add tasks to array
                JSONObject jsonObject = new JSONObject(); // create a new JSON Object for each task
                jsonObject.put("Name", task.getName());
                jsonObject.put("Type", task.getType());
                jsonObject.put("Date", task.getStartDate());
                jsonObject.put("StartTime", task.getStartTime());
                jsonObject.put("Duration", task.getDuration());
                jsonArray.put(jsonObject);

                FileWriter fileWriter = new FileWriter(filename);
                fileWriter.write(jsonArray.toString()); // write tasks to JSON file
                fileWriter.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean readSchedule(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists() && !filename.matches(".*\\.json$")) {
                return false;
            }
            //System.out.println("file exists");
            // read the JSON file
            StringBuilder json = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
            //System.out.println("json file read");
            // parse the JSON array
            JSONArray jsonArray = new JSONArray(json.toString());

            // new vector to contain all new tasks
            Vector<Tasks> newTaskVector = new Vector<Tasks>();

            // loop to iterate over each task (JSON object) in JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i); // get object

                // if key type value is "Cancellation" - get values for AntiTask
                String type = jsonObject.optString("Type");
                if ("Cancellation".equals(type)) {
                    String name = jsonObject.optString("Name");
                    String taskType = jsonObject.optString("Type");
                    int date = jsonObject.optInt("Date");
                    double startTime = jsonObject.optDouble("StartTime");
                    double duration = jsonObject.optDouble("Duration");
                    if (name.isEmpty() || taskType.isEmpty() || date <= 0 ||
                            duration <= 0.0 || startTime < 0.0) {
                        cancelRead(newTaskVector);
                        schedule.outputSchedule();
                        return false;
                    }
                    float floatStartTime = (float) startTime;
                    float floatDuration = (float) duration;
                    boolean antiTaskCreated = ScheduleController.createAntiTask(name, taskType, floatStartTime, floatDuration, date);
                    if (antiTaskCreated) {
                        AntiTasks antiTasks = new AntiTasks(name, taskType, floatStartTime, floatDuration, date);
                        System.out.println("Schedule for AntiTasks starts here:");
                        schedule.outputSchedule();
                        newTaskVector.add(antiTasks);
                    }
                    else {
                        cancelRead(newTaskVector);
                        schedule.outputSchedule();
                        return false;
                    }
                }
                else if (jsonObject.has("EndDate") && jsonObject.has("Frequency")) {
                    String name = jsonObject.optString("Name");
                    String taskType = jsonObject.optString("Type");
                    int startDate = jsonObject.optInt("StartDate");
                    double startTime = jsonObject.optDouble("StartTime");
                    double duration = jsonObject.optDouble("Duration");
                    int endDate = jsonObject.optInt("EndDate");
                    int frequency = jsonObject.optInt("Frequency");
                    if (name.isEmpty() || taskType.isEmpty() || startDate <= 0 ||
                            startTime < 0.0 || duration <= 0.0 || endDate <= 0 || frequency <= 0) {
                        cancelRead(newTaskVector);
                        schedule.outputSchedule();
                        return false;
                    }
                    float floatStartTime = (float) startTime;
                    float floatDuration = (float) duration;
                    boolean recurringTaskCreated = ScheduleController.createRecurringTask(name, taskType, floatStartTime, floatDuration, startDate, endDate, frequency);
                    if (recurringTaskCreated) {
                        RecurringTasks recurringTasks = new RecurringTasks(name, taskType, floatStartTime, floatDuration, startDate, endDate, frequency);
                        schedule.outputSchedule();
                        newTaskVector.add(recurringTasks);
                    }
                    else {
                        cancelRead(newTaskVector);
                        schedule.outputSchedule();
                        return false;
                    }
                }
                else {
                    String name = jsonObject.optString("Name");
                    String taskType = jsonObject.optString("Type");
                    int date = jsonObject.optInt("Date");
                    double startTime = jsonObject.optDouble("StartTime");
                    double duration = jsonObject.optDouble("Duration");
                    if (name.isEmpty() || taskType.isEmpty() || date <= 0 ||
                            duration <= 0.0 || startTime < 0.0) {
                        cancelRead(newTaskVector);
                        schedule.outputSchedule();
                        return false;
                    }
                    float floatStartTime = (float) startTime;
                    float floatDuration = (float) duration;
                    boolean transientTaskCreated = ScheduleController.createTransientTask(name, taskType, floatStartTime, floatDuration, date);
                    if (transientTaskCreated) {
                        TransientTasks transientTasks = new TransientTasks(name, taskType, floatStartTime, floatDuration, date);
                        schedule.outputSchedule();
                        newTaskVector.add(transientTasks);
                    }
                    else {
                        cancelRead(newTaskVector);
                        schedule.outputSchedule();
                        return false;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private static void cancelRead(Vector<Tasks> newTaskVector) {
        // Loop through keeping track vector and delete tasks
        for (Tasks task : newTaskVector) {
            ScheduleController.deleteTask(task.getName());
        }
    }

}
