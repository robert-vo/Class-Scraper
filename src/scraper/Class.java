package scraper;

/**
 * Created by Robert on 6/29/16.
 */
public class Class {

    public enum Status {
        Open, Closed
    }

    private String classID;
    private Term termID;
    private String classTitle;
    private String className;
    private Status classStatus;
    private String courseNumber;
    private int seatsTaken;
    private int seatsAvailable;
    private Semester semester;
    private String classDates;
    private String attributes;
    private String daysTimes;
    private String instructorName;
    private String instructorEmail;
    private String location;
    private String room;
    private String format;
    private String description;
    private String duration;
    private String session;
    private String component;
    private String syllabus;

    public Class(Term termID, String classTitle, String className, Status classStatus, String courseNumber, int seatsTaken, int seatsAvailable, Semester semester, String classDates, String attributes, String daysTimes, String instructorName, String instructorEmail, String location, String room, String format, String description, String duration, String session, String component, String syllabus) {
        this.termID = termID;
        this.classTitle = classTitle;
        this.className = className;
        this.classStatus = classStatus;
        this.courseNumber = courseNumber;
        this.seatsTaken = seatsTaken;
        this.seatsAvailable = seatsAvailable;
        this.semester = semester;
        this.classDates = classDates;
        this.attributes = attributes;
        this.daysTimes = daysTimes;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.location = location;
        this.room = room;
        this.format = format;
        this.description = description;
        this.duration = duration;
        this.session = session;
        this.component = component;
        this.syllabus = syllabus;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
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

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getClassDates() {
        return classDates;
    }

    public void setClassDates(String classDates) {
        this.classDates = classDates;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getDaysTimes() {
        return daysTimes;
    }

    public void setDaysTimes(String daysTimes) {
        this.daysTimes = daysTimes;
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

/*
CLASS 1234 (12345) CLASS_NAME √
OPEN/CLOSED (Seats Taken / Seats Available) √
Class Attributes (if applicable) √
Dates: start dates - end dates √
Days/Times: MoFr 9am-5pm √
Instructor: Teacher √
Instructor Email: e@e.e √
Location: UH √
Room: Roomroom √
Format: OnlineOrNot √

Description: Something (remove the word 'Description')
Class Number: Something (remove the word 'Class Number')
Duration: 123123 Weeks √ (remove the word 'Duration')
Session: 1 (remove the word 'Session')
Class Component: LEC √ (remove the words 'Class Component')
Syllabus: Link (remove the word 'Syllabus')
Notes:
*/

/*


 */