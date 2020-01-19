package Commands.General;

import Commands.GeneralCmd;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class InfoCmd extends GeneralCmd {

    public InfoCmd() {
        this.name = "info";
    }

    @Override
    public void doCommand(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(161, 255, 221));
        eb.setTitle("**" + e.getSelfUser().getName() + "**");
        eb.setDescription("Info about " + e.getSelfMember().getEffectiveName());
        eb.addField("Servers", String.valueOf(e.getClient().getTotalGuilds()), true);
        eb.addField("Prefix", "`" + e.getClient().getPrefix() + "`", true);
        eb.addField("Start Time", e.getClient().getStartTime().getYear() + "-" + e.getClient().getStartTime().getMonthValue() + "-" + e.getClient().getStartTime().getDayOfMonth(), true);
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
