package Listeners;

import Commands.RandomStoof.SpamCmd;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalTime;

public class IrregularResponses extends ListenerAdapter {

    int mood = 0;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        int activationNum = (int) Math.floor(Math.random() * 100);
        int randomNum = (int) Math.floor(Math.random() * 100);

        String msg = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();

        if(LocalTime.now().getHour() == 16 && LocalTime.now().getMinute() == 20 && LocalTime.now().getSecond() == 0) {
            event.getChannel().sendMessage("@everyone u asked for it").queue();
        }
        if(!event.getAuthor().isBot()) {
            if(randomNum % 9 == 0) {
                if(event.getMessage().getContentRaw().toCharArray()[event.getMessage().getContentRaw().length() - 1] == '.' && event.getMessage().getContentRaw().split("\\s+").length >= 3) {
                    channel.sendMessage(event.getAuthor().getAsMention() + " hey! bad").queue();
                }
            }
        }
        int wordCount = 0;
        for (String word : event.getMessage().getContentRaw().split("\\s+")) {
            if(word.toCharArray().length >= 8) {
                wordCount += 1;
                if(wordCount > 4) {
                    channel.sendMessage(event.getAuthor().getAsMention() + " hey! bad").queue();
                    break;
                }
            }
        }
        int commaCount = 0;
        for (Character c : event.getMessage().getContentRaw().toCharArray()) {
            if(c == ',') {
                commaCount += 1;
                if(commaCount >= 3) {
                    channel.sendMessage(event.getAuthor().getAsMention() + " hey! bad").queue();
                    break;
                }
            }
        }
        if(event.getMessage().isFromGuild() && !event.getMessage().getMentionedMembers().isEmpty()) {
            for (Member m : event.getMessage().getMentionedMembers()) {
                if(m.getUser().getName().contains("Vela") && !event.getAuthor().isBot()) {
                    if(event.getAuthor().getName().contains("Kangaroo")) {
                        SpamCmd.sendPrivateMessage(event.getAuthor(), "HEY YOU STOP PINGING ME!");
                    }
                    if(activationNum % 2 == 0) {
                        if(randomNum % 5 == 0) {
                            channel.sendMessage("BRUH WHY YOU PINGING ME").queue();
                        } else if(randomNum % 3 == 0) {
                            channel.sendMessage(event.getAuthor().getAsMention() + " no").queue();
                        }
                    }
                } else if(m.getUser().getName().contains("Keyla") && !event.getAuthor().isBot()) {
                    channel.sendMessage(event.getAuthor().getAsMention() + " hey! bad").queue();
                }
            }

            if(event.getMessage().mentionsEveryone()) {
                if(activationNum % 3 == 0) {
                    channel.sendMessage("YOU ARE ANNOYING!").queue();
                }
            }

            if(event.getMessage().getMentionedMembers().size() >= 2) {
                if(activationNum % 4 == 0) {
                    channel.sendMessage("Why u pinging so many people >:V").queue();
                    channel.sendMessage("Guess I'll ping you too " + event.getAuthor().getAsMention()).queue();
                }
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
