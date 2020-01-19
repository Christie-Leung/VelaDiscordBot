package Commands.RandomStoof;

import Commands.General.UserCmd;
import Commands.RandomStoofCmd;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SpamCmd extends RandomStoofCmd {

    private final EventWaiter waiter;
    ArrayList<String> blackList = new ArrayList<>();

    public SpamCmd(EventWaiter waiter) {
        this.name = "spam";
        this.help = "wellllll";
        this.waiter = waiter;
        this.arguments = "User (either ping or name)> <#toSpam";
        setBlackList();
    }

    public void setBlackList() {
        blackList.add("301028982684516352");
        blackList.add("276839191537516546");
    }

    public static void sendPrivateMessage(User user, String content) {
        // notice that we are not placing a semicolon (;) in the callback this time!
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(content).queue());
    }

    public static void sendPrivateEmbedMessage(User user, EmbedBuilder content) {
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(content.build()).queue());
    }

    @Override
    public void doCommand(CommandEvent event) {
        User user;

        String[] items = event.getArgs().split("\\s+");
        if(items.length == 1) {
            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if(items[0].contains("add")) {
                    event.reply("Who would you like to add to the blacklist? (Ping them)");
                    waiter.waitForEvent(MessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel, and for safety, a different message
                            e -> e.getAuthor().equals(event.getAuthor())
                                    && e.getChannel().equals(event.getChannel())
                                    && !e.getMessage().equals(event.getMessage()),
                            // respond, inserting the name they listed into the response
                            e -> {
                                if(e.getMessage().getMentionedMembers().isEmpty()) {
                                    event.reply("You did not ping anyone! ");
                                } else {
                                    addToBlackList(e.getMessage().getMentionedMembers().get(0).getId());
                                }
                            },
                            // if the user takes more than a minute, time out
                            1, TimeUnit.MINUTES, () -> event.reply("<@" + event.getAuthor().getId() + ">" + " Sorry, you took too long."));
                } else if(items[0].contains("remove")) {
                    event.reply("Who would you like to remove from the blacklist? (Ping them)");
                    waiter.waitForEvent(MessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel, and for safety, a different message
                            e -> e.getAuthor().equals(event.getAuthor())
                                    && e.getChannel().equals(event.getChannel())
                                    && !e.getMessage().equals(event.getMessage()),
                            // respond, inserting the name they listed into the response
                            e -> {
                                if(e.getMessage().getMentionedMembers().isEmpty()) {
                                    event.reply("You did not ping anyone! ");
                                } else {
                                    removeFromBlackList(e.getMessage().getMentionedMembers().get(0).getId());
                                }
                            },
                            // if the user takes more than a minute, time out
                            1, TimeUnit.MINUTES, () -> event.reply("<@" + event.getAuthor().getId() + ">" + " Sorry, you took too long."));
                }
            }

        } else if(items.length >= 2) {
            if(event.getMessage().getMentionedMembers().isEmpty()) {
                Member m = UserCmd.getUser(items[0], event.getGuild().getMembers());
                user = m.getUser();
            } else {
                user = event.getMessage().getMentionedUsers().get(0);
            }
            int number = Integer.parseInt(items[1]);
            if(user != null) {
                if(!getBlackList().contains(user.getId())) {
                    event.reply("What do you want to spam them with?");
                    User finalUser = user;
                    waiter.waitForEvent(MessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel, and for safety, a different message
                            e -> e.getAuthor().equals(event.getAuthor())
                                    && e.getChannel().equals(event.getChannel())
                                    && !e.getMessage().equals(event.getMessage()),
                            // respond, inserting the name they listed into the response
                            e -> {
                                for (int x = 0; x < number; x++) {
                                    sendPrivateMessage(finalUser, e.getMessage().getContentDisplay());
                                }
                            },
                            // if the user takes more than a minute, time out
                            1, TimeUnit.MINUTES, () -> event.reply("<@" + event.getAuthor().getId() + ">" + " Sorry, you took too long."));
                    sendPrivateMessage(user, "This is sent by " + event.getAuthor().getName());
                } else {
                    event.replyWarning("No you are not allowed to spam them <:latino:631336237197688833>");
                }
            }
        } else {
            event.replyWarning("You stoopid");
        }
    }

    public void addToBlackList(String s) {
        blackList.add(s);
    }

    public String getBlackList() {
        StringBuilder users = new StringBuilder();
        for (String s : blackList) {
            users.append(s).append("\n");
        }
        return users.toString();
    }

    void removeFromBlackList(String s) {
        blackList.removeIf(s::contains);
    }
}
