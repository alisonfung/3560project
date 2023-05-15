package PSS;
import javafx.concurrent.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.*;

public class Schedule {
    private HashMap<Integer, Vector<Tasks>> DatesMap;
    private Vector<RecurringTasks> RecurringVector;
    private Vector<Tasks> ListofTasksVector;

    public Schedule() {
        DatesMap = new HashMap<>();
        RecurringVector = new Vector<>();
        ListofTasksVector = new Vector<>();
    }

    private boolean addToMap(Tasks exampleTask, int startDate) {
        if (DatesMap.containsKey(startDate)) {
            Vector<Tasks> day = DatesMap.get(startDate);
            day.add(exampleTask);
        } else {
            Vector<Tasks> day = new Vector<>();
            day.add(exampleTask);
            DatesMap.put(startDate, day);
        }
        return true;
    }

    public TransientTasks createTransientTask(TransientTasks exampleTask) {
        int startDate = exampleTask.getStartDate();
        //Delete Later
        addToMap(exampleTask, startDate);
        return exampleTask;
    }

    public RecurringTasks createRecurringTask(RecurringTasks exampleTask) {
        RecurringVector.add(exampleTask);
        Vector<RecurringTasksOccurrence> listOfOccurrences = exampleTask.getListOfOccurrences();

        //Takes occurrences made in RecurringTask object are inserted into DateMap
        for(int counter = 0; counter < listOfOccurrences.size(); counter++)
        {
            Tasks occurrence = listOfOccurrences.get(counter);
            System.out.println(occurrence.getJavaStartDate());
            System.out.println(occurrence.getJavaEndDate());
            addToMap(occurrence, occurrence.getStartDate());
        }

        return exampleTask;
    }

    public AntiTasks createAntiTask(AntiTasks exampleTask)
    {
        int startDate = exampleTask.getStartDate();
        addToMap(exampleTask, startDate);
        return exampleTask;
    }

    public boolean editTransientTask(String searchName, String name, String type, Float startTime, Float duration, int startDate) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof TransientTasks) {
            task.updateTask(name, type, startTime, duration, startDate);
            return true;
        }
        return false;
    }


    public boolean editRecurringTask(String searchName, String name, String type, int startDate, Float startTime, Float duration, int endDate, int frequency) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof RecurringTasks) {
            task.updateTask(name, type, startTime, duration, startDate);
            RecurringTasks recurringTask = (RecurringTasks) task;
            recurringTask.updateTask(name, type, startDate, startTime, duration, endDate, frequency);
            return true;
        }
        return false;
    }

    public boolean setAntiTaskFlag(boolean flag) {
        return true;
    }

    public boolean deleteTask(String name) {
        boolean deleted = false;

        // Delete tasks in DatesMap
        for (Vector<Tasks> taskVector : DatesMap.values()) {
            for (Iterator<Tasks> iterator = taskVector.iterator(); iterator.hasNext(); ) {
                Tasks task = iterator.next();
                if (task.getName().equals(name)) {
                    iterator.remove();
                    deleted = true;
                }
            }
        }

        // Search in RecurringVector
        for (Tasks task : RecurringVector) {
            if (task.getName().equals(name)) {
                RecurringVector.remove(task);
                return true;
            }
        }

        return deleted;
    }


    public Tasks findTask(String name) {
        // Search in DatesMap
        for (Vector<Tasks> taskVector : DatesMap.values()) {
            for (Tasks task : taskVector) {
                if (task.getName().equals(name)) {
                    return task;
                }
            }
        }

        // Search in RecurringVector
        for (RecurringTasks recurringTask : RecurringVector) {
            if (recurringTask.getName().equals(name)) {
                return recurringTask;
            }
        }

        // Task not found
        return null;
    }

    // Purely for testing purposes to output the whole schedule, remove in final
    public void outputSchedule() {
        System.out.println("Scheduled Tasks:");
        System.out.println("================");

        // Output tasks in DatesMap
        for (Vector<Tasks> taskVector : DatesMap.values()) {
            for (Tasks task : taskVector) {
                System.out.println("Task: " + task.getName());
                System.out.println("Type: " + task.getType());
                System.out.println("Start Time: " + task.getStartTime());
                System.out.println("Duration: " + task.getDuration());
                System.out.println("Start Date: " + task.getStartDate());
                System.out.println();
            }
        }

        // Output tasks in RecurringVector
        System.out.println("Recurring Tasks:");
        for (RecurringTasks recurringTask : RecurringVector) {
            System.out.println("Task: " + recurringTask.getName());
            System.out.println("Start Time: " + recurringTask.getStartTime());
            System.out.println("Duration: " + recurringTask.getDuration());
            System.out.println("Start Date: " + recurringTask.getStartDate());
            System.out.println("End Date: " + recurringTask.getEndDate());
            System.out.println("Frequency: " + recurringTask.getFrequency());
            System.out.println();
        }
    }

    public Vector<Vector<Tasks>> getTaskList(int startDate, int endDate, float startTime,
                                             float endTime) {
        Vector<Vector<Tasks>> exampleVector = new Vector<Vector<Tasks>>();
        Date firstDate;
        Date lastDate;
        Date firstTime;
        Date lastTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat( "HH.mm");
//        try {
//            firstDate = dateFormat.parse(Integer.toString(startDate)); //convert date & time to Java date & time
//            lastDate = dateFormat.parse(Integer.toString(endDate));
//            firstTime = timeFormat.parse(Float.toString(startTime));
//            lastTime = timeFormat.parse(Float.toString(endTime));
//
//            for (Tasks tasks : ListofTasksVector) {
//                Date taskDate = tasks.getDate();
//                if (taskDate.after(firstDate) && taskDate.before(lastDate)) { // check between the range of dates
//                    if (taskDate.after(firstTime) && taskDate.before(lastTime)) { // check between the range of times
//                        return exampleVector;
//                    }
//                }
//            }
//        }
//        catch (ParseException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}

