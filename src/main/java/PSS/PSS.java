package PSS;

public class PSS {
    public static void main(String args[])
    {
        Schedule firstSchedule = new Schedule();
        firstSchedule.createTransientTask("Eating Lunch", 10.25f, 1.0f, 20230721);
        firstSchedule.createTransientTask("Eating Lunch", 10.25f, 1.0f, 20230701);
        firstSchedule.createTransientTask("Eating Lunch", 10.25f, 1.0f, 20210701);
    }
}
