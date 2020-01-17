package Commands.RandomStoof;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class EightBallCmd extends Command {

    public EightBallCmd() {
        this.name = "8ball";
        this.help = "u should know waht this does";
        this.category = new Category("Random Stoof");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

        int rando = (int) Math.floor(Math.random() * 10);
        String decision;

        switch (rando) {
            case 1:
                decision = "https://tenor.com/7zZn.gif"; //yes
                break;
            case 2:
                decision = "https://tenor.com/baE1E.gif"; //duh
                break;
            case 3:
                decision = "https://tenor.com/tegD.gif"; //no
                break;
            case 4:
                decision = "https://tenor.com/3djE.gif"; //angery
                break;
            case 5:
                decision = "https://tenor.com/uWDm.gif"; //probably
                break;
            case 6:
                decision = "https://tenor.com/wFOh.gif"; //idk
                break;
            case 7:
                decision = "https://tenor.com/EAAG.gif"; //how about no
                break;
            case 8:
                decision = "https://tenor.com/7tGX.gif"; // pika pika
                break;
            case 9:
                decision = "https://tenor.com/MsUW.gif"; //of course
                break;
            case 10:
                decision = "https://tenor.com/ssRw.gif"; //nod head
                break;
            case 11:
                decision = "https://tenor.com/WKGD.gif"; //idgaf
                break;
            case 12:
                decision = "https://tenor.com/IFPD.gif"; //thor yes
                break;
            case 13:
                decision = "https://tenor.com/o6gI.gif"; //nope
                break;
            case 14:
                decision = "https://tenor.com/GjJc.gif"; //no
                break;
            case 15:
                decision = "https://tenor.com/EVAp.gif"; // yes
                break;
            default:
                decision = "https://tenor.com/SF0a.gif"; //hmm
                break;
        }
        commandEvent.replySuccess(decision + "");
    }
}
