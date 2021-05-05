package Commands.General;

import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.Page;
import com.github.ygimenez.type.PageType;
import com.jagrosh.jdautilities.command.*;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HelpCmd extends Command {

    private final Paginator.Builder pBuilder;

    public HelpCmd(EventWaiter waiter) {
        this.name = "help";
        this.help = "this thing is the help menu u stoopid!";
        this.pBuilder = new Paginator.Builder()
                .waitOnSinglePage(true)
                .setFinalAction(m -> m.clearReactions().queue(v -> {
                }, v -> {
                }))
                .setItemsPerPage(10)
                .showPageNumbers(true)
                .setEventWaiter(waiter);
    }

    @Override
    protected void execute(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Commands");
        eb.setColor(new Color((int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255)));

        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Command> tempCmds = new ArrayList<>();
        for (Command c : e.getClient().getCommands()) {
            if(!c.isHidden()) {
                if(c.getCategory() == null) {
                    if (!categories.contains("No Category")) categories.add("No Category");
                } else if(!categories.contains(c.getCategory().getName())) {
                    categories.add(c.getCategory().getName());
                }
            }
        }

        ArrayList<Page> pages = new ArrayList<>();
        for (String s : categories) {
            for (Command c : e.getClient().getCommands()) {
                if (c.getCategory() == null) {
                    if (s.contains("No Cate")) {
                        tempCmds.add(c);
                    }
                } else if (c.getCategory().getName().contains(s)) {
                    tempCmds.add(c);
                }
            }
            eb.clear();
            eb.setTitle("Commands");
            eb.addField(s, buildCommandString(tempCmds).toString(), false);
            eb.setColor(new Color((int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255)));
            pages.add(new Page(PageType.EMBED, eb.build()));
            tempCmds.clear();
        }

        e.getChannel().sendMessage((MessageEmbed) pages.get(0).getContent()).queue(success -> Pages.paginate(success, pages));
    }

    StringBuilder buildCommandString(List<Command> commands) {
        StringBuilder sb = new StringBuilder();
        for (Command c : commands) {
            sb.append("`!").append(c.getName().toUpperCase()).append("`");
            if(c.getArguments() != null) {
                sb.append(" <").append(c.getArguments()).append("> - ");
            } else {
                sb.append(" - ");
            }
            sb.append("*").append(c.getHelp()).append("*\n");
        }
        return sb;
    }
}