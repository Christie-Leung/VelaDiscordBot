import Commands.Admin.ChannelCmd;
import Commands.General.*;
import Commands.Owner.ReactionRoles.AddReactionRoles;
import Commands.Owner.ReactionRoles.ReactionListener;
import Commands.Utilities.ListCmd;
import Sql.ReactionRolesSql;
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
import Sql.UserSql;
import com.github.ygimenez.exception.InvalidHandlerException;
import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.Paginator;
import com.github.ygimenez.model.PaginatorBuilder;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class VelaBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, InvalidHandlerException {
        JDABuilder builder = JDABuilder.createDefault(Private.botToken);
        EventWaiter waiter = new EventWaiter();

        ScheduleSql.getConn();
        MovieListSql.getConn();
        ReactionRolesSql.getConn();
        UserSql.getConn();

        ScheduleReminder scheduleReminder = new ScheduleReminder();
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
                        new HelpCmd(waiter),
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
                        new ListCmd(waiter),
                        // Admin
                        new RoleCmd(waiter),
                        new ChannelCmd(waiter),
                        // Owner
                        new AddReactionRoles(waiter),
                        new Testing(waiter)
                );

        builder.addEventListeners(
                scheduleReminder,
                regularResponses,
                irregularResponses,
                playerJoinListener,
                joinGuildListener,
                gameListener,
                reactionListener,
                client.build(),
                waiter);

        JDA bot = builder.build();

        Paginator paginator = PaginatorBuilder.createPaginator()
                .setHandler(bot)
                .shouldRemoveOnReact(true)
                .build();
        Pages.activate(paginator);
    }
}
