package PSS;
import javafx.concurrent.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.*;

import static PSS.PSSInterface.schedule;

public class Schedule {
    private HashMap<Integer, Vector<Tasks>> DatesMap;
    private Vector<RecurringTasks> RecurringVector;

    public Schedule() {
        DatesMap = new HashMap<>();
        RecurringVector = new Vector<>();
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
            //System.out.println(occurrence.getJavaStartDate());
            //System.out.println(occurrence.getJavaEndDate());
            addToMap(occurrence, occurrence.getStartDate());
        }

        return exampleTask;
    }

    public AntiTasks createAntiTask(AntiTasks exampleTask)
    {
        int startDate = exampleTask.getStartDate();
        float startTime = exampleTask.getStartTime();
        float duration = exampleTask.getDuration();
        Vector<Tasks> list = DatesMap.get(startDate);
        for(int count = 0; count < list.size(); count++)
        {
            Tasks currentTask = list.get(count);
            if(currentTask instanceof RecurringTasksOccurrence && Float.compare(startTime, currentTask.getStartTime()) == 0 && Float.compare(duration, currentTask.getDuration()) == 0)
            {
                ((RecurringTasksOccurrence) currentTask).setAntiTask(exampleTask);
                exampleTask.setRecurringOccurrence(((RecurringTasksOccurrence) currentTask));
                //System.out.println("Anti-Task linked");
            }
        }
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


    public boolean editRecurringTask(String searchName, String name, String type, Float startTime, Float duration, int startDate, int endDate, int frequency) {
        Tasks task = findTask(searchName);
        if (task != null && task instanceof RecurringTasks) {
            deleteTask(searchName);
            RecurringTasks newTask = new RecurringTasks(name, type, startTime, duration, startDate, endDate, frequency);
            createRecurringTask(newTask);
            return true;
        }
        return false;
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
        // Search in RecurringVector
        for (RecurringTasks recurringTask : RecurringVector) {
            if (recurringTask.getName().equals(name)) {
                return recurringTask;
            }
        }

        // Search in DatesMap
        for (Vector<Tasks> taskVector : DatesMap.values()) {
            for (Tasks task : taskVector) {
                if (task.getName().equals(name)) {
                    return task;
                }
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
                if (task instanceof RecurringTasksOccurrence && ((RecurringTasksOccurrence) task).getAntiTask() != null){
                    AntiTasks antiTask = ((RecurringTasksOccurrence) task).getAntiTask();
                    System.out.println("Associated Anti-Task: " + antiTask.getName());

                }
                System.out.println();
            }
        }

        // Output tasks in RecurringVector
        System.out.println("Recurring Tasks:");
        for (RecurringTasks recurringTask : RecurringVector) {
            System.out.println("Task: " + recurringTask.getName());
            System.out.println("Task: " + recurringTask.getType());
            System.out.println("Start Time: " + recurringTask.getStartTime());
            System.out.println("Duration: " + recurringTask.getDuration());
            System.out.println("Start Date: " + recurringTask.getStartDate());
            System.out.println("End Date: " + recurringTask.getEndDate());
            System.out.println("Frequency: " + recurringTask.getFrequency());
            System.out.println();
        }
    }

    public Vector<Tasks> getTaskList(Date startDate, Date endDate){
        // declare result vector
        Vector<Tasks> resultVector = new Vector<Tasks>();

        if (startDate.after(endDate)) {
            //System.out.println("Start date is after end date.");
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // declare a java calendar object and set the date to the start date:
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);

        // go to the day before first date:
        c.add(Calendar.DATE, -1);
        Date beforeDate = c.getTime();
        // convert beforeDate's date to an integer
        Integer dateBefore = Integer.parseInt(dateFormat.format(beforeDate));
        // get tasks on beforeDate
        Vector<Tasks> beforeDateVector = DatesMap.get(dateBefore);
        // if there are tasks on before date, check if any are in the range of firstDate
        if (beforeDateVector != null) {
            for (Tasks task : beforeDateVector) {
                if (task.getJavaEndDate().after(startDate)) {
                    resultVector.add(task);
                }
            }
        }
        c.setTime(startDate);
        Date currentDate = c.getTime();
        while(currentDate.compareTo(endDate) <= 0) {
            // getting all tasks from current date
            Integer newDate = Integer.parseInt(dateFormat.format(currentDate)); // get integer of that date
            // get from hashmap with key newDate
            Vector<Tasks> currentDateVector = DatesMap.get(newDate);
            // for each task in currentDate
            if (currentDateVector != null) {
                for (Tasks task : currentDateVector) {
                    //convert that task's start date and time into one java date
                    Date taskDate = task.getJavaStartDate();
                    if (((taskDate.after(startDate) && taskDate.before(endDate)) || taskDate.equals(startDate))) {
                        resultVector.add(task); // add task to result vector
                    }
                }
            }
            //Increments day
            c.add(Calendar.DATE, 1);
            currentDate= c.getTime();
        }
        return resultVector;

    }
}

