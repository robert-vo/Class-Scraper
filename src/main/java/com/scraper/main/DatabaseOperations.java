package com.scraper.main;

import com.scraper.ui.ScraperGUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DatabaseOperations {

    static Properties properties = new Properties();
    static String jdbcDriver;
    static String databaseURL;
    static String databaseTable;
    static String userName;
    static String passWord;
    static String configPropertiesPath;

    public static void setPropertiesFileLocation(String path) {
        configPropertiesPath = path;
    }

    public static void setJdbcDriver(String jdbcDriver) {
        DatabaseOperations.jdbcDriver = jdbcDriver;
    }

    public static void setDatabaseURL(String databaseURL) {
        DatabaseOperations.databaseURL = databaseURL;
    }

    public static void setDatabaseTable(String databaseTable) {
        DatabaseOperations.databaseTable = databaseTable;
    }

    public static void setUserName(String userName) {
        DatabaseOperations.userName = userName;
    }

    public static void setPassWord(String passWord) {
        DatabaseOperations.passWord = passWord;
    }

    private static void loadPropertiesFile() throws IOException {
        InputStream input = new FileInputStream(configPropertiesPath);
        properties.load(input);
        setJdbcDriver   (properties.getProperty("jdbcDriver"));
        setDatabaseTable(properties.getProperty("databaseTable"));
        setDatabaseURL  (properties.getProperty("databaseURL") +  "/" + databaseTable);
        setUserName     (properties.getProperty("userName"));
        setPassWord     (properties.getProperty("passWord"));
    }

    private static void initializeDatabaseActions(Class c) throws SQLException, ClassNotFoundException {
        try {
            if(jdbcDriver.isEmpty() || jdbcDriver.equals("")) {
                loadPropertiesFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.lang.Class.forName(jdbcDriver);

        try (java.sql.Connection conn = DriverManager.getConnection(databaseURL, userName, passWord)) {
            print("Checking if the class, " + c.getClassTitle() + " is already in the database.");
            if(isClassInDatabase(c, conn)) {
                print("Class, " + c.getClassTitle() + " exists in database. Updating class.");
                updateClassInDatabase(c, conn);
            }
            else {
                print("Class, " + c.getClassTitle() + " does not exist in the database. Inserting new row.");
                insertIntoDatabase(c, conn);
            }
        }
        catch (Exception e1) {
            print(e1.getMessage());
        }
    }

    public static void performDatabaseActions(List<Class> allClasses) {
        allClasses.parallelStream().forEach((c) -> {
            try {
                initializeDatabaseActions(c);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static boolean isClassInDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
        boolean doesClassExist = false;
        final String getNumberOfOccurrences = "SELECT COUNT(*) FROM CLASS " +
                "WHERE (TERM_ID = ? AND CRN = ? AND DEPARTMENT = ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(getNumberOfOccurrences);

        preparedStatement.setString (1, c.getTerm().getTermID());
        preparedStatement.setString (2, c.getCourseNumber());
        preparedStatement.setString (3, c.getDepartmentAbbreviation());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if(resultSet.getInt(1) > 0) {
                doesClassExist = true;
            }
        }

        return doesClassExist;
    }

    private static void insertIntoDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
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

    private static void updateClassInDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
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
        preparedStatement.setString (32, c.getCourseNumber());
        preparedStatement.setString (33, c.getDepartmentAbbreviation());
        preparedStatement.executeUpdate();
    }

    private static PreparedStatement prepareStatementForFields(Class c, java.sql.Connection conn, String sqlQuery) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ps.setString (1, c.getTerm().getTermID());
        ps.setString (2, c.getCourseNumber());
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

    private static void print(String message) {
        try {
            java.lang.Class.forName("com.scraper.ui.ScraperGUI");
            ScraperGUI.appendToLoggerTextArea(message);
        }
        catch (ClassNotFoundException e) {
            System.out.println(message);
        }
    }
}