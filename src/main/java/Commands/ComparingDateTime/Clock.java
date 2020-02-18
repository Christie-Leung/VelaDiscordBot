package Commands.ComparingDateTime;

import java.time.LocalDateTime;

public class Clock {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int min;

    public Clock(int year, int month, int day, int hour, int min) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
    }

    public static Clock getComparedTime(LocalDateTime scheduled, int day) {
        int currentDay = LocalDateTime.now().getDayOfYear();
        int scheduledDay = scheduled.getDayOfYear();
        int currentHour = LocalDateTime.now().getHour();
        int scheduledHour = scheduled.getHour();
        int currentMin = LocalDateTime.now().getMinute();
        int scheduledMin = scheduled.getMinute();

        int[] time = new int[2];
        boolean isFuture = scheduledDay > currentDay || (scheduledDay == currentDay && scheduledHour > currentHour) || (scheduledDay == currentDay && scheduledHour == currentHour && scheduledMin > currentMin);

        if(!(scheduledHour == 0 && scheduledMin == 0)) {
            if(isFuture) {
                time = getTimeDifference(scheduledMin, currentMin, scheduledHour, currentHour, true);
            } else {
                time = getTimeDifference(currentMin, scheduledMin, currentHour, scheduledHour, false);
            }
        }

        int comparedHours = time[0];
        int comparedMin = time[1];

        if(comparedMin >= 60) {
            comparedMin -= 60;
            comparedHours += 1;
        }
        if(comparedHours >= 24) {
            int spillOver = comparedHours / 24;
            comparedHours -= 24 * spillOver;
            day += spillOver;
        }
        if(comparedHours > 0 && day != 0) {
            day -= 1;
        }
        return new Clock(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), day, comparedHours, comparedMin);
    }

    static int[] getTimeDifference(int bigMin, int smallMin, int bigHour, int smallHour, boolean isFuture) {
        int comparedMins = 0;
        int comparedHours = 0;

        if(bigMin > smallMin) {
            comparedMins = bigMin - smallMin;
            if(bigHour > smallHour) {
                comparedHours = bigHour - smallHour;
            } else if(bigHour < smallHour) {
                comparedHours = bigHour + (24 - smallHour);
            }
        } else if(bigMin < smallMin) {
            comparedMins = bigMin + (60 - smallMin);
            if(bigHour > smallHour) {
                if(isFuture) {
                    comparedHours = bigHour - smallHour - 1;
                } else {
                    comparedHours = bigHour - smallHour + 1;
                }
            } else if(bigHour < smallHour) {
                if(isFuture) {
                    comparedHours = bigHour + (24 - smallHour) - 1;
                } else {
                    comparedHours = bigHour + (24 - smallHour) + 1;
                }
            }
        } else {
            if(bigHour > smallHour) {
                if(isFuture) {
                    comparedHours = bigHour - smallHour;
                } else {
                    comparedHours = bigHour + (24 - smallHour);
                }
            } else if(bigHour < smallHour) {
                if(isFuture) {
                    comparedHours = bigHour + (24 - smallHour);
                } else {
                    comparedHours = bigHour - smallHour;
                }
            }
        }
        return new int[]{comparedHours, comparedMins};
    }

}
