package PSS;

import java.util.Vector;

public class PSS {
    public static void main(String args[])
    {
        // Creating a test schedule
        Schedule firstSchedule = new Schedule();
        firstSchedule.createTransientTask("Eating Breakfast", 10.25f, 1.0f, 20230721);
        firstSchedule.createTransientTask("Eating Lunch", 10.50f, 2.0f, 20220721);
        firstSchedule.createTransientTask("Eating Dinner", 10.75f, 3.0f, 20210721);
        firstSchedule.createRecurringTask("Study", 20230508, 10.0f, 2.0f, 20230601, 7);

        // Uncomment to test find, delete, and edit
        /*
        // Testing searching by name
        String taskName = "Eating Dinner";
        Tasks foundTask = firstSchedule.findTask(taskName);

        if (foundTask != null) {
            // Task found, print its information
            System.out.println("Task found: " + foundTask.getName());
            // Print other task properties
            System.out.println("Start Time: " + foundTask.getStartTime());
            System.out.println("Duration: " + foundTask.getDuration());
            System.out.println("Start Date: " + foundTask.getStartDate());
            System.out.println();
        } else {
            // Task not found
            System.out.println("Task not found");
        }

        // Testing deleting by name
        String deleteTaskName = "Eating Lunch";
        boolean deleted = firstSchedule.deleteTask(deleteTaskName);

        if (deleted) {
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Task not found or failed to delete");
        }

        Tasks foundDeletedTask = firstSchedule.findTask(deleteTaskName);

        // Trying to find the deleted task
        if (foundDeletedTask != null) {
            System.out.println("Task found: " + foundDeletedTask.getName());
            System.out.println("Start Time: " + foundDeletedTask.getStartTime());
            System.out.println("Duration: " + foundDeletedTask.getDuration());
            System.out.println("Start Date: " + foundDeletedTask.getStartDate());
            System.out.println();
        } else {
            // Task not found
            System.out.println("Task not found");
        }

        // Testing editing a task
        String oldTaskName = "Eating Breakfast";
        String newTaskName = "Taking a walk";
        String type = "test";
        float newStartTime = 9.0f;
        float newDuration = 2.5f;
        int startDate = 20230508;

        boolean success = firstSchedule.editTransientTask(oldTaskName, newTaskName, type, newStartTime, newDuration, startDate);

        if (success) {
            System.out.println("Transient task updated successfully");
        } else {
            System.out.println("Failed to update transient task");
        }

        Tasks foundEditedTask = firstSchedule.findTask(newTaskName);

        // Outputting the values edited task
        if (foundEditedTask != null) {
            System.out.println("Task found: " + foundEditedTask.getName());
            System.out.println("Start Time: " + foundEditedTask.getStartTime());
            System.out.println("Duration: " + foundEditedTask.getDuration());
            System.out.println("Start Date: " + foundEditedTask.getStartDate());
            System.out.println();
        } else {
            // Task not found
            System.out.println("Task not found");
        }
         */
    }
}