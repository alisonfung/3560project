package PSS;

import org.json.simple.JSONObject;
import java.util.Date;
import java.util.Vector;

public class PSS {
    public static void main(String args[])
    {
        // Creating a test schedule
        Schedule firstSchedule = new Schedule();
        firstSchedule.createTransientTask("Eating Breakfast", "Appointment", 10.10f, 1.0f, 20230721);
        firstSchedule.createTransientTask("Eating Lunch", "Appointment",10.20f, 2.0f, 20220721);
        firstSchedule.createTransientTask("Eating Dinner", "Appointment",10.90f, 3.0f, 20210721);
        firstSchedule.createRecurringTask("Study", "Class",20230508, 10.0f, 2.0f, 20230601, 7);

        // Uncomment to test find, delete, and edit
        /*
        // Testing searching by name
        String taskName = "Study";
        Tasks foundTask = firstSchedule.findTask(taskName);
        firstSchedule.editRecurringTask("Study", "Studious", "recurring", 20230508, 10.0f, 2.0f, 20230601, 7);
        foundTask = firstSchedule.findTask(taskName);

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

        firstSchedule.outputSchedule();
        // Testing deleting by name
        String deleteTaskName = "Study";
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

        firstSchedule.outputSchedule();


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

//         Uncomment to test how info is getting written into/read into json

        /* // Test to see if writeSchedule to JSON file works
        ScheduleController.writeSchedule("writeTest.json");
        System.out.println();
        // Test to see if readSchedule to JSON file works
        ScheduleController.readSchedule("src/test.json"); */

        // Test to see if getTaskList works
        //System.out.print(firstSchedule.getTaskList(20210721,20230721, 10.25f,1.0f)); //-- this prints null not sure if it's working?
    }

}