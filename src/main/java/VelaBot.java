import Commands.Admin.ChannelCmd;
import Commands.General.*;
import Commands.Owner.ReactionListener;
import Commands.Owner.ReactionRolesSql;
import Commands.Owner.Testing;
import Commands.RandomStoof.*;
import Commands.Admin.RoleCmd;
import Commands.School.Chem;
import Commands.Utilities.CompareDatesCmd;
import Commands.Utilities.MovieList.MovieListCmd;
import Commands.Utilities.ScheduleCmd;
import Listeners.*;
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
        ReactionRolesSql.getConn();

        ScheduleReminder scheduleReminder = new ScheduleReminder();
        LogChat logChat = new LogChat();
        RegularResponses regularResponses = new RegularResponses();
        IrregularResponses irregularResponses = new IrregularResponses();
        PlayerJoinListener playerJoinListener = new PlayerJoinListener();
        JoinGuildListener joinGuildListener = new JoinGuildListener();
        GameListener gameListener = new GameListener();
        ReactionListener reactionListener = new ReactionListener();


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
                        new PickCmd(),
                        // School
                        new Chem(),
                        // Utilities
                        new MovieListCmd(waiter),
                        new CompareDatesCmd(),
                        new ScheduleCmd(waiter),
                        // Admin
                        new RoleCmd(waiter),
                        new ChannelCmd(),
                        // Owner
                        new Testing(waiter)
                );

        new JDABuilder(Private.botToken)
                .addEventListeners(logChat,
                        scheduleReminder,
                        regularResponses,
                        irregularResponses,
                        playerJoinListener,
                        joinGuildListener,
                        gameListener,
                        reactionListener,
                        client.build(),
                        waiter)
                .build();
    }
}
