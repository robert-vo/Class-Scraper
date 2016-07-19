package scraper;

/**
 * Created by Robert on 6/29/16.
 */
public class Class {

    public enum Status {
        Open, Closed
    }

    private Term            termID;
    private String          classTitle;
    private String          className;
    private Class.Status    classStatus;
    private String          courseNumber;
    private int             seatsTaken;
    private int             seatsAvailable;
    private int             seatsTotal;
    private String          classStartDate;
    private String          classEndDate;
    private String          attributes;
    private String          classStartTime;
    private String          classEndTime;
    private boolean         isMondayClass;
    private boolean         isTuesdayClass;
    private boolean         isWednesdayClass;
    private boolean         isThursdayClass;
    private boolean         isFridayClass;
    private boolean         isSaturdayClass;
    private boolean         isSundayClass;
    private String          instructorName;
    private String          instructorEmail;
    private String          location;
    private String          room;
    private String          format;
    private String          description;
    private String          duration;
    private String          session;
    private String          component;
    private String          syllabus;

    public Class(Term termID, String classTitle, String className, Status classStatus, String courseNumber,
                 int seatsTaken, int seatsAvailable, int seatsTotal, String classStartDate, String classEndDate,
                 String attributes, String classStartTime, String classEndTime, boolean isMondayClass,
                 boolean isTuesdayClass, boolean isWednesdayClass, boolean isThursdayClass, boolean isFridayClass,
                 boolean isSaturdayClass, boolean isSundayClass, String instructorName,
                 String instructorEmail, String location, String room, String format, String description,
                 String duration, String session, String component, String syllabus) {
        this.termID             = termID;
        this.classTitle         = classTitle;
        this.className          = className;
        this.classStatus        = classStatus;
        this.courseNumber       = courseNumber;
        this.seatsTaken         = seatsTaken;
        this.seatsAvailable     = seatsAvailable;
        this.seatsTotal         = seatsTotal;
        this.classStartDate     = classStartDate;
        this.classEndDate       = classEndDate;
        this.attributes         = attributes;
        this.classStartTime     = classStartTime;
        this.classEndTime       = classEndTime;
        this.isMondayClass      = isMondayClass;
        this.isTuesdayClass     = isTuesdayClass;
        this.isWednesdayClass   = isWednesdayClass;
        this.isThursdayClass    = isThursdayClass;
        this.isFridayClass      = isFridayClass;
        this.isSaturdayClass    = isSaturdayClass;
        this.isSundayClass      = isSundayClass;
        this.instructorName     = instructorName;
        this.instructorEmail    = instructorEmail;
        this.location           = location;
        this.room               = room;
        this.format             = format;
        this.description        = description;
        this.duration           = duration;
        this.session            = session;
        this.component          = component;
        this.syllabus           = syllabus;
    }

    public Term getTermID() {
        return termID;
    }

    public void setTermID(Term termID) {
        this.termID = termID;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Status getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(Status classStatus) {
        this.classStatus = classStatus;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getSeatsTaken() {
        return seatsTaken;
    }

    public void setSeatsTaken(int seatsTaken) {
        this.seatsTaken = seatsTaken;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }

    public void setSeatsTotal(int seatsTotal) {
        this.seatsTotal = seatsTotal;
    }

    public String getClassStartDate() {
        return classStartDate;
    }

    public void setClassStartDate(String classStartDate) {
        this.classStartDate = classStartDate;
    }

    public String getClassEndDate() {
        return classEndDate;
    }

    public void setClassEndDate(String classEndDate) {
        this.classEndDate = classEndDate;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(String classStartTime) {
        this.classStartTime = classStartTime;
    }

    public String getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(String classEndTime) {
        this.classEndTime = classEndTime;
    }

    public boolean isMondayClass() {
        return isMondayClass;
    }

    public void setMondayClass(boolean mondayClass) {
        isMondayClass = mondayClass;
    }

    public boolean isTuesdayClass() {
        return isTuesdayClass;
    }

    public void setTuesdayClass(boolean tuesdayClass) {
        isTuesdayClass = tuesdayClass;
    }

    public boolean isWednesdayClass() {
        return isWednesdayClass;
    }

    public void setWednesdayClass(boolean wednesdayClass) {
        isWednesdayClass = wednesdayClass;
    }

    public boolean isThursdayClass() {
        return isThursdayClass;
    }

    public void setThursdayClass(boolean thursdayClass) {
        isThursdayClass = thursdayClass;
    }

    public boolean isFridayClass() {
        return isFridayClass;
    }

    public void setFridayClass(boolean fridayClass) {
        isFridayClass = fridayClass;
    }

    public boolean isSaturdayClass() {
        return isSaturdayClass;
    }

    public void setSaturdayClass(boolean saturdayClass) {
        isSaturdayClass = saturdayClass;
    }

    public boolean isSundayClass() {
        return isSundayClass;
    }

    public void setSundayClass(boolean sundayClass) {
        isSundayClass = sundayClass;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
}

