package Listeners;

import Commands.RandomStoof.SpamCmd;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayerJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if(e.getGuild().getName().contains("Inter")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Welcome to " + e.getGuild().getName());
            eb.setDescription("Please go to #roles to get your roles and ping a mod/admin with your real name!");
            SpamCmd.sendPrivateEmbedMessage(e.getUser(), eb);
        }
    }
}
