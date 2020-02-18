package Commands.Utilities;

import Commands.ComparingDateTime.*;
import Commands.UtilitiesCmd;
import Sql.ScheduleSql;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ScheduleCmd extends UtilitiesCmd {

    public final EventWaiter waiter;

    public ScheduleCmd(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "schedule";
        this.help = "All things to do with schedules";
        this.arguments = "add <name> | delete <name> | list | mine";
    }

    @Override
    public void doCommand(CommandEvent e) {
        String[] items = e.getArgs().split("\\s+");
        EmbedBuilder replyMsg;
        switch (items[0]) {
            case "add":
                e.reply("When would you like me to remind you?");
                EmbedBuilder parameters = new EmbedBuilder();
                parameters.setTitle("Parameters")
                        .appendDescription("You may choose any of the options")
                        .addField("Optional Time", "--:--AM/PM (Ex. 9:00am)", true)
                        .addField("Optional Day", "[month] [day] (Ex. Feb 17", true)
                        .addField("Optional Year", "[yyyy] (Ex. 2020)", true)
                        .addField("All Options", "ex. Feb 17 2020 9:00am", true);
                parameters.setColor(new Color((int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255)));
                e.reply(parameters.build());

                waiter.waitForEvent(MessageReceivedEvent.class,
                        event -> event.getChannel().equals(e.getChannel())
                                && event.getAuthor().equals(e.getAuthor())
                                && !event.getMessage().equals(e.getMessage()),
                        event -> {
                            String[] dateTime = event.getMessage().getContentRaw().split("\\s+");
                            e.reply(addSchedule(Arrays.asList(dateTime), e).build());
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> e.reply("Sorry, you took too long."));
                break;
            case "delete":
                deleteSchedule(e.getArgs().substring(7));
                replyMsg = listSchedule(e.getGuild().getIdLong());
                reply(replyMsg, e);
                break;
            case "list":
                replyMsg = listSchedule(e.getGuild().getIdLong());
                reply(replyMsg, e);
                break;
            case "mine":
                replyMsg = userSchedule(e.getAuthor().getIdLong());
                reply(replyMsg, e);
                break;
        }
    }

    EmbedBuilder addSchedule(List<String> time, CommandEvent e) {
        EmbedBuilder eb;

        Clock scheduled = parseString(time);
        LocalDateTime scheduledTime = LocalDateTime.of(
                scheduled.year,
                scheduled.month,
                scheduled.day,
                scheduled.hour,
                scheduled.min
        );

        String description = e.getArgs().substring(4);

        ScheduledEvent scheduledEvent = new ScheduledEventImpl();
        scheduledEvent.setName(e.getAuthor().getName());
        scheduledEvent.setDescription(description);
        scheduledEvent.setNameID(e.getAuthor().getIdLong());
        scheduledEvent.setServerID(e.getGuild().getIdLong());
        scheduledEvent.setTimestamp(Timestamp.valueOf(scheduledTime));

        if(!scheduledEvent.getName().isEmpty() && !scheduledEvent.getDescription().isEmpty() && !scheduledEvent.getTimestamp().toString().isEmpty()) {
            ScheduleSql.add(scheduledEvent);
        }

        CompareDates calendar = new CompareDates();
        eb = calendar.buildEmbedMessage(description, calendar.compareDates(scheduledTime));
        return eb;
    }

    void deleteSchedule(String scheduleName) {
        String description = ScheduleSql.get(scheduleName).getDescription();
        ScheduleSql.delete(description);
    }

    EmbedBuilder listSchedule(long channelID) {
        EmbedBuilder eb = new EmbedBuilder();
        List<Schedules> schedulesList = parseThruSchedules(channelID);

        for (Schedules s : schedulesList) {
            String name = s.getUserName();
            String description = s.getDescription();
            String formatting = s.getTime();

            eb.setTitle("All Schedules Events");
            eb.addField(description + " (" + name + ")", formatting, false);
            eb.setColor(Color.PINK);
        }
        if(eb.isEmpty()) {
            eb.appendDescription("There are no scheduled events!");
        }
        return eb;
    }

    EmbedBuilder userSchedule(long userID) {
        EmbedBuilder eb = new EmbedBuilder();
        List<Schedules> schedulesList = parseThruSchedules(userID);

        for (Schedules s : schedulesList) {
            String name = s.getUserName();
            String description = s.getDescription();
            String formatting = s.getTime();

            eb.setTitle("Your Scheduled Events");
            eb.addField(description + " (" + name + ")", formatting, false);
            eb.setColor(Color.PINK);
        }

        if(eb.isEmpty()) {
            eb.appendDescription("You have no scheduled events!");
        }
        return eb;
    }

    Clock parseString(List<String> stringTime) {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        int hour = LocalDateTime.now().getHour();
        int min = LocalDateTime.now().getMinute();

        Map<String, Integer> monthAbbreviations = new HashMap<>();
        monthAbbreviations.put("jan", 1);
        monthAbbreviations.put("feb", 2);
        monthAbbreviations.put("mar", 3);
        monthAbbreviations.put("apr", 4);
        monthAbbreviations.put("may", 5);
        monthAbbreviations.put("jun", 6);
        monthAbbreviations.put("jul", 7);
        monthAbbreviations.put("aug", 8);
        monthAbbreviations.put("sep", 9);
        monthAbbreviations.put("oct", 10);
        monthAbbreviations.put("nov", 11);
        monthAbbreviations.put("dec", 12);

        for (String s : stringTime) {
            if(s.length() == 4) {
                year = Integer.parseInt(s);

            } else if(s.toLowerCase().contains("am") || s.toLowerCase().contains("pm")) {
                int index = 1;

                for (Character c : s.toCharArray()) {
                    if(c == ':') {
                        index = s.indexOf(':');
                    }
                }

                int i;
                if(index == 1) {
                    i = Integer.parseInt(String.valueOf(s.charAt(0)));
                } else {
                    i = Integer.parseInt(s.substring(0, index));
                }
                if(s.toLowerCase().contains("am") || s.contains("12")) {
                    hour = i;
                } else {
                    hour = i + 12;
                }

                min = Integer.parseInt(s.substring(index + 1, s.length() - 2));

            } else if(Character.isLetter(s.toCharArray()[0])) {
                for (String monthString : monthAbbreviations.keySet()) {
                    if(s.toLowerCase().contains(monthString)) {
                        month = monthAbbreviations.get(monthString);
                    }
                }
            } else {
                day = Integer.parseInt(s);
            }
        }
        return new Clock(year, month, day, hour, min);
    }

    List<Schedules> parseThruSchedules(long ID) {
        List<Schedules> schedulesList = new ArrayList<>();

        List<ScheduledEvent> se = ScheduleSql.scheduledEvent();

        for (ScheduledEvent aSe : se) {
            if(aSe.getServerID() == ID || aSe.getNameID() == ID) {
                String userName = aSe.getName();
                String description = aSe.getDescription();
                LocalDateTime scheduledTime = aSe.getTimestamp().toLocalDateTime();
                CompareDates compareDates = new CompareDates();
                String format = compareDates.compareDates(scheduledTime);

                Schedules s = new Schedules(userName, description, format);
                schedulesList.add(s);
            }
        }
        return schedulesList;
    }

    void reply(EmbedBuilder embedBuilder, CommandEvent e) {
        if(embedBuilder.isEmpty()) {
            e.replyError("You messed up real bad <:keylastupidface:630989473554890752>");
        } else {
            e.reply(embedBuilder.build());
        }
    }
}

class Schedules {
    String userName;
    String description;
    String time;

    public Schedules(String userName, String description, String time) {
        this.userName = userName;
        this.description = description;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
}