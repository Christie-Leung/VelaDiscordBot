package Commands.General;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ChannelCmd extends Command {

    public ChannelCmd() {
        this.name = "channels";
        this.arguments = "create, add, delete, list";
        this.category = new Category("General");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

    }
}
