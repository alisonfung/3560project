package PSS;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Tasks {
    private String name;
    private String type;
    private Float startTime;
    private Float duration;
    private int startDate;

    private Date date;

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
        return name;
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

    private String findTime(float time)
    {
        //Subtracts hour from time to find just the minutes, then rounds number to nearest 15 increment.
        float floatMinutes = Math.round((60 * (time - (int) time))/15) * 15;
        String minutes = Integer.toString((int) floatMinutes);

        String hours = Integer.toString((int) time);
        if(hours.length() < 2)
            hours = "0" + hours;

        if(minutes.length() < 2)
            minutes += "0";

        return hours + "-" + minutes;
    }
    public Date getJavaStartDate()
    {
        //Initializes date, defines date format to transform into Java Date, and finds exact time from given startTime
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH-mm");
        String time = findTime(this.startTime);

        //Parses Java Date from startDate and time
        try {
            date = dateFormat.parse(Integer.toString(this.startDate) + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public Date getJavaEndDate()
    {
        //Retrieves java start date and initializes calendar
        Date javaStartDate = getJavaStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(javaStartDate);

        //Calls findTime on duration of task to find out exact number of hours and minutes
        String[] taskDuration = findTime(duration).split("-");
        int hours = Integer.parseInt(taskDuration[0]);
        int minutes = Integer.parseInt(taskDuration[1]);

        //Adds hours and minutes to java start date
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
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

    public Date getDate() {
        return date;
    }
}