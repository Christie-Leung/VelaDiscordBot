package Commands.RandomStoof;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;

import java.util.List;

public class DeleteLineCmd extends Command {

    public DeleteLineCmd() {
        this.name = "delete";
        this.name = "deyeet";
        this.help = "delete u stoopid";
        this.arguments = "<integer>";
        this.category = new Category("Random Stoof");
    }

    @Override
    protected void execute(CommandEvent event) {

        String[] items = event.getArgs().split("\\s+");
        int lines = Integer.parseInt(items[0]);
        MessageHistory history = new MessageHistory(event.getTextChannel());
        List<Message> msgs;

        for (int x = 0; x <= lines; x++) {
            msgs = history.retrievePast(1).complete();
            msgs.get(0).delete().queue();
        }

    }
}
