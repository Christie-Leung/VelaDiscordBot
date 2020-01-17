package Commands.RandomStoof;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class ConvoCmd extends Command {

    private final EventWaiter waiter;

    public ConvoCmd(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "hi";
        this.help = "hi";
        this.category = new Category("Random Stoof");
    }

    @Override
    protected void execute(CommandEvent event) {

        event.reply("Hello! What's your name?");

        waiter.waitForEvent(MessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel, and for safety, a different message
                e -> e.getAuthor().equals(event.getAuthor())
                        && e.getChannel().equals(event.getChannel())
                        && !e.getMessage().equals(event.getMessage()),
                // respond, inserting the name they listed into the response
                e -> event.reply("Hello, " + e.getMessage().getContentRaw() + "! I'm " + e.getJDA().getSelfUser().getName() + "!"),
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long."));


    }
}
