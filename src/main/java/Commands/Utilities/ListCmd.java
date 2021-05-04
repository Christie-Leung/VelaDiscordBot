package Commands.Utilities;

import Commands.Essential;
import Commands.UtilitiesCmd;
import Sql.UserSql;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ListCmd extends UtilitiesCmd {

    private final Paginator.Builder pBuilder;
    private final EventWaiter waiter;

    public ListCmd(EventWaiter waiter) {
        this.name = "list";
        this.help = "shows all saved lists";
        this.arguments = "list | view | create | add | remove";
        this.pBuilder = new Paginator.Builder()
                .waitOnSinglePage(true)
                .setFinalAction(m -> m.clearReactions().queue(v -> {
                }, v -> {
                }))
                .setItemsPerPage(10)
                .allowTextInput(true)
                .showPageNumbers(true)
                .useNumberedItems(true)
                .wrapPageEnds(true)
                .setBulkSkipNumber(5)
                .setColor(new Color((int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255), (int) Math.floor(Math.random() * 255)))
                .setEventWaiter(waiter);
        this.waiter = waiter;
    }

    @Override
    public void doCommand(CommandEvent e) {
        String[] args = e.getArgs().split("\\s+");

        if (args[0].toLowerCase().equals("list")) {
            List<String> userLists = new ArrayList<>();
            String name = "";
            if (args.length == 2) {
                try {
                    Member m = e.getGuild().getMemberByTag(args[1]);
                    if (m != null) {
                        userLists = UserSql.getLists(e.getGuild().getMemberByTag(args[1]).getId());
                        name = e.getGuild().getMemberByTag(args[1]).getUser().getName();
                    }
                } catch (Exception exception) {
                    e.reply("You have encountered an error: " + exception.getMessage());
                    return;
                }
            } else if (args.length == 1) {
                userLists = UserSql.getLists(e.getAuthor().getId());
                name = e.getAuthor().getName();
            }
            if (userLists.isEmpty()) {
                e.reply(name + " does not have any saved lists.");
                return;
            }
            List<String> finalUserLists = userLists;
            String finalName = name;
            e.reply(name + "'s Lists", message -> {
                pBuilder.clearItems();
                pBuilder.setColor(e.getMember().getColor());
                pBuilder.setText(finalName + "'s Lists");
                for (String item : finalUserLists) {
                    pBuilder.addItems(item);
                }
                pBuilder.build().display(message);
            });
        } else if (args[0].equals("view")) {
            List<String> items = new ArrayList<>();
            String tableName = "";
            if (e.getArgs().contains("\"")) {
                String[] split = e.getArgs().split("\"\\s*");
                tableName = split[1];
                if (split.length == 2) {
                    items = UserSql.getItems(e.getAuthor().getId(), split[1]);
                } else {
                    items = UserSql.getItems(e.getGuild().getMemberByTag(split[2]).getId(), split[1]);
                }
            } else {
                tableName = args[1];
                if (args.length == 3) {
                    if (e.getGuild().getMemberByTag(args[1]) != null) {
                        items = UserSql.getItems(e.getGuild().getMemberByTag(args[2]).getId(), args[1]);
                    } else {
                        e.reply("This is not a valid user tag in this server!");
                    }
                } else if (args.length == 2) {
                    items = UserSql.getItems(e.getAuthor().getId(), args[1]);
                } else {
                    e.reply("Please include the following arguments: **!list view [table name (\"\" if spaces)] [Optional: User Tag]**");
                    return;
                }
            }
            if (items.isEmpty()) {
                e.reply("There are no items in this list!");
                return;
            }
            List<String> finalItems = items;
            String finalTableName = tableName;
            e.reply(tableName, message -> {
                pBuilder.clearItems();
                pBuilder.setColor(e.getMember().getColor());
                pBuilder.setText(finalTableName);
                for (String item : finalItems) {
                    pBuilder.addItems(item);
                }
                pBuilder.build().display(message);
            });
        } else if (args[0].equals("create")) {
            if (args.length >= 2) {
                String title = e.getMessage().getContentRaw().substring(13);
                e.reply("What items would you like to add to your list? \nPlease separate items by **a newline or `**");
                waiter.waitForEvent(MessageReceivedEvent.class,
                        event -> event.getChannel().equals(e.getChannel())
                                && event.getAuthor().equals(e.getAuthor())
                                && !event.getMessage().equals(e.getMessage()),
                        event -> {
                            String[] items = event.getMessage().getContentRaw().split("[\\n`]+\\s*");
                            String list = String.join("`", items);
                            UserSql.addList(e.getAuthor().getId(), title, list);
                            e.getChannel().sendMessage("Successfully added the " + title + " list.").queue();
                        }, 2, TimeUnit.MINUTES, () -> e.reply("Sorry, you took too long."));
            } else {
                e.reply("Please include the following arguments: **!list create [table name]**");
            }
        } else if (args[0].equals("delete")) {
            if (args.length >= 2) {
                String title = e.getMessage().getContentRaw().substring(13);
                UserSql.delList(e.getAuthor().getId(), title);
                e.getChannel().sendMessage("Successfully deleted the " + title + " list.").queue();
            } else {
                e.reply("Please include the following arguments: **!list delete [table name]**");
            }
        } else if (args[0].equals("add")) {
            if (args.length >= 2) {
                e.reply("What items would you like to add to your list?\n(If you specified a position, you may only insert one entry) \nPlease separate items by **a newline or `**");
                waiter.waitForEvent(MessageReceivedEvent.class,
                        event -> event.getChannel().equals(e.getChannel())
                                && event.getAuthor().equals(e.getAuthor())
                                && !event.getMessage().equals(e.getMessage()),
                        event -> {
                            String title = Essential.getLongString(e.getArgs(), 1);
                            int index = 0;
                            try {
                                index = Integer.parseInt(args[args.length - 1]);
                            } catch (NumberFormatException exception) {
                                index = -1;
                            }
                            List<String> list1 = UserSql.getItems(e.getAuthor().getId(), title);
                            ArrayList<String> list = new ArrayList<>(list1);
                            String[] items = event.getMessage().getContentRaw().split("[\\n`]+\\s*");
                            if (index < list.size() && 0 <= index) {
                                list.add(index - 1, items[0]);
                            } else if (index < 0) {
                                list.addAll(Arrays.asList(items));
                            }
                            UserSql.replaceList(e.getAuthor().getId(), title, String.join("`", list));
                            e.reply("Successfully added to list!");
                        });
            } else {
                e.reply("Please include the following arguments: **!list add [table name (\"\" if spaces)] [Optional: Index]**");
            }
        } else if (args[0].equals("remove")) {
            if (args.length >= 3) {
                String title = Essential.getLongString(e.getArgs(), 1);
                List<String> list = UserSql.getItems(e.getAuthor().getId(), title);
                List<String> temp = Essential.getIndexes(Essential.getLongString(e.getArgs(), 2), list);
                String items = String.join("`", temp);
                UserSql.replaceList(e.getAuthor().getId(), title, items);
                e.reply("Successfully removed from " + title);
            } else {
                e.reply("Please include the following arguments: **!list remove [table name (\"\" if spaces)] [Index (Separate by commas or use dash for a range)]**");
            }
        }
    }
}
