package scraper;

import ui.ScraperGUI;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseOperations {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/class?useSSL=false";
    static final String USER = "root";
    static final String PASS = "password";

    public static void insertIntoDatabase(Class c) throws SQLException, ClassNotFoundException {
        java.lang.Class.forName(JDBC_DRIVER);

        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            final String insertClassIntoDatabase = "INSERT INTO CLASS(Term_ID, " +
                    "Title, CRN, Name, Status, ATTRIBUTES, START_DATE, END_DATE, " +
                    "START_TIME, END_TIME, INSTRUCTOR, INSTRUCTOR_EMAIL, LOCATION, ROOM, " +
                    "FORMAT, DESCRIPTION, DURATION, SESSION, COMPONENT, SYLLABUS, SEATS_TAKEN, " +
                    "SEATS_AVAILABLE, SEATS_TOTAL, MONDAY, TUESDAY, WEDNESDAY, " +
                    "THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                    ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(insertClassIntoDatabase);
            preparedStatement.setInt    (1, Integer.parseInt(c.getTermID().getTermID()));
            preparedStatement.setString (2, c.getClassTitle());
            preparedStatement.setString (3, c.getCourseNumber());
            preparedStatement.setString (4, c.getClassName());
            preparedStatement.setString (5, c.getClassStatus().toString());
            preparedStatement.setString (6, c.getAttributes());

            String startDate = c.getClassStartDate();
            String endDate = c.getClassEndDate();

            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date startDate1 = format.parse(startDate);
            Date endDate1 = format.parse(endDate);

            preparedStatement.setDate (7, new java.sql.Date(startDate1.getTime()));
            preparedStatement.setDate (8, new java.sql.Date(endDate1.getTime()));

            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm a", Locale.ENGLISH); // 12 hour format
            java.sql.Time ppstime1 = null;
            java.sql.Time ppstime2 = null;

            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

            try {
                ppstime1 = new java.sql.Time(date12Format.parse(c.getClassStartTime().replace(".", "")).getTime());
                ppstime2 = new java.sql.Time(date12Format.parse(c.getClassEndTime().replace(".", "")).getTime());
            }
            catch (Exception e) {
                ScraperGUI.appendToLoggerTextArea("Invalid date");
            }

            preparedStatement.setTime (9, ppstime1);
            preparedStatement.setTime (10, ppstime2);

            preparedStatement.setString (11, c.getInstructorName());
            preparedStatement.setString (12, c.getInstructorEmail());
            preparedStatement.setString (13, c.getLocation());
            preparedStatement.setString (14, c.getRoom());
            preparedStatement.setString (15, c.getFormat());
            preparedStatement.setString (16, c.getDescription());
            preparedStatement.setString (17, c.getDuration());
            preparedStatement.setString (18, c.getSession());
            preparedStatement.setString (19, c.getComponent());
            preparedStatement.setString (20, c.getSyllabus());
            preparedStatement.setInt    (21, c.getSeatsTaken());
            preparedStatement.setInt    (22, c.getSeatsAvailable());
            preparedStatement.setInt    (23, c.getSeatsTotal());
            preparedStatement.setBoolean(24, c.isMondayClass());
            preparedStatement.setBoolean(25, c.isTuesdayClass());
            preparedStatement.setBoolean(26, c.isWednesdayClass());
            preparedStatement.setBoolean(27, c.isThursdayClass());
            preparedStatement.setBoolean(28, c.isFridayClass());
            preparedStatement.setBoolean(29, c.isSaturdayClass());
            preparedStatement.setBoolean(30, c.isSundayClass());

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
use class;

DROP TABLE IF EXISTS DEPARTMENT;
DROP TABLE IF EXISTS CLASS;
DROP TABLE IF EXISTS TERMS;

CREATE TABLE DEPARTMENT (
	Department_Abbreviation	VARCHAR(5) PRIMARY KEY,
    Department_Name			varchar(75) NOT NULL
);

CREATE TABLE TERMS (
	Term_ID		INT PRIMARY KEY,
    Year		INT NOT NULL,
    Semester	ENUM('Fall', 'Summer', 'Spring')
);

CREATE TABLE CLASS (
	Class_ID            INT     PRIMARY KEY AUTO_INCREMENT,
    Term_ID             INT,
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
    ONLINE_COURSE		BOOLEAN DEFAULT FALSE,
    foreign key (Term_ID)
		REFERENCES TERMS(Term_ID)
        ON DELETE no action
);

 */

