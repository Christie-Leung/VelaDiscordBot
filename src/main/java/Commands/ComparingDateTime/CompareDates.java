package Commands.ComparingDateTime;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;


public class CompareDates {

    public static String getDay(int day) {
        String d;
        if(day == 1 || day == -1) {
            d = "day";
        } else {
            d = "days";
        }
        return d;
    }

    public static String getHour(int hour) {
        String h;
        if(hour == 1 || hour == -1) {
            h = "hour";
        } else {
            h = "hours";
        }
        return h;
    }

    public static String getMinute(int minute) {
        String m;
        if(minute == 1 || minute == -1) {
            m = "minute";
        } else {
            m = "minutes";
        }
        return m;
    }

    public static String getSecond(int second) {
        String s;
        if(second == 1 || second == -1) {
            s = "second";
        } else {
            s = "seconds";
        }
        return s;
    }

    public static String getLeftVsLate(LocalDateTime now, ChronoLocalDateTime scheduled) {
        String modifier;
        if(now.isAfter(scheduled)) {
            modifier = "late";
        } else {
            modifier = "left";

        }
        return modifier;
    }

    public static String getYouVsThere(LocalDateTime now, ChronoLocalDateTime scheduled) {
        String modifier2;
        if(now.isAfter(scheduled)) {
            modifier2 = "You";
        } else {
            modifier2 = "There";
        }
        return modifier2;
    }

    public EmbedBuilder compareDates(String eventName, LocalDateTime eventDT) {

        int day = Days.getDays(eventDT);

        Clock clock = Clock.getComparedTime(eventDT, day);
        int clockDay = clock.day;
        int hour = clock.hour;
        int minute = clock.min;
        int second = clock.sec;

        String description = String.format("%s are **%d** %s **%d** %s **%d** %s and **%d** %s %s!",
                getYouVsThere(LocalDateTime.now(), eventDT), clockDay, getDay(day), hour,
                getHour(hour), minute, getMinute(minute), second, getSecond(second),
                getLeftVsLate(LocalDateTime.now(), eventDT));


        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Color.PINK);

        eb.setTitle("__**" + eventName + "**__");
        eb.setDescription(description);

        switch (eventName) {
            case "Christmas Countdown":
                eb.setThumbnail("https://us.123rf.com/450wm/lawkeeper/lawkeeper1411/lawkeeper141100004/33240113-stock-vector-vector-illustration-of-cute-cartoon-christmas-reindeer.jpg?ver=6");
                eb.setColor(Color.GREEN);
                break;
            case "Halloween Countdown":
                eb.setThumbnail("https://www.searchpng.com/wp-content/uploads/2019/01/Pumpkin-Halloween-Clipart-PNG-715x715.png");
                eb.setColor(Color.orange);
                break;
        }

        return eb;
    }


}
