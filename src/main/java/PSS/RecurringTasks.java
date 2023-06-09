package PSS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Calendar;

public class RecurringTasks extends Tasks{
    private int endDate;
    private int frequency;
    private Vector<RecurringTasksOccurrence> listOfOccurrences;
    public RecurringTasks(String name, String type, Float startTime,
                          Float duration, int startDate, int endDate, int frequency) {
        super(name, type, startTime, duration, startDate);
        this.endDate = endDate;
        this.frequency = frequency;
        createOccurrences();
    }

    private void createOccurrences() {
        this.listOfOccurrences = new Vector<>();
        //Initializes all fields of the class
        String name = getName();
        String type = getType();
        Float startTime = getStartTime();
        Float duration = getDuration();
        int startDate = getStartDate();

        Date firstDate;
        Date lastDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        try {
            //Converting startDate & endDate to Date type
            firstDate = dateFormat.parse(Integer.toString(startDate));
            lastDate = dateFormat.parse(Integer.toString(endDate));
            c.setTime(firstDate);
            //Creating RecurringTaskOccurences until firstDate is greater than lastDate
            while(firstDate.compareTo(lastDate) <= 0)
            {
                //Parses integer date from Java Date
                Integer newDate = Integer.parseInt(dateFormat.format(firstDate));

                RecurringTasksOccurrence occurrence = new RecurringTasksOccurrence(name, type, startTime, duration, newDate);
                listOfOccurrences.add(occurrence);

                //Increments number of days depending on frequency supplied
                c.add(Calendar.DATE, this.frequency);
                firstDate = c.getTime();
                //System.out.println(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setEndDate(int endDate)
    {
        this.endDate = endDate;
    }

    public void setFrequency(int frequency)
    {
        this.frequency = frequency;
    }

    public int getEndDate()
    {
        return this.endDate;
    }

    public int getFrequency()
    {
        return this.frequency;
    }

    public Vector<RecurringTasksOccurrence> getListOfOccurrences()
    {
        return listOfOccurrences;
    }

    public void updateTask(String name, String type, int startDate, Float startTime, Float duration, int endDate, int frequency) {
        super.updateTask(name, type, startTime, duration, startDate);
        this.endDate = endDate;
        this.frequency = frequency;
        createOccurrences();
    }

}
