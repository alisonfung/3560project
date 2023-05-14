package PSS;

public class RecurringTasksOccurrence extends Tasks{
    private AntiTasks antiTask;
    public RecurringTasksOccurrence(String name, String type, Float startTime, Float duration, int startDate)
    {
        super(name, type, startTime, duration, startDate);
    }

    public void setAntiTask(AntiTasks antiTask)
    {
        this.antiTask = antiTask;
    }

    public AntiTasks getAntiTask()
    {
        return this.antiTask;
    }
}
