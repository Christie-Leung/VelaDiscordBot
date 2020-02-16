package Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public abstract class SchoolCmd extends Command {

    public SchoolCmd() {
        this.category = new Category("School");
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        doCommand(commandEvent);
    }

    public abstract void doCommand(CommandEvent e);
}
