package Commands;

import java.util.ArrayList;
import java.util.List;

public class Essential {

    public static String getLongString(String msg, int index) {
        String target = "";
        if (msg.contains("\"")) {
            String[] split = msg.split("\"\\s*");
            target = split[index];
        } else {
            String[] split = msg.split("\\s+");
            target = split[index];
        }
        return target;
    }

    public static List<String> getIndexes(String msg, List<String> list1) {
        String[] split = msg.split(",\\s*");
        ArrayList<String> list = new ArrayList<>(list1);
        for (String s : split) {
            try {
                int index = Integer.parseInt(s);
                list.remove(index - 1);
            } catch (NumberFormatException ex) {
                System.out.println();
            } finally {
                String[] range = s.split("\\s*-\\s*");
                if (range.length > 1) {
                    try {
                        int index1 = Integer.parseInt(range[0]);
                        int index2 = Integer.parseInt(range[1]);
                        for (; index1 < index2; index2--) {
                            list.remove(index1 - 1);
                        }
                    } finally {
                        System.out.println();
                    }
                }
            }
        }
        return list;
    }
}
