package Commands.RandomStoof;

import Commands.RandomStoofCmd;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class YellCmd extends RandomStoofCmd {

    private final EventWaiter waiter;

    public YellCmd(EventWaiter waiter) {
        this.name = "yell";
        this.botPermissions = new Permission[]{Permission.MESSAGE_WRITE};
        this.arguments = "add | remove | list";
        this.help = "Deals with different strings (For ex. yesss racoooool)";
        this.waiter = waiter;
    }

    @Override
    public void doCommand(CommandEvent event) {
        String[] items = event.getArgs().split("\\s+");

        if(items.length == 2) {
            if(items[0].toLowerCase().contains("add")) {
                event.getChannel().sendMessage("What would you like the output of that cmd to be? ").queue();
                waiter.waitForEvent(MessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(event.getAuthor())
                                && e.getChannel().equals(event.getChannel())
                                && !e.getMessage().equals(event.getMessage()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            StringCmds.addStringCmds(items[1], e.getMessage().getContentRaw());
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> event.reply("<@" + event.getAuthor().getId() + ">" + " Sorry, you took too long."));
            } else if(items[0].toLowerCase().contains("remove")) {
                StringCmds.removeStringCmds(items[1]);
            }
        } else if(items[0].toLowerCase().contains("list")) {
            // event.getChannel().sendMessage(StringCmds.getListOfStrings().build()).queue();
            SpamCmd.sendPrivateEmbedMessage(event.getMember().getUser(), StringCmds.getListOfStrings());
        }
    }


}
