package PSS;

public class Tasks {
    private String name;
    private String type;
    private Float startTime;
    private Float duration;
    private int startDate;

    public Tasks(String name, String type, Float startTime, Float duration, int startDate)
    {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.startDate = startDate;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDuration(Float duration)
    {
        this.duration = duration;
    }

    public void setStartDate(int startDate)
    {
        this.startDate = startDate;
    }

    public void setStartTime(Float startTime)
    {
        this.startTime = startTime;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return this.name;
    }

    public Float getDuration()
    {
        return this.duration;
    }

    public Float getStartTime()
    {
        return this.startTime;
    }

    public int getStartDate()
    {
        return this.startDate;
    }

    public String getType()
    {
        return this.type;
    }

    public void updateTask(String name, String type, Float startTime, Float duration, int startDate)
    {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.startDate = startDate;
    }
}