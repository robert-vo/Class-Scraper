package com.scraper.main;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * DatabaseOperations contains methods that sets the credentials needed for a database connection and
 * performs database a variety (select, update, insert) of SQL queries on a given database connection.
 *
 * @author Robert Vo
 */
public class DatabaseOperations implements AutoCloseable {

    Properties properties = new Properties();
    String jdbcDriver;
    String databaseURL;
    String userName;
    String passWord;
    String configPropertiesPath;
    private static Logger log = Logger.getLogger(DatabaseOperations.class);

    public DatabaseOperations(String configPropertiesPath) throws IOException {
        this.configPropertiesPath = configPropertiesPath;
        loadPropertiesFile();
    }

    public DatabaseOperations(String jdbcDriver, String databaseURL,
                              String userName, String passWord) throws IOException {
        this.jdbcDriver = jdbcDriver;
        this.databaseURL = databaseURL;
        this.userName = userName;
        this.passWord = passWord;
    }

    private void loadPropertiesFile() throws IOException {
        log.info("Loading database credentials using the properties file.");
        try(InputStream input = new FileInputStream(configPropertiesPath)) {
            properties.load(input);
            this.jdbcDriver     = properties.getProperty("jdbcDriver");
            this.databaseURL    = properties.getProperty("databaseURL");
            this.userName       = properties.getProperty("userName");
            this.passWord       = properties.getProperty("passWord");
        }
        catch (IOException e) {
            log.error("Unable to load properties file at: " + configPropertiesPath + ". " +
                    "Please verify that the properties file is in the right location, " +
                    "or set the database credentials using a different method.");
        }
        log.info("Set database credentials using the properties file.");
    }

    private void performUpdateOrInsertForClass(Class c) throws SQLException, ClassNotFoundException, IOException {
        loadPropertiesFileIfCredentialsNotSet();
        initializeJdbcDriver();

        final String currentClass = c.getClassTitle() + ", " +
                c.getDepartmentAbbreviation() + " " + c.getDepartmentCourseNumber();

        try (java.sql.Connection conn = DriverManager.getConnection(databaseURL, userName, passWord)) {

            if(isClassInDatabase(c, conn)) {
                log.info("The class, " + currentClass + " exists in database. Updating class.");
                updateClassInDatabase(c, conn);
            }
            else {
                log.info("The class, " + currentClass + " does not exist in the database. Inserting new row.");
                insertIntoDatabase(c, conn);
            }
        }
        catch (Exception e1) {
            log.error("Something has gone wrong during the SQL data manipulation queries. " +
                    "The class in that caused the exception is: " + currentClass + ". The error is: " + e1);
        }
    }

    public void performUpdateOrInsertForAllClass(List<Class> allClasses) {
        long startTime = System.currentTimeMillis();
        long endTime;

        allClasses.parallelStream().forEach((c) -> {
            try {
                performUpdateOrInsertForClass(c);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                log.error("An error has occurred while performing the database actions. The error is: " + e);
            }
        });

        endTime = System.currentTimeMillis();

        log.info("Time taken to perform database operations for " + allClasses.size() +
                 " is " + String.valueOf(endTime - startTime) + " milliseconds.");
        log.info("Database operations complete!");
    }

    private boolean isClassInDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
        final String getNumberOfOccurrences = "SELECT COUNT(*) FROM CLASS " +
                "WHERE (TERM_ID = ? AND CRN = ? AND DEPARTMENT = ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(getNumberOfOccurrences);

