package PSS;

public class AntiTasks extends Tasks {
    private RecurringTasksOccurrence recurringOccurrence;
    public AntiTasks(String name, Float startTime,
                     Float duration, int startDate)
    {
        super(name, "anti", startTime, duration, startDate);
    }

    public RecurringTasksOccurrence getRecurringOccurrence()
    {
        return this.recurringOccurrence;
    }

    public void setRecurringOccurrence(RecurringTasksOccurrence recurringOccurrence)
    {
        this.recurringOccurrence = recurringOccurrence;
    }
}
