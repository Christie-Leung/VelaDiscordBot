package Commands.Utilities;

import Commands.ComparingDateTime.*;
import Commands.UtilitiesCmd;
import Sql.ScheduleSql;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Commands.ComparingDateTime.CompareDates.*;

public class ScheduleCmdOld extends UtilitiesCmd {

    private final EventWaiter waiter;
    private ScheduledEvent scheduledEvent;

    public ScheduleCmdOld(EventWaiter waiter) {
        this.name = "schedule";
        this.waiter = waiter;
        this.help = "schedules an event";
        this.arguments = "add | get | list | delete> <yyyy> <mm> <dd> <HH> <MM> <ss";
    }

    @Override
    public void doCommand(CommandEvent event) {

        CompareDates calendar = new CompareDates();

        if(event.getArgs().isEmpty()) {
            event.replyWarning("You didn't give me the correct format >:V! The command format is `!schedule {add|list|delete|get} yyyy mm dd HH MM ss`");
        } else {
            // split the choices on all whitespace
            String[] items = event.getArgs().split("\\s+");
            if(items.length == 1 && items[0].equalsIgnoreCase("list")) {

                List<ScheduledEvent> se = ScheduleSql.scheduledEvent();
                EmbedBuilder eb = new EmbedBuilder();

                for (ScheduledEvent aSe : se) {

                    long serverID = event.getGuild().getIdLong();
                    if(aSe.getServerID() == serverID) {

                        String description = aSe.getDescription();
                        LocalDateTime scheduledTime = aSe.getTimestamp().toLocalDateTime();
                        int day = Days.getDays(scheduledTime);
                        Clock clock = Clock.getComparedTime(scheduledTime, day);
                        int clockDay = clock.day;
                        int hour = clock.hour;
                        int minute = clock.min;

                        String formatting = String.format("%s are **%d** %s **%d** %s and **%d** %s %s!",
                                getYouVsThere(LocalDateTime.now(), scheduledTime), clockDay, CompareDates.getDay(day), hour,
                                getHour(hour), minute, getMinute(minute),
                                getLeftVsLate(LocalDateTime.now(), scheduledTime));

                        eb.setTitle("All Schedules Events");
                        eb.addField(description + " (" + aSe.getName() + ")", formatting, false);
                        eb.setColor(Color.PINK);
                    } else if(aSe.getServerID() < 0) {
                        eb.setDescription("There are no scheduled events for this server!");
                    }
                    //   event.reply("<@!" + aSe.getName() + ">");
                    //  event.reply(calendar.compareDates(description, time).build());
                }
                if(eb.isEmpty()) {
                    event.replyWarning("There are no scheduled events!");
                } else {
                    event.reply(eb.build());
                }
            } else if(items.length == 2) {
                switch (items[0]) {
                    case "get":
                        String eventName = ScheduleSql.get(items[1]).getDescription();
                        LocalDateTime time = ScheduleSql.get(items[1]).getTimestamp().toLocalDateTime();
                        event.reply(calendar.compareDates(eventName, time).build());
                        break;
                    case "delete":
                        String description = ScheduleSql.get(items[1]).getDescription();
                        ScheduleSql.delete(description);
                        event.replySuccess("Successfully deleted " + description + "!");
                        break;
                    default:
                        event.replyWarning("You stoopid, you messed up");
                        break;
                }
            } else if(items.length == 7 && items[0].equalsIgnoreCase("add")) {

                int year = Integer.parseInt(items[1]);
                int month = Integer.parseInt(items[2]);
                int day = Integer.parseInt(items[3]);
                int hour = Integer.parseInt(items[4]);
                int minute = Integer.parseInt(items[5]);
                int second = Integer.parseInt(items[6]);

                if(year < 1000) {
                    event.replyWarning("You didn't give a proper year! smh my head");
                }
                if(month > 12 || month < 1) {
                    event.replyWarning("So, do you think that a " + month + "th month exists? u boomer");
                }
                if(day > 31 || day < 1) {
                    event.replyWarning("Day " + day + " :interrobang:");
                }
                if(hour > 24 || hour < 0) {
                    event.replyWarning("Yes, " + hour + "th hour exists >:V");
                }
                if(minute > 60 || minute < 0) {
                    event.replyWarning("Thanos is gonna come and snap his fingers, THERE ARE ONLY 60 MINUTES IN AN HOUR >:V");
                }
                if(second > 60 || second < 0) {
                    event.replyWarning(second + " seconds" + " :interrobang:");
                }


                Timestamp scheduledTime = Timestamp.valueOf(LocalDateTime.of(year, month, day, hour, minute, second));
                event.reply("What do you want the schedule name to be?");


                waiter.waitForEvent(MessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(event.getAuthor())
                                && e.getChannel().equals(event.getChannel())
                                && !e.getMessage().equals(event.getMessage()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            event.reply(calendar.compareDates(e.getMessage().getContentRaw(), LocalDateTime.of(year, month, day, hour, minute, second)).build());
                            scheduledEvent = new ScheduledEventImpl();
                            scheduledEvent.setNameID(Long.parseLong(event.getAuthor().getId()));
                            scheduledEvent.setName(event.getAuthor().getName());
                            scheduledEvent.setDescription(e.getMessage().getContentRaw());
                            scheduledEvent.setTimestamp(scheduledTime);
                            scheduledEvent.setServerID(event.getGuild().getIdLong());
                            if(scheduledEvent.getName() != null && scheduledEvent.getDescription() != null && scheduledEvent.getTimestamp().toString() != null) {
                                ScheduleSql.add(scheduledEvent);
                            } else {
                                event.reply("Unsuccessful!" +
                                        "\nUser: " + scheduledEvent.getName() +
                                        "\nDescription: " + scheduledEvent.getDescription() +
                                        "\nDate: " + scheduledEvent.getTimestamp());
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long."));

            } else {
                event.replyWarning("You messed up real bad <:keylastupidface:630989473554890752>");
            }
        }
    }


}


