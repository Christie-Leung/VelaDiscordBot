package Commands.General;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class RoleCmd extends Command {

    public RoleCmd() {
        this.name = "role";
        this.help = "shows ";
        this.arguments = "create, add, list, delete, remove, edit";
        this.category = new Category("Commands/General");
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS, Permission.MANAGE_ROLES};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

    }
}
