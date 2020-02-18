package Commands.ComparingDateTime;

import java.time.LocalDateTime;

public class Days {

    public int getTotalDays(LocalDateTime localDateTime) {
        int currentDay = LocalDateTime.now().getDayOfYear();
        int scheduledDay = localDateTime.getDayOfYear();
        int totalDays = 0;
        int scheduledYear = localDateTime.getYear();
        int currentYear = LocalDateTime.now().getYear();
        totalDays += getYear(scheduledYear, currentYear);
        boolean isFuture = scheduledYear > currentYear || (scheduledYear >= currentYear && scheduledDay > currentDay);

        if(isFuture && scheduledDay > currentDay) {
            totalDays += scheduledDay - currentDay;
        } else if(isFuture && scheduledDay < currentDay) {
            if(currentYear % 4 == 0) {
                totalDays += (366 - currentDay) + scheduledDay;
            } else {
                totalDays += (365 - currentDay) + scheduledDay;
            }
        } else if(scheduledDay < currentDay) {
            totalDays += currentDay - scheduledDay;
        } else if(currentDay < scheduledDay) {
            if(currentYear % 4 == 0) {
                totalDays += (366 - scheduledDay) + currentDay;
            } else {
                totalDays += (365 - scheduledDay) + currentDay;
            }
        }
        return totalDays;
    }

    public int getYear(int scheduledYear, int currentYear) {
        int daysInYear = 0;
        if(scheduledYear > currentYear) {
            for (int x = currentYear; x < scheduledYear; x++) {
                if(scheduledYear % 4 == 0) {
                    daysInYear += 366;
                } else {
                    daysInYear += 365;
                }
            }
        } else {
            for (int x = currentYear; x > scheduledYear; x--) {
                if(scheduledYear % 4 == 0) {
                    daysInYear += 366;
                } else {
                    daysInYear += 365;
                }
            }
        }
        return daysInYear;
    }
}

