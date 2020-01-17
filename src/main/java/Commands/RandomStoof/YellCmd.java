package Commands.RandomStoof;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class YellCmd extends Command {

    public YellCmd() {
        this.name = "!";
        this.botPermissions = new Permission[]{Permission.MESSAGE_WRITE};
        this.category = new Category("Random Stoof");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

    }


}
