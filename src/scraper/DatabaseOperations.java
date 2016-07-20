package scraper;

import ui.ScraperGUI;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseOperations {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/class?useSSL=false";
    static final String USER = "root";
    static final String PASS = "password";

    public static void insertIntoDatabase(Class c) throws SQLException, ClassNotFoundException {
        java.lang.Class.forName(JDBC_DRIVER);

        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            final String insertClassIntoDatabase = "INSERT INTO CLASS(Term_ID, " +
                    "Title, CRN, Department, Department_CRN, Status, ATTRIBUTES, START_DATE, END_DATE, " +
                    "START_TIME, END_TIME, INSTRUCTOR, INSTRUCTOR_EMAIL, LOCATION, ROOM, " +
                    "FORMAT, DESCRIPTION, DURATION, SESSION, COMPONENT, SYLLABUS, SEATS_TAKEN, " +
                    "SEATS_AVAILABLE, SEATS_TOTAL, MONDAY, TUESDAY, WEDNESDAY, " +
                    "THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                    ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(insertClassIntoDatabase);
            preparedStatement.setInt    (1, Integer.parseInt(c.getTermID().getTermID()));
            preparedStatement.setString (2, c.getClassTitle());
            preparedStatement.setString (3, c.getCourseNumber());
            preparedStatement.setString (4, c.getDepartmentAbbreviation());
            preparedStatement.setString (5, c.getDepartmentCourseNumber());
            preparedStatement.setString (6, c.getClassStatus().toString());
            preparedStatement.setString (7, c.getAttributes());
            preparedStatement.setDate   (8, c.getClassStartDate());
            preparedStatement.setDate   (9, c.getClassEndDate());
            preparedStatement.setTime   (10, c.getClassStartTime());
            preparedStatement.setTime   (11, c.getClassEndTime());
            preparedStatement.setString (12, c.getInstructorName());
            preparedStatement.setString (13, c.getInstructorEmail());
            preparedStatement.setString (14, c.getLocation());
            preparedStatement.setString (15, c.getRoom());
            preparedStatement.setString (16, c.getFormat());
            preparedStatement.setString (17, c.getDescription());
            preparedStatement.setString (18, c.getDuration());
            preparedStatement.setString (19, c.getSession());
            preparedStatement.setString (20, c.getComponent());
            preparedStatement.setString (21, c.getSyllabus());
            preparedStatement.setInt    (22, c.getSeatsTaken());
            preparedStatement.setInt    (23, c.getSeatsAvailable());
            preparedStatement.setInt    (24, c.getSeatsTotal());
            preparedStatement.setBoolean(25, c.isMondayClass());
            preparedStatement.setBoolean(26, c.isTuesdayClass());
            preparedStatement.setBoolean(27, c.isWednesdayClass());
            preparedStatement.setBoolean(28, c.isThursdayClass());
            preparedStatement.setBoolean(29, c.isFridayClass());
            preparedStatement.setBoolean(30, c.isSaturdayClass());
            preparedStatement.setBoolean(31, c.isSundayClass());

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
            } catch (SQLException | ClassNotFoundException e) {
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

