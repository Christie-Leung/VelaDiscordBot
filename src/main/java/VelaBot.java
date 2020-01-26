import Commands.Admin.ChannelCmd;
import Commands.Admin.RoleCmd;
import Commands.General.*;
import Commands.RandomStoof.*;
import Commands.Utilities.CompareDatesCmd;
import Listeners.JoinGuildListener;
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
       /* ScheduleSql scheduleSql = new ScheduleSql();
        ScheduleSql.getConn();

        MovieListSql movieListSql = new MovieListSql();
        MovieListSql.getConn();
*/
        MessageListener msgListener = new MessageListener();
        PlayerJoinListener playerJoinListener = new PlayerJoinListener();
        JoinGuildListener joinGuildListener = new JoinGuildListener();

        StringCmds.setListOfStrings();

        CommandClientBuilder client = new CommandClientBuilder();
        client.setPrefix("!")
                .setOwnerId(Private.ownerId)
                .setActivity(Activity.playing("you"))
                .useHelpBuilder(false)
                .addCommands(
                        new HelpCmd(),
                        // General
                        new ChannelCmd(),
                        new InfoCmd(),
                        new RoleCmd(),
                        new UserCmd(),
                        // Random Stoof
                        new ClapCmd(),
                        new ConvoCmd(waiter),
                        new DeleteLineCmd(),
                        new EightBallCmd(),
                        new EmojisCmd(),
                        new SpamCmd(waiter),
                        new YellCmd(waiter),
                        // Utilities
                        //    new MovieListCmd(waiter, movieListSql),
                        new CompareDatesCmd()
                        //   new ScheduleCmd(waiter, scheduleSql)
                );

        new JDABuilder(Private.botToken)
                .addEventListeners(msgListener,
                        playerJoinListener,
                        joinGuildListener,
                        client.build(),
                        waiter)
                .build();
    }
}
