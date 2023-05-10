package PSS;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Schedule {
    private HashMap<Integer, Vector<Tasks>> DatesMap;
    private Vector<RecurringTasks> RecurringVector;

    public Schedule() {
        DatesMap = new HashMap<>();
        RecurringVector = new Vector<>();
    }

    private boolean addToMap(Tasks exampleTask, int startDate)
    {
        if(DatesMap.containsKey(startDate))
        {
            Vector<Tasks> day = DatesMap.get(startDate);
            day.add(exampleTask);
        } else {
            Vector<Tasks> day = new Vector<>();
            day.add(exampleTask);
            DatesMap.put(startDate, day);
        }
        return true;
    }

    public TransientTasks createTransientTask(String name, Float startTime,
                                     Float duration, int startDate)
    {
        TransientTasks exampleTask = new TransientTasks(name, startTime, duration, startDate);
        addToMap(exampleTask, startDate);
        return exampleTask;
    }

    public RecurringTasks createRecurringTask(String name, int startDate,
                                       Float startTime, Float duration,
                                       int endDate, int frequency)
    {
        RecurringTasks exampleTask = new RecurringTasks(name, startTime, duration, startDate, endDate, frequency);
        RecurringVector.add(exampleTask);
        Vector<RecurringTasksOccurrence> listOfOccurences = exampleTask.getListOfOccurrences();

        for(int counter = 0; counter < listOfOccurences.size(); counter++)
        {
            Tasks occurrence = listOfOccurences.get(counter);
            addToMap(occurrence, occurrence.getStartDate());
        }

        return exampleTask;
    }

    public AntiTasks createAntiTask(String name, int startDate,
                                  Float startTime, Float duration)
    {
        AntiTasks exampleTask = new AntiTasks(name, startTime, duration, startDate);;
        return exampleTask;
    }

    public boolean editTransientTask(String searchName, String name, String type, Float startTime, Float duration, int startDate)
    {
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

    public boolean setAntiTaskFlag(boolean flag)
    {
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
                                             float endTime)
    {
        Vector<Vector<Tasks>> exampleVector = new Vector<Vector<Tasks>>();
        return exampleVector;
    }
}

