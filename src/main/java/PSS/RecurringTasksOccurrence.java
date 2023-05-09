package PSS;

public class RecurringTasksOccurrence extends Tasks{
    private AntiTasks antiTask;
    public RecurringTasksOccurrence(String name, Float startTime, Float duration, int startDate)
    {
        super(name, "occurrence", startTime, duration, startDate);
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
