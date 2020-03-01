package Listeners;

import Commands.ComparingDateTime.CompareDates;
import Commands.RandomStoof.StringCmds;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import java.time.LocalDateTime;

public class RegularResponses extends ListenerAdapter {

    private CompareDates calendar = new CompareDates();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
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
                            String author = event.getAuthor().getName();
                            RestAction<Message> appendingMsg = event.getChannel().sendMessage(author + " said " + s.output).tts(true);
                            long msgid = appendingMsg.complete().getIdLong();
                            event.getChannel().deleteMessageById(msgid).queue();
                            event.getChannel().sendMessage(s.output).tts(false).queue();
                            event.getMessage().delete().queue();
                        }
                    }
                    switch (stringBuilder.toString()) {
                        //random
                        case "christmas":
                            String description = calendar.compareDates(LocalDateTime.of(year, 12, 25, 0, 0, 0));
                            channel.sendMessage(calendar.buildEmbedMessage("Christmas Countdown", description).build()).queue();
                            break;
                        case "halloween":
                            description = calendar.compareDates(LocalDateTime.of(year, 10, 31, 0, 0, 0));
                            channel.sendMessage(calendar.buildEmbedMessage("Halloween Countdown", description).build()).queue();
                            break;
                    }
                }
            }
        }
    }
}
