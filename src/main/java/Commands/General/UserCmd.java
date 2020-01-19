package Commands.General;

import Commands.GeneralCmd;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class UserCmd extends GeneralCmd {

    public UserCmd() {
        this.name = "user";
        this.help = "Deals with users";
        this.arguments = "id | name";
    }

    @Override
    public void doCommand(CommandEvent event) {
        String[] items = event.getArgs().split("\\s+");
        for (int x = 0; x < items.length; x++) {
            items[x] = items[x].toLowerCase();
        }

        if(items[0].contains("id") && items.length == 1) {
            event.reply("Your id is " + event.getAuthor().getId());
        } else if(items.length == 2) {
            if(items[1].length() > 4) {
                if(items[0].contains("id")) {
                    Member m = getUser(items[1], event.getGuild().getMembers());
                    if(m != null) {
                        String name = m.getEffectiveName();
                        String userID = m.getId();
                        event.reply(name + "'s id: " + userID);
                    } else {
                        event.reply("Not a user in this server! ");
                    }
                } else if(items[0].contains("name")) {
                    Member m = getName(items[1], event);
                    if(m != null) {
                        String userID = m.getId();
                        String name = m.getEffectiveName();
                        event.reply(userID + "'s name: " + name);
                    } else {
                        event.reply("Not a user in this server! ");
                    }
                }
            } else {
                event.replyError("Please input 5 or more characters! ");
            }
        }
    }

    public static Member getUser(String user, List<Member> memberList) {
        Member member = null;
        for (Member m : memberList) {
            if(m.getEffectiveName().toLowerCase().contains(user)) {
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
