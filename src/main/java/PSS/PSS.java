package PSS;

import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import static PSS.ScheduleController.verifyTask;

public class PSS {
    public static void main(String args[]) throws ParseException {
        // Creating a test schedule
//        Schedule firstSchedule = new Schedule();
//        ScheduleController controllerSchedule = new ScheduleController();
//        controllerSchedule.createTransientTask("Eating Breakfast", "Appointment", 10.10f, 1.0f, 20230721);
//        controllerSchedule.createTransientTask("Eating Lunch", "Appointment",10.20f, 2.0f, 20220721);
//        controllerSchedule.createTransientTask("Eating Dinner", "Appointment",10.90f, 3.0f, 20210721);
//        controllerSchedule.createRecurringTask("Study", "Class",2.0f, 10.0f, 20230508, 20230601, 7);

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

        // Testing editing recurring task
        firstSchedule.editRecurringTask("Study", "Studious", "task", 8.0f, 1.0f, 20230308, 20230608, 7);
        firstSchedule.outputSchedule();

         */
        /*


//         Uncomment to test how info is getting written into/read into json

        /* // Test to see if writeSchedule to JSON file works
        ScheduleController.writeSchedule("writeTest.json");
        System.out.println();
        // Test to see if readSchedule to JSON file works
        ScheduleController.readSchedule("src/test.json"); */

        // Test to see if getTaskList works
//        Vector<Tasks> v = new Vector<Tasks>();
//        Schedule testschedule = new Schedule();
//        testschedule.createTransientTask("Eating Breakfast", "Appointment", 10.10f, 1.0f, 20230721);
//        testschedule.createTransientTask("Eating Lunch", "Appointment", 10.20f, 2.0f, 20230920);
//        testschedule.createRecurringTask("Study", "Class", 20230801, 10.0f, 2.0f, 20230827, 7);
//        Tasks lunchTask = testschedule.findTask("Eating Lunch");
//        Tasks breakfastTask = testschedule.findTask("Eating Breakfast");
//        Date startDateofBreakfast = breakfastTask.getJavaStartDate();
//        Date endDateofLunch = lunchTask.getJavaEndDate();
//        System.out.println(startDateofBreakfast);
//        v = testschedule.getTaskList(startDateofBreakfast, endDateofLunch);
//        System.out.println("Output starts here: ");
//        for (Tasks task: v) {
//            System.out.println("Task: " + task.getName());
//            System.out.println("Type: " + task.getType());
//            System.out.println("Start Time: " + task.getStartTime());
//            System.out.println("Duration: " + task.getDuration());
//            System.out.println("Start Date: " + task.getStartDate());
//            System.out.println();
//        }


//        Uncomment to test verification of tasks
       /* //testing verification for transient task with invalid start time
        Tasks testTask = firstSchedule.createTransientTask("Tennis", "Appointment", 24.10f, 1.0f, 20230721);
        verifyTask(testTask);
        //testing transient task with invalid duration
        Tasks testTask2 = firstSchedule.createTransientTask("Tutoring", "Appointment", 24.10f, 0.0f, 20230721);
        verifyTask((testTask2));
        */






    }

}