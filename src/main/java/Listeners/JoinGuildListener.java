package Listeners;

import Commands.General.GuildsSettings;
import Commands.General.GuildsSettingsImpl;
import Sql.GuildSettingsSql;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinGuildListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        GuildsSettings gs = new GuildsSettingsImpl();
        gs.setGuildId(event.getGuild().getIdLong());
        gs.setGuildName(event.getGuild().getName());
        gs.setGuildPrefix("!");
        gs.setGuildMembers(event.getGuild().getMaxMembers());
        gs.setGuildWelcomeMsg("");
        GuildSettingsSql.add(gs);
    }
}
