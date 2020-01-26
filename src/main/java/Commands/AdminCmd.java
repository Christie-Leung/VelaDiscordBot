package Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public abstract class AdminCmd extends Command {

    public AdminCmd() {
        this.category = new Category("Admin");
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent event) {
        doCommand(event);
    }

    public abstract void doCommand(CommandEvent event);
}

