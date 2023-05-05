package PSS;

public class RecurringTasks extends Tasks{
    private int endDate;
    private int frequency;
    public RecurringTasks(String name, Float startTime,
                          Float duration, int startDate, int endDate, int frequency)
    {
        super(name, startTime, duration, startDate);
        this.endDate = endDate;
        this.frequency = frequency;
    }
}
