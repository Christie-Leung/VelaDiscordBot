package Commands.RandomStoof;

import Commands.RandomStoofCmd;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PickCmd extends RandomStoofCmd {

    public PickCmd() {
        this.name = "pick";
        this.help = "Helps you pick an option if you are struggling";
        this.arguments = "all different options (separated by spaces)";
    }


    @Override
    public void doCommand(CommandEvent e) {
        String[] args = e.getArgs().split("\\s+");
        int random = (int) Math.floor(Math.random() * args.length);
        e.replySuccess(args[random]);
    }
}
