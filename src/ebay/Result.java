package ebay;

import java.util.ArrayList;

/**
 * Created by Subash on 8/15/16.
 */
public class Result {
    private static final String NEW_LINE = "\n";
    private String resultQuery;
    private ArrayList<Data> datas;

    public Result(String resultQuery, ArrayList<Data> datas) {
        this.resultQuery = resultQuery;
        this.datas = datas;
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();
        builder.append(resultQuery);
        builder.append(NEW_LINE);
        for (Data d : datas) {
            builder.append(d.getString());
            builder.append(NEW_LINE);
        }
        return builder.toString();
    }
}
