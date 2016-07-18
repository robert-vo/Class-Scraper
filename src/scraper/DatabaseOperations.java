package scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ui.ScraperGUI;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

    public static void performDatabaseActions(List<Class> allClasses) throws SQLException, ClassNotFoundException {
        //if it exists in database, update information

        //else, insert into database
        allClasses.stream().forEach((c) -> {
            try {
                DatabaseOperations.insertIntoDatabase(c);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

}

/*
CREATE TABLE CLASS (
	Class_ID            INT     PRIMARY KEY AUTO_INCREMENT,
    Term_ID             INT,
    Semester            ENUM('Fall', 'Summer', 'Spring'),
    Year				INT,
	Title               VARCHAR(100),
    CRN                 INT     ,
    Name                VARCHAR(100)    NOT NULL,
    Status              ENUM('Open', 'Closed') ,
    ATTRIBUTES          VARCHAR(100),
    DATES               VARCHAR(100),
    DAYS_TIMES          VARCHAR(100),
    INSTRUCTOR          VARCHAR(100),
    INSTRUCTOR_EMAIL    VARCHAR(100),
    LOCATION            VARCHAR(100),
    ROOM                VARCHAR(100),
    FORMAT              VARCHAR(100),
    DESCRIPTION         VARCHAR(500),
    DURATION            VARCHAR(100),
    SESSION             VARCHAR(100),
    COMPONENT           VARCHAR(100),
    SYLLABUS            VARCHAR(200),
    SEATS_TAKEN 		INT,
    SEATS_AVAILABLE		INT,
    SEATS_TOTAL			INT,
    MONDAY				BOOLEAN DEFAULT FALSE,
    TUESDAY				BOOLEAN DEFAULT FALSE,
    WEDNESDAY			BOOLEAN DEFAULT FALSE,
    THURSDAY			BOOLEAN DEFAULT FALSE,
    FRIDAY				BOOLEAN DEFAULT FALSE,
	SATURDAY			BOOLEAN DEFAULT FALSE,
    SUNDAY				BOOLEAN DEFAULT FALSE,
    ONLINE_COURSE		BOOLEAN DEFAULT FALSE
);
 */