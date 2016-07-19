package scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ui.ScraperGUI;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScraperRunner implements Scraper {

    private static String WEBSITE_URL;
    private Document WEBSITE_TO_SCRAPE;
    private static Term term;

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
        return Integer.parseInt(Scraper.extractTextBetweenParentheses(numberOfClassesInHTML));
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
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
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
        String          classTitle          = ScrapeElements.getCourseTitle(aClass);
        String          className           = ScrapeElements.getCourseName(aClass);
        Class.Status    classStatus         = ScrapeElements.getCourseStatusOpenOrClosed(aClass);
        String          courseNumber        = ScrapeElements.getCourseNumber(aClass);
        int             seatsTaken          = ScrapeElements.getNumberOfSeatsTaken(aClass);
        int             seatsTotal          = ScrapeElements.getNumberOfTotalSeats(aClass);
        int             seatsAvailable      = seatsTotal - seatsTaken;
        Semester        semester            = getTerm().getSemester();
        String          classStartDate      = ScrapeElements.getClassStartDate(aClass);
        String          classEndDate        = ScrapeElements.getClassEndDate(aClass);
        String          attributes          = ScrapeElements.getClassAttributes(aClass);
        String          classStartTime      = ScrapeElements.getClassStartTime(aClass);
        String          classEndTime        = ScrapeElements.getClassEndTime(aClass);
        boolean         isMondayClass       = ScrapeElements.isMondayClass(aClass);
        boolean         isTuesdayClass      = ScrapeElements.isTuesdayClass(aClass);
        boolean         isWednesdayClass    = ScrapeElements.isWednesdayClass(aClass);
        boolean         isThursdayClass     = ScrapeElements.isThursdayClass(aClass);
        boolean         isFridayClass       = ScrapeElements.isFridayClass(aClass);
        boolean         isSaturdayClass     = ScrapeElements.isSaturdayClass(aClass);
        boolean         isSundayClass       = ScrapeElements.isSundayClass(aClass);
        String          instructorName      = ScrapeElements.getInstructorName(aClass);
        String          instructorEmail     = ScrapeElements.getInstructorEmail(aClass);
        String          location            = ScrapeElements.getLocation(aClass);
        String          room                = ScrapeElements.getRoom(aClass);
        String          format              = ScrapeElements.getFormat(aClass);
        String          description         = ScrapeElements.getDescription(aClass);
        String          duration            = ScrapeElements.getClassDuration(aClass);
        String          session             = ScrapeElements.getSession(aClass);
        String          component           = ScrapeElements.getClassComponent(aClass);
        String          syllabus            = ScrapeElements.getSyllabus(aClass);
        return new Class(termID, classTitle, className, classStatus, courseNumber, seatsTaken, seatsAvailable,
                seatsTotal, semester, classStartDate, classEndDate, attributes, classStartTime, classEndTime,
                isMondayClass, isTuesdayClass, isWednesdayClass, isThursdayClass, isFridayClass, isSaturdayClass,
                isSundayClass, instructorName, instructorEmail, location, room, format,
                description, duration, session, component, syllabus);
    }

}
