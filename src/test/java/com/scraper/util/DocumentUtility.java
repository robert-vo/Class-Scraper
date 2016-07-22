package com.scraper.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class DocumentUtility {
    public static Document returnDocumentFromFileName(String fileName) throws IOException {
        File page = new File("src/test/resources/" + fileName + ".html");
        return Jsoup.parse(page, "UTF-8", "");
    }
}
