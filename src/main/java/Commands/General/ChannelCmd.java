package Commands.General;

import Commands.GeneralCmd;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildChannel;

import java.awt.*;

public class ChannelCmd extends GeneralCmd {

    public ChannelCmd() {
        this.name = "channels";
        this.arguments = "create | add | delete | list";
    }

    @Override
    public void doCommand(CommandEvent event) {
        String[] items = event.getArgs().split("\\s+");

        if(items.length == 2) {
            if(items[0].contains("create")) {
                event.getGuild().createTextChannel(items[1]).queue();
                event.getChannel().sendMessage("Success!").queue();
            } else if(items[0].contains("edit")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(177, 240, 216));
                eb.setTitle("Text Channels in Order");
                for (int i = 0; i < event.getGuild().getCategories().size(); i++) {
                    String categoryName = event.getGuild().getCategories().get(i).getName();
                    StringBuilder channels = new StringBuilder();
                    for (GuildChannel channel : event.getGuild().getCategories().get(i).getChannels()) {
                        channels.append(channel.getName()).append("\n");
                    }
                    eb.addField(categoryName, channels.toString(), false);
                }

                event.getChannel().sendMessage(eb.build()).queue();
            }
        }
    }
}
