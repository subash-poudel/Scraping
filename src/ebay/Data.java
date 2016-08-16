package ebay;

import java.util.ArrayList;

/**
 * Created by Subash on 8/14/16.
 */
public class Data {
    public static final String NEW_LINE = "\n";
    private String name;
    private ArrayList<String> items;

    private static final String[] IGNORED_STRING = {"unbranded", "not specified"};


    public Data(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void add(String item) {
        items.add(item);
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(":");
        builder.append(NEW_LINE);
        builder.append(NEW_LINE);
        for (String s : items) {
            if (ignore(s)) {
                continue;
            }
            builder.append(s);
            builder.append(NEW_LINE);
        }
        return builder.toString();
    }

    private boolean ignore(String s) {
        for (String ignore : IGNORED_STRING) {
            if (s.equalsIgnoreCase(ignore)) {
                return true;
            }
        }
        return false;
    }
}
