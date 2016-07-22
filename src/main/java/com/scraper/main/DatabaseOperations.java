package com.scraper.main;

import com.scraper.ui.ScraperGUI;

import java.lang.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseOperations {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL      = "jdbc:mysql://localhost/class?useSSL=false";
    static final String USER        = "root";
    static final String PASS        = "password";

    public static void insertIntoDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
        final String insertClassIntoDatabase = "INSERT INTO CLASS(Term_ID, " +
                "Title, CRN, Department, Department_CRN, Status, ATTRIBUTES, START_DATE, END_DATE, " +
                "START_TIME, END_TIME, INSTRUCTOR, INSTRUCTOR_EMAIL, LOCATION, BUILDING_ABBV, " +
                "BUILDING_ROOM_NUM, FORMAT, DESCRIPTION, DURATION, SESSION, COMPONENT, SYLLABUS, SEATS_TAKEN, " +
                "SEATS_AVAILABLE, SEATS_TOTAL, MONDAY, TUESDAY, WEDNESDAY, " +
                "THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = prepareStatementForFields(c, conn, insertClassIntoDatabase);
        ps.executeUpdate();
    }

    public static void performDatabaseActions(List<Class> allClasses) throws SQLException, ClassNotFoundException {
        allClasses.stream().forEach((c) -> {
            try {
                initializeDatabaseActions(c);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static void updateClassInDatabase(Class c, java.sql.Connection conn) throws SQLException, ClassNotFoundException {
        final String updateClassInDatabase = "UPDATE CLASS SET " +
                "Term_ID = ?, Title = ?, CRN = ?, Department = ?, Department_CRN = ?, Status = ?, " +
                "ATTRIBUTES = ?, START_DATE = ?, END_DATE = ?, START_TIME = ?, END_TIME = ?," +
                "INSTRUCTOR = ?, INSTRUCTOR_EMAIL = ?, LOCATION = ?, BUILDING_ABBV = ?, BUILDING_ROOM_NUM = ?," +
                "FORMAT = ?, DESCRIPTION = ?, DURATION = ?, SESSION = ?, COMPONENT = ?, SYLLABUS = ?," +
                "SEATS_TAKEN = ?, SEATS_AVAILABLE = ?, SEATS_TOTAL = ?," +
                "MONDAY = ?, TUESDAY = ?, WEDNESDAY = ?, THURSDAY = ?, FRIDAY = ?, SATURDAY = ?, SUNDAY = ? " +
                "WHERE (Term_ID = ? AND CRN = ? AND Department = ?);";
        PreparedStatement preparedStatement = prepareStatementForFields(c, conn, updateClassInDatabase);
        preparedStatement.setString (33, c.getTerm().getTermID());
        preparedStatement.setString (34, c.getCourseNumber());
        preparedStatement.setString (35, c.getDepartmentAbbreviation());
        preparedStatement.executeUpdate();
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

    private static void initializeDatabaseActions(Class c) throws SQLException, ClassNotFoundException {
        java.lang.Class.forName(JDBC_DRIVER);
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
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

    private static PreparedStatement prepareStatementForFields(Class c, java.sql.Connection conn, String sqlQuery) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ps.setString (1, c.getTerm().getTermID());
        ps.setString (2, c.getClassTitle());
        ps.setString (3, c.getCourseNumber());
        ps.setString (4, c.getDepartmentAbbreviation());
        ps.setString (5, c.getDepartmentCourseNumber());
        ps.setString (6, c.getClassStatus().toString());
        ps.setString (7, c.getAttributes());
        ps.setDate   (8, c.getClassStartDate());
        ps.setDate   (9, c.getClassEndDate());
        ps.setTime   (10, c.getClassStartTime());
        ps.setTime   (11, c.getClassEndTime());
        ps.setString (12, c.getInstructorName());
        ps.setString (13, c.getInstructorEmail());
        ps.setString (14, c.getLocation());
        ps.setString (15, c.getBuildingAbbreviation());
        ps.setString (16, c.getBuildingRoomNumber());
        ps.setString (17, c.getFormat());
        ps.setString (18, c.getDescription());
        ps.setString (19, c.getDuration());
        ps.setString (20, c.getSession());
        ps.setString (21, c.getComponent());
        ps.setString (22, c.getSyllabus());
        ps.setInt    (23, c.getSeatsTaken());
        ps.setInt    (24, c.getSeatsAvailable());
        ps.setInt    (25, c.getSeatsTotal());
        ps.setBoolean(26, c.isMondayClass());
        ps.setBoolean(27, c.isTuesdayClass());
        ps.setBoolean(28, c.isWednesdayClass());
        ps.setBoolean(29, c.isThursdayClass());
        ps.setBoolean(30, c.isFridayClass());
        ps.setBoolean(31, c.isSaturdayClass());
        ps.setBoolean(32, c.isSundayClass());
        return ps;
    }

    private static void print(String message) {
        try {
            java.lang.Class.forName("ui.ScraperGUI");
            ScraperGUI.appendToLoggerTextArea(message);
        }
        catch (ClassNotFoundException e) {
            System.out.println(message);
        }

    }
}