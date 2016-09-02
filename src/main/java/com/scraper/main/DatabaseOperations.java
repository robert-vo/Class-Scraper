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
import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;
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

    /**
     * Constructor to initialize the database credentials using the path of the configuration properties file.
     *
     * @param configPropertiesPath The path of the configuration properties file.
     * @throws IOException if the file does not exist, or can not be read due to it not being a being a folder.
     * @throws MissingResourceException if the file does not contain or is missing the necessary properties.
     *                                  The mandatory properties required are:
     *                                  jdbcDriver, databaseURL, userName, passWord.
     */
    public DatabaseOperations(String configPropertiesPath) throws IOException, MissingResourceException {
        this.configPropertiesPath = configPropertiesPath;
        loadPropertiesFile();
    }

    /**
     * Constructor to initialize the database credentials through the method arguments.
     *
     * @param jdbcDriver The database driver that allows Java to communicate with a database.
     *                   MySQL example: com.mysql.jdbc.Driver
     * @param databaseURL The URL, local or remote, where the database is located at.
     *                    Please include the database table name, e.g. jdbc:mysql://localhost/class
     * @param userName The username used to access the database.
     * @param passWord The password that supplements the username for access to the database.
     */
    public DatabaseOperations(String jdbcDriver, String databaseURL,
                              String userName, String passWord) {
        this.jdbcDriver = jdbcDriver;
        this.databaseURL = databaseURL;
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * Loads and sets the database credentials using the properties file.
     *
     * @throws IOException if the properties file does not exist, or could not be found.
     * @throws MissingResourceException if one of the properties is missing.
     */
    private void loadPropertiesFile() throws IOException, MissingResourceException {
        log.info("Loading database credentials using the properties file.");

        try (InputStream input = new FileInputStream(configPropertiesPath)) {
            properties.load(input);
            this.jdbcDriver     = properties.getProperty("jdbcDriver");
            this.databaseURL    = properties.getProperty("databaseURL");
            this.userName       = properties.getProperty("userName");
            this.passWord       = properties.getProperty("passWord");

            if(databaseCredentialsEmptyOrNull()) {
                throw new MissingResourceException("One of the database credentials has not been set using the properties file. " +
                        "Please double check the properties file to see if the credentials are valid.",
                        "DatabaseOperations", "");
            }

        }
        catch (IOException ioe) {
            log.error("Unable to load properties file at: " + configPropertiesPath + ". " +
                    "Please verify that the properties file is in the right location, " +
                    "or set the database credentials using a different method.");
        }
        catch (MissingResourceException mre) {
            log.error("One of the database credentials has not been set using the properties file. " +
                    "Please see the error: " + mre);
        }
        log.info("Set database credentials using the properties file.");
    }

    /**
     *
     * @param aClass
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void performUpdateOrInsertForClass(Class aClass) throws SQLException, ClassNotFoundException {
        final String currentClass = aClass.getClassTitle() + ", " +
                aClass.getDepartmentAbbreviation() + " " + aClass.getDepartmentCourseNumber() +
                "(" + aClass.getClassNumber() + ")";

        try (java.sql.Connection connection = DriverManager.getConnection(databaseURL, userName, passWord)) {

            if(isClassInDatabase(aClass, connection)) {
                log.info("The class, " + currentClass + " exists in database. " +
                        "Updating class entry if any changes have been made.");
                updateClassInDatabase(aClass, connection);
            }
            else {
                log.info("The class, " + currentClass + " does not exist in the database. " +
                        "Inserting new entry in the database.");
                insertClassIntoDatabase(aClass, connection);
            }
        }
        catch (Exception e) {
            log.error("Something has gone wrong during the SQL data manipulation queries. " +
                    "The class in that caused the exception is: " + currentClass + ". The error is: " + e);
        }
    }

    public void performUpdateOrInsertForAllClass(List<Class> allClasses) {
        long startTime = System.currentTimeMillis();
        long endTime;

        try {
            if (databaseCredentialsEmptyOrNull()) {
                throw new MissingFormatArgumentException("Database credentials are empty or null. Aborting database operations.");
            }

            initializeJdbcDriver();

            allClasses.parallelStream().forEach((aClass) -> {
                try {
                    performUpdateOrInsertForClass(aClass);
                } catch (SQLException | ClassNotFoundException e) {
                    log.error("An error has occurred while performing the database actions. The error is: " + e);
                }
            });

            endTime = System.currentTimeMillis();

            log.debug("Time taken to perform database operations for " + allClasses.size() +
                    " is " + String.valueOf(endTime - startTime) + " milliseconds.");
            log.info("Database operations complete!");
        }
        catch (ClassNotFoundException cnfe) {
            log.error("The jdbc driver is not valid. Error message is: " + cnfe);
        }
        catch (MissingFormatArgumentException e) {
            log.error("The database credentials have not been set with error: " + e);
        }

    }

    private boolean isClassInDatabase(Class aClass, java.sql.Connection connection) throws SQLException, ClassNotFoundException {
        final String getNumberOfOccurrences = "SELECT COUNT(*) FROM CLASS " +
                "WHERE (TERM_ID = ? AND CRN = ? AND DEPARTMENT = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(getNumberOfOccurrences);

        preparedStatement.setString (1, aClass.getTerm().getTermID());
        preparedStatement.setString (2, aClass.getClassNumber());
        preparedStatement.setString (3, aClass.getDepartmentAbbreviation());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if(resultSet.getInt(1) > 0) {
                return true;
            }
        }

        return false;
    }

    private void insertClassIntoDatabase(Class aClass, java.sql.Connection connection) throws SQLException, ClassNotFoundException {
        final String insertClassInformationIntoDatabase = "INSERT IGNORE INTO CLASS_INFORMATION(DEPARTMENT, " +
                "DEPARTMENT_CRN, CLASS_TITLE, CLASS_DESCRIPTION) VALUES(" +
                "?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertClassInformationIntoDatabase);
        preparedStatement.setString(1, aClass.getDepartmentAbbreviation());
        preparedStatement.setString(2, aClass.getDepartmentCourseNumber());
        preparedStatement.setString(3, aClass.getClassTitle());
        preparedStatement.setString(4, aClass.getDescription());
        preparedStatement.executeUpdate();

        final String insertClassIntoDatabase = "INSERT INTO CLASS(Term_ID, " +
                "CRN, Department, Department_CRN, Status, ATTRIBUTES, START_DATE, END_DATE, " +
                "START_TIME, END_TIME, INSTRUCTOR, INSTRUCTOR_EMAIL, LOCATION, BUILDING_ABBV, " +
                "BUILDING_ROOM_NUM, FORMAT, DURATION, SESSION, COMPONENT, SYLLABUS, SEATS_TAKEN, " +
                "SEATS_AVAILABLE, SEATS_TOTAL, MONDAY, TUESDAY, WEDNESDAY, " +
                "THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = prepareStatementForCommonFields(aClass, connection, insertClassIntoDatabase);
        ps.executeUpdate();
    }

    private void updateClassInDatabase(Class aClass, java.sql.Connection connection) throws SQLException, ClassNotFoundException {
        final String updateClassInDatabase = "UPDATE CLASS SET " +
                "Term_ID = ?, CRN = ?, Department = ?, Department_CRN = ?, Status = ?, " +
                "ATTRIBUTES = ?, START_DATE = ?, END_DATE = ?, START_TIME = ?, END_TIME = ?," +
                "INSTRUCTOR = ?, INSTRUCTOR_EMAIL = ?, LOCATION = ?, BUILDING_ABBV = ?, BUILDING_ROOM_NUM = ?," +
                "FORMAT = ?, DURATION = ?, SESSION = ?, COMPONENT = ?, SYLLABUS = ?," +
                "SEATS_TAKEN = ?, SEATS_AVAILABLE = ?, SEATS_TOTAL = ?," +
                "MONDAY = ?, TUESDAY = ?, WEDNESDAY = ?, THURSDAY = ?, FRIDAY = ?, SATURDAY = ?, SUNDAY = ? " +
                "WHERE (Term_ID = ? AND CRN = ? AND Department = ?);";
        PreparedStatement preparedStatement = prepareStatementForCommonFields(aClass, connection, updateClassInDatabase);
        preparedStatement.setString (31, aClass.getTerm().getTermID());
        preparedStatement.setString (32, aClass.getClassNumber());
        preparedStatement.setString (33, aClass.getDepartmentAbbreviation());
        preparedStatement.executeUpdate();
    }

    private PreparedStatement prepareStatementForCommonFields(Class aClass, java.sql.Connection connection, String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString (1, aClass.getTerm().getTermID());
        preparedStatement.setString (2, aClass.getClassNumber());
        preparedStatement.setString (3, aClass.getDepartmentAbbreviation());
        preparedStatement.setString (4, aClass.getDepartmentCourseNumber());
        preparedStatement.setString (5, aClass.getClassStatus().toString());
        preparedStatement.setString (6, aClass.getAttributes());
        preparedStatement.setDate   (7, aClass.getClassStartDate());
        preparedStatement.setDate   (8, aClass.getClassEndDate());
        preparedStatement.setTime   (9, aClass.getClassStartTime());
        preparedStatement.setTime   (10, aClass.getClassEndTime());
        preparedStatement.setString (11, aClass.getInstructorName());
        preparedStatement.setString (12, aClass.getInstructorEmail());
        preparedStatement.setString (13, aClass.getLocation());
        preparedStatement.setString (14, aClass.getBuildingAbbreviation());
        preparedStatement.setString (15, aClass.getBuildingRoomNumber());
        preparedStatement.setString (16, aClass.getFormat());
        preparedStatement.setString (17, aClass.getDuration());
        preparedStatement.setString (18, aClass.getSession());
        preparedStatement.setString (19, aClass.getComponent());
        preparedStatement.setString (20, aClass.getSyllabus());
        preparedStatement.setInt    (21, aClass.getSeatsTaken());
        preparedStatement.setInt    (22, aClass.getSeatsAvailable());
        preparedStatement.setInt    (23, aClass.getSeatsTotal());
        preparedStatement.setBoolean(24, aClass.isMondayClass());
        preparedStatement.setBoolean(25, aClass.isTuesdayClass());
        preparedStatement.setBoolean(26, aClass.isWednesdayClass());
        preparedStatement.setBoolean(27, aClass.isThursdayClass());
        preparedStatement.setBoolean(28, aClass.isFridayClass());
        preparedStatement.setBoolean(29, aClass.isSaturdayClass());
        preparedStatement.setBoolean(30, aClass.isSundayClass());
        return preparedStatement;
    }

    private boolean databaseCredentialsEmptyOrNull() {
        if(jdbcDriver == null || databaseURL == null || userName == null || passWord == null) {
            log.info("Database credentials are currently null. Setting credentials using properties file.");
            return true;
        }
        else {
            log.info("Database credentials are not null. Proceeding with database operations.");
            return false;
        }
    }

    private void initializeJdbcDriver() throws ClassNotFoundException {
        try {
            log.info("Attempting to set jdbc driver...");
            java.lang.Class.forName(jdbcDriver);
            log.info("Jdbc driver set!");
        }
        catch (ClassNotFoundException e) {
            log.error("Invalid jdbcDriver for given driver: " + jdbcDriver);
        }
    }

    @Override
    public void close() throws Exception {
        log.debug("Closing database operations resource.");
    }
}