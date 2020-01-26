package Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public abstract class GeneralCmd extends Command {

    public GeneralCmd() {
        this.category = new Category("General");
    }

    @Override
    protected void execute(CommandEvent event) {
        doCommand(event);
    }

    public abstract void doCommand(CommandEvent event);
}
