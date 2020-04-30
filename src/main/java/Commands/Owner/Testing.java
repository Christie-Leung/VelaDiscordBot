package Commands.Owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.vdurmont.emoji.EmojiManager;
import emoji4j.Emoji;
import emoji4j.EmojiUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Testing extends Command {

    public final EventWaiter waiter;

    public Testing(EventWaiter waiter) {
        this.name = "test";
        this.ownerCommand = true;
        this.waiter = waiter;
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent e) {


        e.reply("Please reply with the message channel and message id");
        waiter.waitForEvent(MessageReceivedEvent.class,
                event -> event.getChannel().equals(e.getChannel())
                        && event.getAuthor().equals(e.getAuthor())
                        && !event.getMessage().equals(e.getMessage()),
                event -> {
                    String[] items = event.getMessage().getContentRaw().split("\\s+");
                    TextChannel messageChannel = e.getGuild().getTextChannelById(items[0].substring(2, items[0].length() - 1));
                    String messageId = items[1];
                    assert messageChannel != null;
                    String msg = messageChannel.retrieveMessageById(messageId).complete().getContentRaw();

                    if(EmojiManager.containsEmoji(msg)) {
                        String[] emojisAndRoles = msg.split("\\s+");
                        ArrayList<Emoji> emojiList = new ArrayList<>();
                        ArrayList<String> roleList = new ArrayList<>();
                        boolean roleName = false;
                        StringBuilder stringBuilder = new StringBuilder();

                        for (String emojisAndRole : emojisAndRoles) {
                            Emoji emoji = EmojiUtils.getEmoji(emojisAndRole);
                            if(emoji != null) {
                                emojiList.add(emoji);
                                roleName = false;
                            } else if(emojisAndRole.equals("-")) {
                                roleName = true;
                                roleList.add(stringBuilder.toString());
                                stringBuilder.delete(0, stringBuilder.length());
                            } else if(roleName) {
                                stringBuilder.append(emojisAndRole).append(" ");
                            }
                        }
                        roleList.add(stringBuilder.toString());
                        for (String role : roleList) {
                            e.reply(role);
                        }
                        if(emojiList.size() > 0) {
                            for (Emoji unicode : emojiList) {
                                messageChannel.retrieveMessageById(messageId).queue(message ->
                                        message.addReaction(unicode.getEmoji()).queue()
                                );
                            }
                        } else {
                            e.replyError("Didnt werk you stoopid");
                        }
                    }

                },
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> e.reply("Sorry, you took too long."));

    }


}
