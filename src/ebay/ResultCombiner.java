package ebay;

import googletrend.GoogleData;
import googletrend.GoogleParser;
import selenium.Selenium;
import utils.FileUtils;
import utils.PrintHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Subash on 8/15/16.
 */
public class ResultCombiner {
    public static class Combine {
        public String name;
        public ArrayList<String> googleResult;
        public Result ebayResult;
        public String ebayUrl;
        public String googleUrl;

        public String getEbayUrl() {
            return ebayUrl;
        }

        public void setEbayUrl(String ebayUrl) {
            this.ebayUrl = ebayUrl;
        }

        public String getGoogleUrl() {
            return googleUrl;
        }

        public void setGoogleUrl(String googleUrl) {
            this.googleUrl = googleUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<String> getGoogleResult() {
            return googleResult;
        }

        public void setGoogleResult(ArrayList<String> googleResult) {
            this.googleResult = googleResult;
        }

        public Result getEbayResult() {
            return ebayResult;
        }

        public void setEbayResult(Result ebayResult) {
            this.ebayResult = ebayResult;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(Data.NEW_LINE);
            if (ebayResult != null) {
                builder.append(ebayResult.getString());
                builder.append(Data.NEW_LINE);
                builder.append(Data.NEW_LINE);
                builder.append("Ebay Link:");
                builder.append(Data.NEW_LINE);
                builder.append(ebayUrl);
                builder.append(Data.NEW_LINE);
                builder.append(Data.NEW_LINE);
            }
            builder.append("Trending Related Searches for " + name + ":");
            builder.append(Data.NEW_LINE);
            builder.append(Data.NEW_LINE);
            for (String s : googleResult) {
                builder.append(s);
                builder.append(Data.NEW_LINE);
            }
            builder.append(Data.NEW_LINE);
            builder.append(Data.NEW_LINE);
            builder.append("Google Link:");
            builder.append(Data.NEW_LINE);
            builder.append(googleUrl);
            return builder.toString();
        }
    }

    public static void parse(String inputFilePath, String outputFilePath, String seleniumFilePath) {
        String fileContent = FileUtils.readFromFile(inputFilePath);
        String[] searchQuery = fileContent.split("\n");

        ArrayList<Combine> combineArrayList = new ArrayList<>(searchQuery.length);
        for (String search : searchQuery) {
            Combine combine = new Combine();
            combine.setName(search);
            Result result = EbayScraper.parseQuery(search);
            if (result != null) {
                combine.setEbayResult(result);
                combine.setEbayUrl(EbayScraper.getUrl(search));
            }
            ArrayList<String> list = Selenium.parse(search, seleniumFilePath);
            if (list != null && !list.isEmpty()) {
                combine.setGoogleResult(list);
                combine.setGoogleUrl(GoogleParser.getTrendsUrl(search));
            }
            combineArrayList.add(combine);
        }
        for (Combine combine : combineArrayList) {
            String outputFile = outputFilePath + File.separator + combine.getName() + ".txt";
            FileUtils.writeToFile(outputFile, combine.toString());
        }

    }
}
