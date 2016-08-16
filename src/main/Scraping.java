package main;

import ebay.Ui;
import googletrend.GoogleParser;
import selenium.Selenium;
import utils.PrintHelper;
import utils.Utility;

public class Scraping {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.setUpUi();
//        GoogleParser.parse("bluetooth headphones");
//        String url = "https://www.google.com/trends/api/widgetdata/relatedsearches?hl=en-US&tz=-345&req=%7B%22restriction%22:%7B%22geo%22:%7B%7D,%22time%22:%222004-01-01+2016-08-15%22,%22complexKeywordsRestriction%22:%7B%22keyword%22:%5B%7B%22type%22:%22BROAD%22,%22value%22:%22bluetooth+speakers%22%7D%5D%7D%7D,%22keywordType%22:%22QUERY%22,%22metric%22:%5B%22TOP%22,%22RISING%22%5D,%22trendinessSettings%22:%7B%22compareTime%22:%222004-01-01+2005-01-01%22%7D,%22requestOptions%22:%7B%22property%22:%22%22,%22backend%22:%22IZG%22,%22category%22:0%7D%7D&token=APP6_UEAAAAAV7LklS8kSd2_6aDhbu1C33q26UJ-rNCb";
//        String response = Utility.readStringFromUrl(url);
//        PrintHelper.print(response);
//        Selenium.parse();
    }
}