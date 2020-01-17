import Commands.General.UserCmd;
import Commands.RandomStoof.*;
import Commands.General.ChannelCmd;
import Commands.General.RoleCmd;
import Commands.Utilities.CompareDatesCmd;
import Commands.Utilities.MovieList.MovieListCmd;
import Commands.Utilities.MovieList.MovieListSql;
import Commands.Utilities.ScheduleCmd;
import Commands.ComparingDateTime.ScheduleSql;
import Listeners.MessageListener;
import Listeners.PlayerJoinListener;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class VelaBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        EventWaiter waiter = new EventWaiter();
        ScheduleSql scheduleSql = new ScheduleSql();
        ScheduleSql.getConn();

        MovieListSql movieListSql = new MovieListSql();
        MovieListSql.getConn();

        MessageListener msgListener = new MessageListener();
        PlayerJoinListener playerJoinListener = new PlayerJoinListener();

        CommandClientBuilder client = new CommandClientBuilder();
        client.setPrefix(Private.prefix)
                .setOwnerId(Private.ownerId)
                .setActivity(Activity.playing("the game"))
                .useHelpBuilder(true)
                .addCommands(
                        new RoleCmd(),
                        new ScheduleCmd(waiter, scheduleSql),
                        new YellCmd(),
                        new ChannelCmd(),
                        new MovieListCmd(waiter, movieListSql),
                        new ClapCmd(),
                        new DeleteLineCmd(),
                        new ConvoCmd(waiter),
                        new CompareDatesCmd(),
                        new EightBallCmd(),
                        new SpamCmd(waiter),
                        new UserCmd()
                );

        new JDABuilder(Private.botToken)
                .addEventListeners(msgListener, playerJoinListener, client.build(), waiter)
                .build();
    }
}
