package Commands.RandomStoof;

import Commands.RandomStoofCmd;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

public class EmojisCmd extends RandomStoofCmd {

    public EmojisCmd() {
        this.name = "emojis";
        this.help = "Displays info about Emojis";
        this.arguments = "emojis | name";
    }

    @Override
    public void doCommand(CommandEvent e) {
        String[] items = e.getArgs().split("\\s+");
        if(EmojiManager.containsEmoji(e.getMessage().getContentRaw())) {
            Emoji emoji = EmojiManager.getByUnicode(items[0]);
            if(emoji != null) {
                e.replySuccess(emoji.getHtmlDecimal() + "\n" + emoji.getUnicode() + "\n" + emoji.getAliases());
                e.getMessage().addReaction(emoji.getUnicode()).queue();
            } else {
                e.replyError("Didnt work you stoopid");
            }
        } else if(items.length == 1) {
            int x = 0;
            for (Emoji emoji : EmojiManager.getAll()) {
                if(emoji.getAliases().get(0).toLowerCase().contains(items[0])) {
                    String unicode = emoji.getUnicode();
                    if(x > 19) {
                        e.getMessage().clearReactions().queue();
                        x -= 20;
                    }
                    e.getMessage().addReaction(unicode).queue();
                    x++;
                }
            }
        }
    }
}
