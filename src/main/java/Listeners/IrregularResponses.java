package Listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class IrregularResponses extends ListenerAdapter {

    int mood = 0;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        int activationNum = (int) Math.floor(Math.random() * 100);
        int randomNum = (int) Math.floor(Math.random() * 100);

        String msg = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();

        for (Member m : event.getMessage().getMentionedMembers()) {
            if(m.getUser().getName().contains("Vela")) {
                if(activationNum % 2 == 0) {
                    if(randomNum % 10 == 0) {
                        channel.sendMessage("BRUH WHY YOU PINGING ME").queue();
                    } else if(randomNum % 3 == 0) {
                        channel.sendMessage(event.getAuthor().getAsMention() + " no").queue();
                    }
                }
            }
        }

        if(event.getMessage().mentionsEveryone()) {
            if(activationNum % 3 == 0) {
                channel.sendMessage("YOU ARE ANNOYING!").queue();
            }
        }

        if(event.getMessage().getMentionedMembers().size() > 2) {
            if(activationNum % 4 == 0) {
                channel.sendMessage("Why u pinging so many people >:V").queue();
                channel.sendMessage("Guess I'll ping you too " + event.getAuthor().getAsMention()).queue();
            }
        }

        for (String s : MoodKeywords.positive()) {
            if(msg.toLowerCase().contains(s)) {
                mood += 1;
            }
        }

        for (String s : MoodKeywords.negative()) {
            if(msg.toLowerCase().contains(s)) {
                mood -= 1;
            }
        }

        if(calcUppercaseLetters(msg) > 5) {
            mood -= 1;
        }

        /*
        if (activationNum % 7 == 0 && mood != 0) {
            if (mood > 0) {
                if (randomNum % 6 == 0) {
                    channel.sendMessage("")
                } else if (randomNum % 9 == 0) {

                }
            } else {
                if (randomNum % 6 == 0) {
                    channel.sendMessage("WHAT DO YOU WANT " + event.getAuthor().getName().toUpperCase()).queue();
                } else if (randomNum % 9 == 0) {
                    channel.sendMessage("YALL ARE STUPID").queue();
                }
            }
        }
*/

    }

    int calcUppercaseLetters(String msg) {
        int uppercaseLetters = 0;
        for (Character c : msg.toCharArray()) {
            if(Character.isUpperCase(c)) {
                uppercaseLetters += 1;
            }
        }
        return uppercaseLetters;
    }
}
