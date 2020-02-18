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


    public static String getLeftVsLate(LocalDateTime now, LocalDateTime scheduled) {
        String modifier;
        if(now.isAfter(scheduled)) {
            modifier = "late";
        } else {
            modifier = "left";

        }
        return modifier;
    }

    public static String getYouVsThere(LocalDateTime now, LocalDateTime scheduled) {
        String modifier2;
        if(now.isAfter(scheduled)) {
            modifier2 = "You";
        } else {
            modifier2 = "There";
        }
        return modifier2;
    }

    public String compareDates(LocalDateTime eventDT) {

        Days days = new Days();
        int day = days.getTotalDays(eventDT);

        Clock clock = Clock.getComparedTime(eventDT, day);
        int clockDay = clock.day;
        int hour = clock.hour;
        int minute = clock.min;

        return String.format("%s are **%d** %s **%d** %s and **%d** %s %s!",
                getYouVsThere(LocalDateTime.now(), eventDT), clockDay, getDay(day), hour,
                getHour(hour), minute, getMinute(minute),
                getLeftVsLate(LocalDateTime.now(), eventDT));
    }

    public EmbedBuilder buildEmbedMessage(String eventName, String description) {
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
