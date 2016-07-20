package scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ui.ScraperGUI;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static scraper.DateTimeUtilities.*;
import static scraper.ScrapeElements.*;
import static scraper.StringUtilities.*;

public class ScraperRunner implements Scraper {

    private static String WEBSITE_URL;
    private Document WEBSITE_TO_SCRAPE;
    private static Term term;
    private static final String USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final String REFFERAL_URL = "http://www.google.com";

    public ScraperRunner(Term term) {
        WEBSITE_URL = URLBuilder.createURLForTermOnly(term);
        this.term = term;
    }

    public static Term getTerm() {
        return term;
    }

    public Document getWebsiteToScrape() throws IOException {
        WEBSITE_TO_SCRAPE = retrieveWebpage(WEBSITE_URL);
        return WEBSITE_TO_SCRAPE;
    }

    public static int getNumberOfClasses(Document document) {
        Elements numberOfClassesInHTML = document.select(HTMLElements.NUMBER_OF_CLASSES.getHtml());
        return Integer.parseInt(extractTextBetweenParentheses(numberOfClassesInHTML));
    }

    public Document generateDocumentForTerm(Term term) throws IOException {
        String url = URLBuilder.createURLForTermOnly(term);
        return Jsoup.connect(url).get();
    }

    public static boolean verifyValidDocument(Document document) {
        try {
            Elements allClasses = document.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
            return allClasses.size() != 0;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public static Document retrieveWebpage(String URL) throws IOException {
        Connection.Response response = Jsoup.connect(URL)
                .ignoreContentType(true)
                .userAgent(USER_AGENT_STRING)
                .referrer(REFFERAL_URL)
                .timeout(12000)
                .followRedirects(true)
                .execute();
        return response.parse();
    }

    public static void run() throws IOException {
        try {
            Document entireWebPage = retrieveWebpage(WEBSITE_URL);
            while (verifyValidDocument(entireWebPage)) {
                List<Class> allClasses = convertDocumentToClasses(entireWebPage);
                DatabaseOperations.performDatabaseActions(allClasses);
                WEBSITE_URL = URLBuilder.incrementPageNumber(WEBSITE_URL);
                entireWebPage = retrieveWebpage(WEBSITE_URL);
                ScraperGUI.appendToLoggerTextArea("Starting next page: " + WEBSITE_URL);
            }
            ScraperGUI.appendToLoggerTextArea("Finished Scraping");
        }
        catch (Exception e1) {
            ScraperGUI.appendToLoggerTextArea(e1.getMessage());
        }
    }

    public static List<Class> convertDocumentToClasses(Document doc) {
        return doc.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                  .stream()
                  .map(ScraperRunner::convertElementToAClass)
                  .collect(Collectors.toList());
    }

    public static Class convertElementToAClass(Element aClass) {
        Term            termID              = getTerm();
        String          classTitle          = getCourseTitle(aClass);
        String          className           = getCourseName(aClass);
        String          departmentName      = splitBySpaceAndExtractHalf(className, true);
        String          departmentCourseNum = splitBySpaceAndExtractHalf(className, false);
        Class.Status    classStatus         = getCourseStatusOpenOrClosed(aClass);
        String          courseNumber        = getCourseNumber(aClass);
        int             seatsTaken          = getNumberOfSeatsTaken(aClass);
        int             seatsTotal          = getNumberOfTotalSeats(aClass);
        int             seatsAvailable      = seatsTotal - seatsTaken;
        Date            classStartDate      = transformStringDateIntoSQLFormat(getClassStartDate(aClass));
        Date            classEndDate        = transformStringDateIntoSQLFormat(getClassEndDate(aClass));
        String          attributes          = getClassAttributes(aClass);
        Time            classStartTime      = transformStringTimeIntoSQLFormat(getClassStartTime(aClass));
        Time            classEndTime        = transformStringTimeIntoSQLFormat(getClassEndTime(aClass));
        boolean         isMondayClass       = isMondayClass(aClass);
        boolean         isTuesdayClass      = isTuesdayClass(aClass);
        boolean         isWednesdayClass    = isWednesdayClass(aClass);
        boolean         isThursdayClass     = isThursdayClass(aClass);
        boolean         isFridayClass       = isFridayClass(aClass);
        boolean         isSaturdayClass     = isSaturdayClass(aClass);
        boolean         isSundayClass       = isSundayClass(aClass);
        String          instructorName      = getInstructorName(aClass);
        String          instructorEmail     = getInstructorEmail(aClass);
        String          location            = getLocation(aClass);
        String          room                = getRoom(aClass);
        String          format              = getFormat(aClass);
        String          description         = getDescription(aClass);
        String          duration            = getClassDuration(aClass);
        String          session             = getSession(aClass);
        String          component           = getClassComponent(aClass);
        String          syllabus            = getSyllabus(aClass);
        return new Class(termID, classTitle, departmentName, departmentCourseNum, classStatus, courseNumber, seatsTaken, seatsAvailable,
                seatsTotal, classStartDate, classEndDate, attributes, classStartTime, classEndTime,
                isMondayClass, isTuesdayClass, isWednesdayClass, isThursdayClass, isFridayClass, isSaturdayClass,
                isSundayClass, instructorName, instructorEmail, location, room, format,
                description, duration, session, component, syllabus);
    }

}
