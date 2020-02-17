import Commands.Admin.ChannelCmd;
import Commands.Admin.RoleCmd;
import Commands.General.*;
import Commands.Owner.Testing;
import Commands.RandomStoof.*;
import Commands.School.Chem;
import Commands.Utilities.CompareDatesCmd;
import Commands.Utilities.MovieList.MovieListCmd;
import Commands.Utilities.ScheduleCmd;
import Listeners.GameListener;
import Listeners.JoinGuildListener;
import Listeners.MessageListener;
import Listeners.PlayerJoinListener;
import Sql.MovieListSql;
import Sql.ScheduleSql;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class VelaBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        EventWaiter waiter = new EventWaiter();

        ScheduleSql.getConn();
        MovieListSql.getConn();

        MessageListener msgListener = new MessageListener();
        PlayerJoinListener playerJoinListener = new PlayerJoinListener();
        JoinGuildListener joinGuildListener = new JoinGuildListener();
        GameListener gameListener = new GameListener();

        StringCmds.setListOfStrings();

        CommandClientBuilder client = new CommandClientBuilder();
        client.setPrefix("!")
                .setOwnerId(Private.ownerId)
                .setActivity(Activity.playing("you"))
                .useHelpBuilder(false)
                .addCommands(
                        new HelpCmd(),
                        // General
                        new UserCmd(),
                        new InfoCmd(),
                        // Random Stoof
                        new ClapCmd(),
                        new ConvoCmd(waiter),
                        new DeleteLineCmd(),
                        new EightBallCmd(),
                        new EmojiCmd(),
                        new SpamCmd(waiter),
                        new YellCmd(waiter),
                        new LetterCmd(waiter),
                        // School
                        new Chem(),
                        // Utilities
                        new MovieListCmd(waiter),
                        new CompareDatesCmd(),
                        new ScheduleCmd(waiter),
                        // Admin
                        new RoleCmd(),
                        new ChannelCmd(),
                        // Owner
                        new Testing(waiter)

                );

        new JDABuilder(Private.botToken)
                .addEventListeners(msgListener,
                        playerJoinListener,
                        joinGuildListener,
                        gameListener,
                        client.build(),
                        waiter)
                .build();
    }
}
