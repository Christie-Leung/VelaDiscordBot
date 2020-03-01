package Listeners;

import Commands.RandomStoof.LetterCmd;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

public class GameListener extends ListenerAdapter {

    Timer timer = new Timer("Timer");
    boolean guessingGame = false;
    char guessLetter = ' ';
    int guessCount;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        boolean game = LetterCmd.runGame;
        if(!event.getAuthor().isBot() && game) {
            if(event.getMessage().getContentRaw().contains("!!")) {
                if(!guessingGame) {
                    startGuessingGame();
                }
                boolean win = guess(event.getMessage().getContentRaw());
                if(guessCount == 0) {
                    event.getChannel().sendMessage("You ran out of guesses! The letter was " + guessLetter).queue();
                    endGuessingGame();
                }
                if(win) {
                    event.getChannel().sendMessage("Yes you've guessed it! The letter was " + guessLetter).queue();
                    endGuessingGame();
                }
                if(guessingGame && !win) {
                    Emoji cross = EmojiManager.getForAlias("x");
                    event.getMessage().addReaction(cross.getUnicode()).queue();
                    Emoji number = EmojiManager.getForAlias(getWordOfNum(guessCount));
                    event.getMessage().addReaction(number.getUnicode()).queue();
                    System.out.println(guessLetter);
                }
            }
        }
    }

    public void startGuessingGame() {
        guessingGame = true;
        Random r = new Random();
        guessLetter = (char) (r.nextInt(26) + 'a');
        guessCount = 10;
    }

    public boolean guess(String s) {
        boolean win = false;
        if(s.charAt(2) == guessLetter) {
            win = true;
        }
        guessCount -= 1;
        return win;
    }

    public void endGuessingGame() {
        guessingGame = false;
        guessLetter = ' ';
        LetterCmd.runGame = false;
    }

    String getWordOfNum(int number) {
        Map<Integer, String> numbers = new HashMap<>();
        String num = null;

        numbers.put(1, "one");
        numbers.put(2, "two");
        numbers.put(3, "three");
        numbers.put(4, "four");
        numbers.put(5, "five");
        numbers.put(6, "six");
        numbers.put(7, "seven");
        numbers.put(8, "eight");
        numbers.put(9, "nine");
        numbers.put(10, "ten");

        for (int x : numbers.keySet()) {
            if(x == number) {
                num = numbers.get(x);
            }
        }
        return num;
    }

}
