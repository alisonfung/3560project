package PSS;

public class Tasks {
    private String name;
    private Float startTime;
    private Float duration;
    private int startDate;

    public Tasks(String name, Float startTime, Float duration, int startDate)
    {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.startDate = startDate;
    }
}