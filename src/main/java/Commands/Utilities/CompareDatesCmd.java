package Commands.Utilities;

import Commands.ComparingDateTime.CompareDates;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.time.LocalDateTime;

public class CompareDatesCmd extends Command {

    CompareDates calendar = new CompareDates();

    public CompareDatesCmd() {
        this.name = "compare";
        this.help = "Compares give date with now";
        this.arguments = "yyyy mm dd HH MM ss";
        this.category = new Category("Utilities");
    }

    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty()) {
            event.replyWarning("You didn't give me the correct format >:V! The command format is `!compare yyyy mm dd HH MM ss`");
        } else {
            // split the choices on all whitespace
            String[] items = event.getArgs().split("\\s+");
            int year = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]);
            int day = Integer.parseInt(items[2]);
            int hour = Integer.parseInt(items[3]);
            int minute = Integer.parseInt(items[4]);
            int second = Integer.parseInt(items[5]);

            if(year < 1000) {
                event.replyWarning("You didn't give a proper year! smh my head");
            }
            if(month > 12 || month < 1) {
                event.replyWarning("So, do you think that a " + month + "th month exists? u boomer");
            }
            if(day > 31 || day < 1) {
                event.replyWarning("Day " + day + " :interrobang:");
            }
            if(hour > 24 || hour < 0) {
                event.replyWarning("Yes, " + hour + "th hour exists >:V");
            }
            if(minute > 60 || minute < 0) {
                event.replyWarning("Thanos is gonna come and snap his fingers, THERE ARE ONLY 60 MINUTES IN AN HOUR >:V");
            }
            if(second > 60 || second < 0) {
                event.replyWarning(second + " seconds" + " :interrobang:");
            }
            event.reply(calendar.compareDates("Comparing ...", LocalDateTime.of(year, month, day, hour, minute, second)).build());
        }
    }
}
