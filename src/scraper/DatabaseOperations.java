package scraper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseOperations {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL      = "jdbc:mysql://localhost/class?useSSL=false";
    static final String USER        = "root";
    static final String PASS        = "password";

    public static void insertIntoDatabase(Class c) throws SQLException, ClassNotFoundException {
        java.lang.Class.forName(JDBC_DRIVER);
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            final String insertClassIntoDatabase = "INSERT INTO CLASS(Term_ID, " +
                    "Title, CRN, Department, Department_CRN, Status, ATTRIBUTES, START_DATE, END_DATE, " +
                    "START_TIME, END_TIME, INSTRUCTOR, INSTRUCTOR_EMAIL, LOCATION, BUILDING_ABBV, " +
                    "BUILDING_ROOM_NUM, FORMAT, DESCRIPTION, DURATION, SESSION, COMPONENT, SYLLABUS, SEATS_TAKEN, " +
                    "SEATS_AVAILABLE, SEATS_TOTAL, MONDAY, TUESDAY, WEDNESDAY, " +
                    "THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                    ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            preparedStatement.setString (15, c.getBuildingAbbreviation());
            preparedStatement.setString (16, c.getBuildingRoomNumber());
            preparedStatement.setString (17, c.getFormat());
            preparedStatement.setString (18, c.getDescription());
            preparedStatement.setString (19, c.getDuration());
            preparedStatement.setString (20, c.getSession());
            preparedStatement.setString (21, c.getComponent());
            preparedStatement.setString (22, c.getSyllabus());
            preparedStatement.setInt    (23, c.getSeatsTaken());
            preparedStatement.setInt    (24, c.getSeatsAvailable());
            preparedStatement.setInt    (25, c.getSeatsTotal());
            preparedStatement.setBoolean(26, c.isMondayClass());
            preparedStatement.setBoolean(27, c.isTuesdayClass());
            preparedStatement.setBoolean(28, c.isWednesdayClass());
            preparedStatement.setBoolean(29, c.isThursdayClass());
            preparedStatement.setBoolean(30, c.isFridayClass());
            preparedStatement.setBoolean(31, c.isSaturdayClass());
            preparedStatement.setBoolean(32, c.isSundayClass());
            preparedStatement.executeUpdate();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void performDatabaseActions(List<Class> allClasses) throws SQLException, ClassNotFoundException {
        allClasses.stream().forEach((c) -> {
            try {
                if(isClassInDatabase(c)) {
                    updateClassInDatabase(c);
                }
                else {
                    insertIntoDatabase(c);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static void updateClassInDatabase(Class c) {

    }

    private static boolean isClassInDatabase(Class c) {
        return false;
    }

}