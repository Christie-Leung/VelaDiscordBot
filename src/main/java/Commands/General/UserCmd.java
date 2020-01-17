package Commands.General;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;

public class UserCmd extends Command {

    public UserCmd() {
        this.name = "user";
        this.help = "Deals with users";
        this.arguments = "<id>";
        this.category = new Category("General");
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] items = event.getArgs().split("\\s+");
        for (int x = 0; x < items.length; x++) {
            items[x] += items[x].toLowerCase();
        }

        if(items[0].contains("id") && items.length == 1) {
            event.reply("Your id is " + event.getAuthor().getId());
        } else if(items[0].contains("id") && items.length == 2) {
            Member m = getUser(items[1], event);
            if(m != null) {
                String name = m.getEffectiveName();
                String userID = m.getId();
                event.reply(name + "'s id: " + userID);
            } else {
                event.reply("Not a user in this server! ");
            }
        } else if(items[0].contains("name") && items.length == 2) {
            Member m = getName(items[1], event);
            if(m != null) {
                String userID = m.getId();
                String name = m.getEffectiveName();
                event.reply(userID + "'s name: " + name);
            } else {
                event.reply("Not a user in this server! ");
            }
        }
    }

    public static Member getUser(String user, CommandEvent event) {
        Member member = null;
        for (Member m : event.getGuild().getMembers()) {
            if(m.getEffectiveName().regionMatches(true, 0, user, 0, 4) || m.getEffectiveName().toLowerCase().contains(user)) {
                member = m;
                break;
            }
        }
        return member;
    }

    public static Member getName(String userId, CommandEvent event) {
        Member member = null;
        for (Member m : event.getGuild().getMembers()) {
            if(m.getId().regionMatches(true, 0, userId, 0, 7)) {
                member = m;
                break;
            }
        }
        return member;
    }
}
