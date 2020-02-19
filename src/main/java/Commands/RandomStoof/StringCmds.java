package Commands.RandomStoof;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;

public class StringCmds {

    public String cmd;
    public String output;
    public static ArrayList<StringCmds> listOfStrings = new ArrayList<>();

    public StringCmds(String command, String callOut) {
        this.cmd = command;
        this.output = callOut;
    }

    public static void setListOfStrings() {
        listOfStrings.add(new StringCmds("racool", "Yes Racoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooool"));
        listOfStrings.add(new StringCmds("server", "Yes ServerRakoooooooooooooooooooooooooooooooooooliooooooooooooooooooooooooooooooooooooooooo"));
        listOfStrings.add(new StringCmds("carter", "Yessssssssssssss Carterrererererererererereerererererererererererererererererererererererererererererererererer"));
        listOfStrings.add(new StringCmds("keith", "Yesssss Kee e e ee e e ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei ei eith"));
        listOfStrings.add(new StringCmds("christie", "Yesssssssssssssssssssssssss Christieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"));
        listOfStrings.add(new StringCmds("ree", "Ree e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e ee e e e e e e e ee e e e e e"));
        listOfStrings.add(new StringCmds("wrathcarter", "__***THE WRATH OF CARTER BAGUETTE***__\n" +
                ":cloud_lightning: :fire: :oncoming_police_car: :police_car: :oncoming_police_car: :regional_indicator_c: :regional_indicator_a: :regional_indicator_r: :regional_indicator_t: :regional_indicator_e: :regional_indicator_r: :oncoming_police_car: :police_car: :oncoming_police_car: :fire: :cloud_lightning:"));
        listOfStrings.add(new StringCmds("josh", "Yesssssssssssssssssssssssssss Jooooooooooooooooooooooooooooooooooooooooooooooooosh"));
        listOfStrings.add(new StringCmds("racuwu", "Yesssssssssssssssssssssss Racuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwuwul"));
        listOfStrings.add(new StringCmds("die", "<:mercy_assassinate:631336123678982145>"));
        listOfStrings.add(new StringCmds("tea", "<:mercy_tea:631336204763398144>"));
        listOfStrings.add(new StringCmds("ew", "<:keylastupidface:630989473554890752>"));
        listOfStrings.add(new StringCmds("catblob", "<a:meow:667208085533753344>"));
        listOfStrings.add(new StringCmds("pepepagun", "<a:PepegaGun:667211084595462165>"));
        listOfStrings.add(new StringCmds("peepoclap", "<a:peepoClap:667211297573568552>"));
        listOfStrings.add(new StringCmds("feelsrainman", "<a:FeelsRainMan:667211370466377760>"));
        listOfStrings.add(new StringCmds("hackermans", "<a:HACKERMANS:667211334638764043>"));
        listOfStrings.add(new StringCmds("firepanda", "<a:FirePanda:667209510745669662>"));
        listOfStrings.add(new StringCmds("kirbdance", "<a:KirbDance:667211208020983809>"));
        listOfStrings.add(new StringCmds("monkaextreme", "<a:monkaExtreme:667211256347885578>"));
        listOfStrings.add(new StringCmds("monkaomega", "<a:monkaOmega:667211149057589251>"));
        listOfStrings.add(new StringCmds("ohyeah", "https://tenor.com/GeSt.gif"));
        listOfStrings.add(new StringCmds("disappointed", "https://tenor.com/66gI.gif"));
        listOfStrings.add(new StringCmds("distewmuch", "https://tenor.com/wXIe.gif"));
        listOfStrings.add(new StringCmds("idgaf", "https://tenor.com/phPD.gif"));
        listOfStrings.add(new StringCmds("idiot", "https://tenor.com/rEQV.gif"));
        listOfStrings.add(new StringCmds("indeed", "https://tenor.com/s1Ve.gif"));
        listOfStrings.add(new StringCmds("orlly", "https://tenor.com/bbnbo.gif"));
        listOfStrings.add(new StringCmds("owow", "https://tenor.com/1zzr.gif"));
        listOfStrings.add(new StringCmds("pray", "https://tenor.com/T5O9.gif"));
        listOfStrings.add(new StringCmds("sigh", "https://tenor.com/wYwq.gif"));
        listOfStrings.add(new StringCmds("sighh", "https://tenor.com/Uipp.gif"));
        listOfStrings.add(new StringCmds("yes", "https://tenor.com/7zZn.gif"));
        listOfStrings.add(new StringCmds(">:v", "<:latino:631336237197688833>"));
    }

    public static EmbedBuilder getListOfStrings() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("All Keywords");
        eb.setColor(new Color(183, 161, 255));
        for (StringCmds s : listOfStrings) {
            eb.appendDescription(s.cmd);
            if(listOfStrings.indexOf(s) != listOfStrings.size() - 1) {
                eb.appendDescription(" | ");
            }
        }
        return eb;
    }

    public static void addStringCmds(String cmd, String output) {
        listOfStrings.add(new StringCmds(cmd, output));
    }

    public static void removeStringCmds(String cmd) {
        int index = -1;
        for (StringCmds s : listOfStrings) {
            if(s.cmd.equalsIgnoreCase(cmd)) {
                index = listOfStrings.indexOf(s);
            }
        }
        if(index > 0) {
            listOfStrings.remove(index);
        }
    }
}
