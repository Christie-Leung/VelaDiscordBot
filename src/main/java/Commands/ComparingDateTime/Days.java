package Commands.ComparingDateTime;

import java.time.LocalDateTime;

public class Days {

    public static int getDays(LocalDateTime localDateTime) {

        int nowMonth = LocalDateTime.now().getMonthValue();
        int comparedMonth = localDateTime.getMonthValue();
        int days = localDateTime.getDayOfMonth() - LocalDateTime.now().getDayOfMonth();
        int daysInMonth = 0;
        int syear = localDateTime.getYear();
        int nyear = LocalDateTime.now().getYear();

        if(syear > nyear && comparedMonth > nowMonth) {
            for (int x = nyear; x < syear; x++) {
                if(syear % 4 == 0) {
                    daysInMonth += 366;
                } else {
                    daysInMonth += 365;
                }
            }
        }

        if(syear >= nyear && comparedMonth > nowMonth || syear <= nyear && comparedMonth < nowMonth) {
            for (int i = nowMonth; i < comparedMonth; i++) {
                switch (i) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        daysInMonth += 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        daysInMonth += 30;
                        break;
                    case 2:
                        if(((syear % 4 == 0) &&
                                !(syear % 100 == 0))
                                || (syear % 400 == 0))
                            daysInMonth += 29;
                        else
                            daysInMonth += 28;
                        break;
                }
            }
            for (int i = nowMonth; i > comparedMonth; i--) {
                switch (i) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        daysInMonth -= 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        daysInMonth -= 30;
                        break;
                    case 2:
                        if(((syear % 4 == 0) &&
                                !(syear % 100 == 0))
                                || (syear % 400 == 0))
                            daysInMonth -= 29;
                        else
                            daysInMonth -= 28;
                        break;
                }
            }
        } else if(syear > nyear && comparedMonth < nowMonth || syear < nyear && comparedMonth > nowMonth) {
            int sMonth = comparedMonth + (12 - nowMonth);
            for (int i = sMonth; i < nowMonth; i--) {
                switch (i) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        daysInMonth += 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        daysInMonth += 30;
                        break;
                    case 2:
                        if(((syear % 4 == 0) &&
                                !(syear % 100 == 0))
                                || (syear % 400 == 0))
                            daysInMonth += 29;
                        else
                            daysInMonth += 28;
                        break;
                }
            }
            for (int i = sMonth; i > nowMonth; i++) {
                switch (i) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        daysInMonth -= 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        daysInMonth -= 30;
                        break;
                    case 2:
                        if(((syear % 4 == 0) &&
                                !(syear % 100 == 0))
                                || (syear % 400 == 0))
                            daysInMonth -= 29;
                        else
                            daysInMonth -= 28;
                        break;
                }
            }
        }
        daysInMonth += days;
        return daysInMonth;
    }
}

