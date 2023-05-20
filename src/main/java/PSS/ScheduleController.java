package PSS;

import java.io.IOException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONException;
import org.json.simple.parser.*;
import org.json.JSONArray;
import org.json.JSONObject;


import static PSS.PSSInterface.schedule;

public class ScheduleController {

    private static boolean checkForOccurrence(AntiTasks antiTask)
    {
        Date date = antiTask.getJavaStartDate();
        float startTime = antiTask.getStartTime();
        float duration = antiTask.getDuration();
        Vector<Tasks> list = schedule.getTaskList(date, date);
        for(int count = 0; count < list.size(); count++) {
            Tasks currentTask = list.get(count);
            if (currentTask instanceof RecurringTasksOccurrence && Float.compare(startTime, currentTask.getStartTime()) == 0 && Float.compare(duration, currentTask.getDuration()) == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean createTransientTask(String name, String type, Float startTime,
                                              Float duration, int startDate){
        TransientTasks newTask = new TransientTasks(name, type, startTime, duration, startDate);
        if (verifyTask(newTask)){
            schedule.createTransientTask(newTask);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean createRecurringTask(String name, String type, Float startTime,
                                              Float duration, int startDate,
                                              int endDate, int frequency){
        RecurringTasks newTask = new RecurringTasks(name, type, startTime, duration, startDate, endDate, frequency);
        if(verifyTask(newTask)){
            schedule.createRecurringTask(newTask);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean createAntiTask(String name, String type, Float startTime,
                                         Float duration, int startDate){
        AntiTasks newTask = new AntiTasks(name, type, startTime, duration, startDate);
        if(verifyTask(newTask)){
            AntiTasks exampleAntiTask = schedule.createAntiTask(newTask);
            return true;
        }
        else{
            return false;
        }
    }


    public static boolean editTransientTask(String searchName, String name, String type, Float startTime, Float duration, int startDate) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof TransientTasks) {
            TransientTasks transientTask = (TransientTasks) task;
            int currentStartDate = transientTask.getStartDate();

            // Store the original task
            TransientTasks originalTask = new TransientTasks(transientTask.getName(), transientTask.getType(), transientTask.getStartTime(), transientTask.getDuration(), transientTask.getStartDate());

            // Delete task and recreate with new details
            deleteTask(searchName);
            boolean createSuccess = createTransientTask(name, type, startTime, duration, startDate);

            if (!createSuccess) {
                // If creation fails, restore the original task
                createTransientTask(originalTask.getName(), originalTask.getType(), originalTask.getStartTime(), originalTask.getDuration(), originalTask.getStartDate());
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean editRecurringTask(String searchName, String name, String type, Float startTime, Float duration, int startDate, int endDate, int frequency) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof RecurringTasks) {
            RecurringTasks originalTask = (RecurringTasks) task;

            deleteTask(searchName);
            boolean createSuccess = createRecurringTask(name, type, startTime, duration, startDate, endDate, frequency);

            if (!createSuccess) {
                // If creation fails, restore the original task
                schedule.createRecurringTask(originalTask);
                return false;
            }

            return true;
        }
        return false;
    }



    public static boolean editAntiTask(String searchName, String name, String type, Float startTime, Float duration, int startDate) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof AntiTasks) {
            AntiTasks antiTask = (AntiTasks) task;
            int currentStartDate = antiTask.getStartDate();

            // Store the original task
            AntiTasks originalTask = new AntiTasks(antiTask.getName(), antiTask.getType(), antiTask.getStartTime(), antiTask.getDuration(), antiTask.getStartDate());

            // Delete anti task and create new anti task with new variables
            deleteTask(searchName);
            boolean createSuccess = createAntiTask(name, type, startTime, duration, startDate);

            if (!createSuccess) {
                // If creation fails, restore the original task
                createAntiTask(originalTask.getName(), originalTask.getType(), originalTask.getStartTime(), originalTask.getDuration(), originalTask.getStartDate());
                return false;
            }
            return true;
        }
        return false;
    }



    public static boolean verifyTask(Tasks task){
        //check all common attributes

        //check for duplicate task name
        if (findTask(task.getName()) != null){
            return false;
        }

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
            Vector<Tasks> taskList = schedule.getTaskList(task.getJavaStartDate(), task.getJavaEndDate());

            if (taskList.size() > 0){
                for (Tasks t : taskList){
                    if (t instanceof AntiTasks){
                        continue;
                    }
                    else if (t instanceof TransientTasks){
                        return false;
                    }
                    else if (t instanceof RecurringTasksOccurrence){
                        RecurringTasksOccurrence firstElem = (RecurringTasksOccurrence) taskList.firstElement();
                        if (firstElem.getAntiTask() == null) {
                            return false;
                        }
                    }

                }

            }

            //verify type
            if (task.getType().equals("Shopping") || task.getType().equals("Appointment") || task.getType().equals("Visit")){
                return true;
            }
            else{
                return false;
            }

        }

        if (task instanceof RecurringTasks){
            //verify end date
            if (task.getJavaStartDate().after(((RecurringTasks) task).getJavaEndDate())){
                System.out.println("invalid end date");
                return false;
            }

            //check for frequency
            if (((RecurringTasks) task).getFrequency() != 1 && ((RecurringTasks) task).getFrequency() != 7){
                return false;
            }

            //check overlap with other tasks
            Vector<RecurringTasksOccurrence> recurTaskList = createRecurringOccurrences((RecurringTasks) task);

            for (RecurringTasksOccurrence r : recurTaskList){
                //check overlap with transient or recurring tasks
                Vector<Tasks> taskList = schedule.getTaskList(task.getJavaStartDate(), task.getJavaEndDate());

                if (taskList.size() > 0){
                    for (Tasks t : taskList){
                        if (t instanceof AntiTasks){
                            continue;
                        }
                        else if (t instanceof TransientTasks){
                            return false;
                        }
                        else if (t instanceof RecurringTasksOccurrence){
                            RecurringTasksOccurrence firstElem = (RecurringTasksOccurrence) taskList.firstElement();
                            if (firstElem.getAntiTask() == null) {
                                return false;
                            }
                        }

                    }

                }
            }

            //verify type
            if (task.getType().equals("Class") || task.getType().equals("Study") || task.getType().equals("Sleep") || task.getType().equals("Exercise") || task.getType().equals("Work") || task.getType().equals("Meal")) {
                return true;
            }
            else {
                return false;
            }
        }

        if (task instanceof AntiTasks) {

            AntiTasks antiTask = (AntiTasks) task;
            //check overlap with transient or antitasks
            //check overlap with recurring tasks
            if (checkForOccurrence(antiTask) == false){
                return false;
            }

            //verify type
            if (task.getType().equals("Cancellation")){
                return true;
            }
            else {
                return false;
            }
        }
        System.out.println("all checks passed");
        return true;
    }

    private static Vector<RecurringTasksOccurrence> createRecurringOccurrences(RecurringTasks recurringTask) {
        Vector<RecurringTasksOccurrence> tasksOccur = new Vector<>();
        //Initializes all fields of the class

        Date firstDate;
        Date lastDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        try {
            //Converting startDate & endDate to Date type
            firstDate = dateFormat.parse(Integer.toString(recurringTask.getStartDate()));
            lastDate = dateFormat.parse(Integer.toString(recurringTask.getEndDate()));
            c.setTime(firstDate);
            //Creating RecurringTaskOccurences until firstDate is greater than lastDate
            while(firstDate.compareTo(lastDate) <= 0)
            {
                //Parses integer date from Java Date
                Integer newDate = Integer.parseInt(dateFormat.format(firstDate));

                RecurringTasksOccurrence occurrence = new RecurringTasksOccurrence(recurringTask.getName(), recurringTask.getType(), recurringTask.getStartTime(), recurringTask.getDuration(), newDate);
                tasksOccur.add(occurrence);

                //Increments number of days depending on frequency supplied
                c.add(Calendar.DATE, recurringTask.getFrequency());
                firstDate = c.getTime();
                //System.out.println(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tasksOccur;
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
