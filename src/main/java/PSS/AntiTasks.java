package PSS;

public class AntiTasks extends Tasks {
    private RecurringTasksOccurrence recurringOccurrence;
    public AntiTasks(String name, String type, Float startTime,
                     Float duration, int startDate)
    {
        super(name, type, startTime, duration, startDate);
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
