package Commands.RandomStoof;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ClapCmd extends Command {

    public ClapCmd() {
        this.name = "clap";
        this.help = "clap";
        this.arguments = "string...";
        this.category = new Category("Random Stoof");
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().delete().queue();
        String str = event.getArgs();
        StringBuilder string = new StringBuilder();
        string.append(" :clap: ");

        for (Character c : str.toCharArray()) {
            if(!Character.isWhitespace(c)) { // Check if not white space print the char
                string.append(c);
            } else {
                string.append(" :clap: ");
            }
        }
        string.append(" :clap: ");
        event.getMessage().delete().queue();
        event.getChannel().sendMessage(string.toString()).queue();
    }
}
