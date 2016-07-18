package scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ui.ScraperGUI;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Robert on 7/17/16.
 */
public class DatabaseOperations {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/class?useSSL=false";
    static final String USER = "root";
    static final String PASS = "password";

    public static void insertIntoDatabase(Class c) throws SQLException, ClassNotFoundException {
        java.lang.Class.forName(JDBC_DRIVER);

        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO CLASS(Term_ID, Semester, Title, CRN, Name, Status, Attributes, Dates, " +
                    "Days_Times, Instructor, Instructor_Email, Location, Room, Format, Description, Duration, " +
                    "Session, Component, Syllabus) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, c.getTermID().getTermID());
            preparedStatement.setString(2, c.getSemester().toString());
            preparedStatement.setString(3, c.getClassTitle());
            preparedStatement.setString(4, c.getCourseNumber());
            preparedStatement.setString(5, c.getClassName());
            preparedStatement.setString(6, c.getClassStatus().toString());
            preparedStatement.setString(7, c.getAttributes());
            preparedStatement.setString(8, c.getClassDates());
            preparedStatement.setString(9, c.getDaysTimes());
            preparedStatement.setString(10, c.getInstructorName());
            preparedStatement.setString(11, c.getInstructorEmail());
            preparedStatement.setString(12, c.getLocation());
            preparedStatement.setString(13, c.getRoom());
            preparedStatement.setString(14, c.getFormat());
            preparedStatement.setString(15, c.getDescription());
            preparedStatement.setString(16, c.getDuration());
            preparedStatement.setString(17, c.getSession());
            preparedStatement.setString(18, c.getComponent());
            preparedStatement.setString(19, c.getSyllabus());
            preparedStatement.executeUpdate();
            ScraperGUI.appendToLoggerTextArea(preparedStatement.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void performDatabaseActions(Elements e) {
        for (Element aClass : e) {
//            Term termID = getTerm();
            String classTitle = ScrapeElements.getCourseTitle(aClass);
            String className = ScrapeElements.getCourseName(aClass);
            Class.Status classStatus = ScrapeElements.getCourseStatusOpenOrClosed(aClass);
            String courseNumber = ScrapeElements.getCourseNumber(aClass);
            int seatsTaken = ScrapeElements.getNumberOfSeatsTaken(aClass);
            int seatsAvailable = ScrapeElements.getNumberOfAvailableSeats(aClass);
//            Semester semester = getTerm().getSemester();
            String classDates = ScrapeElements.getClassDates(aClass);
            String attributes = ScrapeElements.getClassAttributes(aClass);
            String daysTimes = ScrapeElements.getClassDaysAndTimes(aClass);
            String instructorName = ScrapeElements.getInstructorName(aClass);
            String instructorEmail = ScrapeElements.getInstructorEmail(aClass);
            System.out.println(instructorEmail);
            String location = ScrapeElements.getLocation(aClass);
            String room = ScrapeElements.getRoom(aClass);
            String format = ScrapeElements.getFormat(aClass);
            String description = ScrapeElements.getDescription(aClass);
            String duration = ScrapeElements.getClassDuration(aClass);
            String session = ScrapeElements.getSession(aClass);
            String component = ScrapeElements.getClassComponent(aClass);
            String syllabus = ScrapeElements.getSyllabus(aClass);
//            Class c = new Class(termID, classTitle, className, classStatus, courseNumber, seatsTaken,
//                    seatsAvailable, semester, classDates, attributes, daysTimes, instructorName,
//                    instructorEmail, location, room, format, description, duration, session,
//                    component, syllabus);
//            insertIntoDatabase(c);
        }
    }

}
