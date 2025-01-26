package com.exe.paradox.Tools;

public class TimeUtils {
    public class TimeDifference{
        public String time;
        public String units;

        public TimeDifference() {
        }
    }

    public TimeDifference getTimeDifference(){
        return new TimeDifference();
    }
    public static void getDifference(TimeDifference td, long futureTimeStamp){
        long currentTimeStamp = System.currentTimeMillis();

        long difference = futureTimeStamp -  currentTimeStamp;
        if (difference < 0){
            difference *= -1;
        }

        int seconds = (int) (difference/1000);
        int minutes = (int) (seconds/60);
        int hours = (int) (minutes/60);

        int days = (int) (hours/24);
        int months = (int) (days/30.5);
        int years = (int) (months/12);

        if (seconds<60){
            td.time = String.valueOf(seconds);
            td.units = "seconds";
        } else if (minutes<60){
            td.time = String.valueOf(minutes);
            td.units = "minutes";
        } else if (hours<24){
            td.time = String.valueOf(hours);
            td.units = "hours";
        } else if (days<31){
            td.time = String.valueOf(days);
            td.units = "days";
        } else if (months<12){
            td.time = String.valueOf(months);
            td.units = "months";
        } else {
            td.time = String.valueOf(years);
            td.units = "years";
        }
    }
}
