package Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public abstract class RandomStoofCmd extends Command {

    public RandomStoofCmd() {
        this.category = new Category("Random Stoof");
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        doCommand(commandEvent);
    }

    public abstract void doCommand(CommandEvent e);
}
