package Commands.Utilities.MovieList;

import Commands.UtilitiesCmd;
import Sql.MovieListSql;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MovieListCmd extends UtilitiesCmd {

    private final EventWaiter waiter;

    public MovieListCmd(EventWaiter waiter) {
        this.name = "movielist";
        this.help = "does smth with movie lists <:keylastupidface:630989473554890752>";
        this.waiter = waiter;
    }

    @Override
    public void doCommand(CommandEvent event) {

        if(event.getArgs().isEmpty()) {
            event.replyWarning("You didn't give me the correct format >:V! The command format is `!movielist [add|see|delete]`");
        } else {
            String[] items = event.getArgs().split("\\s+");
            if(items.length == 1 && items[0].equalsIgnoreCase("see")) {

                List<MovieList> ml = MovieListSql.movieList();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Movies");

                if(ml.size() > 0) {
                    for (MovieList movieList : ml) {
                        String position = String.valueOf(movieList.getPosition());
                        String movie = movieList.getMovie();
                        eb.appendDescription(position + ". " + movie + "\n");
                    }
                    eb.setColor(new Color(136, 166, 227));
                    event.reply(eb.build());
                } else {
                    event.replyWarning("There are no movies stoopid. Go add some <:latino:631336237197688833>");
                }

            } else if (items.length >= 2 && items[0].equalsIgnoreCase("delete")) {
                MovieListSql.delete(event.getMessage().getContentRaw().substring(18));
                event.replySuccess("Successfully deleted " + event.getMessage().getContentRaw().substring(18) + " from the list!");
            } else if (items[0].equalsIgnoreCase("add")) {
                event.reply("Which movie would you like to add?");
                waiter.waitForEvent(MessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(event.getAuthor())
                                && e.getChannel().equals(event.getChannel())
                                && !e.getMessage().equals(event.getMessage()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            EmbedBuilder eb = new EmbedBuilder();
                            MovieListSql.add(e.getMessage().getContentRaw());
                            String pos = String.valueOf(MovieListSql.get(e.getMessage().getContentRaw()).getPosition());
                            eb.setDescription(pos + ". " + e.getMessage().getContentRaw());
                            eb.setColor(new Color(136, 166, 227));
                            event.reply(eb.build());
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long."));
            } else {
                event.replyWarning("You messed up real bad <:keylastupidface:630989473554890752>");
            }
        }
    }
}

