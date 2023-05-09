package PSS;

public class TransientTasks extends Tasks {

    public TransientTasks(String name, Float startTime,
                          Float duration, int startDate)
    {
        super(name, "transient", startTime, duration, startDate);
    }
}