        preparedStatement.setString (1, c.getTerm().getTermID());
        preparedStatement.setString (2, c.getClassNumber());
        preparedStatement.setString (3, c.getDepartmentAbbreviation());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if(resultSet.getInt(1) > 0) {
                return true;
            }
        }

        return false;
    }

    private void insertIntoDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
        final String insertClassInformationIntoDatabase = "INSERT IGNORE INTO CLASS_INFORMATION(DEPARTMENT, " +
                "DEPARTMENT_CRN, CLASS_TITLE, CLASS_DESCRIPTION) VALUES(" +
                "?, ?, ?, ?);";
        PreparedStatement ps2 = conn.prepareStatement(insertClassInformationIntoDatabase);
        ps2.setString(1, c.getDepartmentAbbreviation());
        ps2.setString(2, c.getDepartmentCourseNumber());
        ps2.setString(3, c.getClassTitle());
        ps2.setString(4, c.getDescription());
        ps2.executeUpdate();

        final String insertClassIntoDatabase = "INSERT INTO CLASS(Term_ID, " +
                "CRN, Department, Department_CRN, Status, ATTRIBUTES, START_DATE, END_DATE, " +
                "START_TIME, END_TIME, INSTRUCTOR, INSTRUCTOR_EMAIL, LOCATION, BUILDING_ABBV, " +
                "BUILDING_ROOM_NUM, FORMAT, DURATION, SESSION, COMPONENT, SYLLABUS, SEATS_TAKEN, " +
                "SEATS_AVAILABLE, SEATS_TOTAL, MONDAY, TUESDAY, WEDNESDAY, " +
                "THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = prepareStatementForFields(c, conn, insertClassIntoDatabase);
        ps.executeUpdate();
    }

    private void updateClassInDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
        final String updateClassInDatabase = "UPDATE CLASS SET " +
                "Term_ID = ?, CRN = ?, Department = ?, Department_CRN = ?, Status = ?, " +
                "ATTRIBUTES = ?, START_DATE = ?, END_DATE = ?, START_TIME = ?, END_TIME = ?," +
                "INSTRUCTOR = ?, INSTRUCTOR_EMAIL = ?, LOCATION = ?, BUILDING_ABBV = ?, BUILDING_ROOM_NUM = ?," +
                "FORMAT = ?, DURATION = ?, SESSION = ?, COMPONENT = ?, SYLLABUS = ?," +
                "SEATS_TAKEN = ?, SEATS_AVAILABLE = ?, SEATS_TOTAL = ?," +
                "MONDAY = ?, TUESDAY = ?, WEDNESDAY = ?, THURSDAY = ?, FRIDAY = ?, SATURDAY = ?, SUNDAY = ? " +
                "WHERE (Term_ID = ? AND CRN = ? AND Department = ?);";
        PreparedStatement preparedStatement = prepareStatementForFields(c, conn, updateClassInDatabase);
        preparedStatement.setString (31, c.getTerm().getTermID());
        preparedStatement.setString (32, c.getClassNumber());
        preparedStatement.setString (33, c.getDepartmentAbbreviation());
        preparedStatement.executeUpdate();
    }

    private PreparedStatement prepareStatementForFields(Class c, java.sql.Connection conn, String sqlQuery) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ps.setString (1, c.getTerm().getTermID());
        ps.setString (2, c.getClassNumber());
        ps.setString (3, c.getDepartmentAbbreviation());
        ps.setString (4, c.getDepartmentCourseNumber());
        ps.setString (5, c.getClassStatus().toString());
        ps.setString (6, c.getAttributes());
        ps.setDate   (7, c.getClassStartDate());
        ps.setDate   (8, c.getClassEndDate());
        ps.setTime   (9, c.getClassStartTime());
        ps.setTime   (10, c.getClassEndTime());
        ps.setString (11, c.getInstructorName());
        ps.setString (12, c.getInstructorEmail());
        ps.setString (13, c.getLocation());
        ps.setString (14, c.getBuildingAbbreviation());
        ps.setString (15, c.getBuildingRoomNumber());
        ps.setString (16, c.getFormat());
        ps.setString (17, c.getDuration());
        ps.setString (18, c.getSession());
        ps.setString (19, c.getComponent());
        ps.setString (20, c.getSyllabus());
        ps.setInt    (21, c.getSeatsTaken());
        ps.setInt    (22, c.getSeatsAvailable());
        ps.setInt    (23, c.getSeatsTotal());
        ps.setBoolean(24, c.isMondayClass());
        ps.setBoolean(25, c.isTuesdayClass());
        ps.setBoolean(26, c.isWednesdayClass());
        ps.setBoolean(27, c.isThursdayClass());
        ps.setBoolean(28, c.isFridayClass());
        ps.setBoolean(29, c.isSaturdayClass());
        ps.setBoolean(30, c.isSundayClass());
        return ps;
    }


    private void loadPropertiesFileIfCredentialsNotSet() throws IOException {
        try {
            if(jdbcDriver == null || jdbcDriver.isEmpty() || jdbcDriver.equals("")) {
                loadPropertiesFile();
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    private void initializeJdbcDriver() throws ClassNotFoundException {
        try {
            java.lang.Class.forName(jdbcDriver);
        }
        catch (ClassNotFoundException e) {
            log.error("Invalid jdbcDriver for: " + jdbcDriver);
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing...");
    }
}