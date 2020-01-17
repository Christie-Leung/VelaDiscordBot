package Commands.RandomStoof;

import java.util.ArrayList;

public class StringsList {

    String cmd;
    String output;
    ArrayList<StringsList> listOfStrings = new ArrayList<>();

    public StringsList(String command, String callOut) {
        this.cmd = command;
        this.output = callOut;
    }

    public void setListOfStrings(ArrayList<StringsList> listOfStrings) {

    }
}
