package Commands.School;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.*;

public class Math extends Command {

    public Math() {
        this.name = "math";
        this.arguments = "<equation>";
        this.category = new Category("School");
    }

    @Override
    protected void execute(CommandEvent event) {
        String msg = event.getMessage().getContentRaw();
        List<Double> numbers = new ArrayList<>();
        do {
            String temp = event.getMessage().getContentRaw().replace("!math", "");
            temp = event.getMessage().getContentRaw().replaceAll(" ", "");

            for (Character chars : temp.toCharArray()) {
                if(Character.isDigit(chars)) {
                    numbers.add(Double.valueOf(chars));
                }
            }
        } while(checkString(msg));

    }

    double multiply(double x, double y) {
        return x * y;
    }

    double divide(double x, double y) {
        return x / y;
    }

    double add(double x, double y) {
        return x + y;
    }

    double subtract(double x, double y) {
        return x - y;
    }

    double power(double x, double y) {
        return java.lang.Math.pow(x, y);
    }

    boolean checkString(String s) {
        boolean hasOps = false;

        for (char c : s.toCharArray()) {
            switch (c) {
                case '/':
                case '*':
                case '^':
                case '+':
                case '-':
                    hasOps = true;
            }
        }
        return hasOps;
    }
}
