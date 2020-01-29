package Commands.Admin;

import Commands.AdminCmd;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.List;

public class RoleCmd extends AdminCmd {

    public RoleCmd() {
        this.name = "role";
        this.help = "deals with roles";
        this.arguments = "create | add | list | delete | remove | edit";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS, Permission.MANAGE_ROLES};
    }

    @Override
    public void doCommand(CommandEvent event) {
        Guild guild = event.getGuild();

        String[] items = event.getArgs().split("\\s+");

        if(items.length > 0) {
            if(items[0].contains("create")) {
                guild.createRole().setName(items[1]).queue();
                event.getChannel().sendMessage("Successfully created " + items[1] + " role!").queue();
            } else if(items[0].contains("delete")) {
                for (Role role : guild.getRoles()) {
                    if(role.getName().equalsIgnoreCase(items[1]) || role.getName().contains(items[1])) {
                        role.delete().queue();
                        event.getChannel().sendMessage("Successfully deleted " + role.getName() + "!").queue();
                        break;
                    }
                }
            } else if(items[0].contains("perm")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Role Perms: ").setColor(Color.green);
                for (Role r : guild.getRoles()) {
                    if(r.getName().equalsIgnoreCase(items[1]) || r.getName().contains(items[1])) {
                        for (Permission perm : Permission.values()) {
                            if(r.hasPermission(perm)) {
                                eb.appendDescription("**" + perm.getName() + ": true**\n");
                            } else {
                                eb.appendDescription(perm.getName() + ": false\n");
                            }
                        }
                    }
                }
                event.getChannel().sendMessage(eb.build()).queue();
            } else if(items[0].contains("add")) {
                if(items.length >= 3) {
                    for (Role r : guild.getRoles()) {
                        if(r.getName().equalsIgnoreCase(items[1]) || r.getName().contains(items[1])) {
                            List<Member> memberList = event.getMessage().getMentionedMembers();
                            for (Member member : memberList) {
                                boolean check = checkPlayersRole(member, r.getName());
                                if(!check) {
                                    event.getGuild().addRoleToMember(member, r).queue();
                                    event.getChannel().sendMessage("Successfully added " + member.getEffectiveName() + " to " + r.getName()).queue();
                                } else {
                                    event.getChannel().sendMessage(member.getEffectiveName() + " already has this role! ").queue();
                                }
                                break;
                            }

                        }
                    }
                } else {
                    event.getChannel().sendMessage("Do !role add [roleName] [user]").queue();
                }
            } else if(items[0].contains("remove")) {
                if(items.length >= 3) {
                    for (Role r : guild.getRoles()) {
                        if(r.getName().equalsIgnoreCase(items[1]) || r.getName().contains(items[1])) {
                            List<Member> memberList = event.getMessage().getMentionedMembers();
                            for (Member member : memberList) {
                                boolean check = checkPlayersRole(member, r.getName());
                                if(check) {
                                    event.getGuild().removeRoleFromMember(member, r).queue();
                                    event.getChannel().sendMessage("Successfully removed " + member.getEffectiveName() + " from " + r.getName()).queue();
                                } else {
                                    event.getChannel().sendMessage(member.getEffectiveName() + " does not have this role! ").queue();
                                }
                            }
                            break;
                        }
                    }
                } else {
                    event.getChannel().sendMessage("Do !role remove [roleName] [user]").queue();
                }
            } else if(items[0].contains("list")) {
                EmbedBuilder eb = new EmbedBuilder();
                if(items.length == 1) {
                    eb.setTitle("List of Roles").setColor(Color.green);
                    for (Role role : guild.getRoles()) {
                        if(!role.getName().contains("everyone")) {
                            int userWithRole = 0;
                            for (Member member : guild.getMembers()) {
                                if(member.getRoles().contains(role)) {
                                    userWithRole += 1;
                                }
                            }
                            eb.appendDescription(role.getName() + ": " + userWithRole + " users\n");
                        }
                    }
                    event.getChannel().sendMessage(eb.build()).queue();
                } else if(items.length == 2) {
                    eb = new EmbedBuilder();
                    Member m = event.getMessage().getMentionedMembers().get(0);
                    eb.setTitle(m.getEffectiveName() + "'s Roles").setColor(Color.green);
                    for (Role role : m.getRoles()) {
                        eb.appendDescription(role.getName() + "\n");
                    }
                    event.getChannel().sendMessage(eb.build()).queue();
                } else {
                    event.getChannel().sendMessage("Do create | edit | delete then say the role name of which you want to change").queue();
                }
            }
        }
    }

    boolean checkPlayersRole(Member m, String role) {
        boolean check = false;
        List<Role> roleList = m.getRoles();
        for (Role r : roleList) {
            if(role.contains(r.getName())) {
                check = true;
            }
        }
        return check;
    }
}
