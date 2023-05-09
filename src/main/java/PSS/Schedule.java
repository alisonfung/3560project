package PSS;
import java.util.HashMap;
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

    public boolean createTransientTask(String name, Float startTime,
                                     Float duration, int startDate)
    {
        Tasks exampleTask = new TransientTasks(name, startTime, duration, startDate);
        addToMap(exampleTask, startDate);
        return true;
    }

    public boolean createRecurringTask(String name, int startDate,
                                       Float startTime, Float duration,
                                       int endDate, int frequency)
    {
        Tasks exampleTask = new RecurringTasks(name, startTime, duration, startDate, endDate, frequency);
        return true;
    }

    public boolean createAntiTask(String name, int date,
                                  Float startTime, Float duration)
    {
        return true;
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


    public boolean editRecurringTask(String name, String type, int startDate, Float startTime, Float duration, int endDate, int frequency) {
        Tasks task = findTask(name);
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
        // Search in DatesMap
        for (Vector<Tasks> taskVector : DatesMap.values()) {
            for (Tasks task : taskVector) {
                if (task.getName().equals(name)) {
                    taskVector.remove(task);
                    return true;
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

        // Task not found
        return false;
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

    public Vector<Vector<Tasks>> getTaskList(int startDate, int endDate, float startTime,
                                             float endTime)
    {
        Vector<Vector<Tasks>> exampleVector = new Vector<Vector<Tasks>>();
        return exampleVector;
    }
}

