package Listeners;

import Commands.ComparingDateTime.CompareDates;
import Commands.RandomStoof.StringCmds;
import Sql.ScheduleSql;
import Commands.RandomStoof.SpamCmd;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MessageListener extends ListenerAdapter {
    private CompareDates calendar = new CompareDates();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        JDA jda = event.getJDA();
        final Timer timer = new Timer("Timer");
        EmbedBuilder eb = new EmbedBuilder();
        final LocalDateTime[] date = new LocalDateTime[1];

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                List<String> schedule = ScheduleSql.getDate();
                if(!schedule.isEmpty()) {
                    for (String str : schedule) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        date[0] = LocalDateTime.parse(str, formatter);
                    }
                    if(LocalDateTime.now().isAfter(date[0])) {
                        String description = ScheduleSql.getFromDate(Timestamp.valueOf(date[0])).getDescription();
                        long name = ScheduleSql.get(description).getNameID();
                        ScheduleSql.delete(description);
                        User user = jda.getUserById(name);
                        eb.setColor(new Color(177, 240, 216))
                                .addField("Reminder!", "I was told to remind you of " + description + "! Now go do it >:V", false)
                                .build();
                        if(user != null) {
                            SpamCmd.sendPrivateEmbedMessage(user, eb);
                        }
                        ScheduleSql.delete(description);
                    }
                }

            }

        };
        timer.scheduleAtFixedRate(repeatedTask, 0, 6000);

        StringBuilder stringBuilder = new StringBuilder();
        MessageChannel channel = event.getChannel();
        int year = LocalDateTime.now().getYear();

        if(!event.getAuthor().isBot() && event.getMessage().getContentRaw().startsWith("!")) {
            stringBuilder.append(event.getMessage().getContentRaw());
            stringBuilder.deleteCharAt(0);
            String msg = stringBuilder.toString();
            int characters = stringBuilder.length();

            if(characters <= 200) {
                if(msg.startsWith("ping")) {
                    channel.sendMessage("pong").queue();
                } else {
                    for (StringCmds s : StringCmds.listOfStrings) {
                        if(msg.toLowerCase().startsWith(s.cmd)) {
                            event.getChannel().sendMessage(s.output).tts(true).queue();
                            event.getMessage().delete().queue();
                        }
                    }
                    switch (stringBuilder.toString()) {
                        //random
                        case "christmas":
                            channel.sendMessage(calendar.compareDates("Christmas Countdown", LocalDateTime.of(year, 12, 25, 0, 0, 0)).build()).queue();
                            break;
                        case "halloween":
                            channel.sendMessage(calendar.compareDates("Halloween Countdown", LocalDateTime.of(year, 10, 31, 0, 0, 0)).build()).queue();
                            break;
                    }
                }
            }
        }
    }
}