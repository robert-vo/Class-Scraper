package com.scraper.main.pojo;

import java.sql.Date;
import java.sql.Time;

/**
 * Class is a java POJO that represents a class at the University of Houston.
 *
 * @author Robert Vo
 */
public class Class {

    private Term            term;
    private String          classTitle;
    private String          departmentAbbreviation;
    private String          departmentCourseNumber;
    private Status          classStatus;
    private String          classNumber;
    private int             seatsTaken;
    private int             seatsAvailable;
    private int             seatsTotal;
    private Date            classStartDate;
    private Date            classEndDate;
    private String          attributes;
    private Time            classStartTime;
    private Time            classEndTime;
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
    private String          buildingAbbreviation;
    private String          buildingRoomNumber;
    private String          format;
    private String          description;
    private String          duration;
    private String          session;
    private String          component;
    private String          syllabus;

    public Class(Term term, String classTitle, String departmentAbbreviation, String departmentCourseNumber,
                 Status classStatus, String classNumber, int seatsTaken, int seatsAvailable,
                 int seatsTotal, Date classStartDate, Date classEndDate, String attributes,
                 Time classStartTime, Time classEndTime, boolean isMondayClass,
                 boolean isTuesdayClass, boolean isWednesdayClass, boolean isThursdayClass,
                 boolean isFridayClass, boolean isSaturdayClass, boolean isSundayClass,
                 String instructorName, String instructorEmail, String location,
                 String buildingAbbreviation, String buildingRoomNumber, String format, String description,
                 String duration, String session, String component, String syllabus) {
        this.term = term;
        this.classTitle = classTitle;
        this.departmentAbbreviation = departmentAbbreviation;
        this.departmentCourseNumber = departmentCourseNumber;
        this.classStatus = classStatus;
        this.classNumber = classNumber;
        this.seatsTaken = seatsTaken;
        this.seatsAvailable = seatsAvailable;
        this.seatsTotal = seatsTotal;
        this.classStartDate = classStartDate;
        this.classEndDate = classEndDate;
        this.attributes = attributes;
        this.classStartTime = classStartTime;
        this.classEndTime = classEndTime;
        this.isMondayClass = isMondayClass;
        this.isTuesdayClass = isTuesdayClass;
        this.isWednesdayClass = isWednesdayClass;
        this.isThursdayClass = isThursdayClass;
        this.isFridayClass = isFridayClass;
        this.isSaturdayClass = isSaturdayClass;
        this.isSundayClass = isSundayClass;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.location = location;
        this.buildingAbbreviation = buildingAbbreviation;
        this.buildingRoomNumber = buildingRoomNumber;
        this.format = format;
        this.description = description;
        this.duration = duration;
        this.session = session;
        this.component = component;
        this.syllabus = syllabus;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getDepartmentAbbreviation() {
        return departmentAbbreviation;
    }

    public void setDepartmentAbbreviation(String departmentAbbreviation) {
        this.departmentAbbreviation = departmentAbbreviation;
    }

    public String getDepartmentCourseNumber() {
        return departmentCourseNumber;
    }

    public void setDepartmentCourseNumber(String departmentCourseNumber) {
        this.departmentCourseNumber = departmentCourseNumber;
    }

    public Status getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(Status classStatus) {
        this.classStatus = classStatus;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
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

    public Date getClassStartDate() {
        return classStartDate;
    }

    public void setClassStartDate(Date classStartDate) {
        this.classStartDate = classStartDate;
    }

    public Date getClassEndDate() {
        return classEndDate;
    }

    public void setClassEndDate(Date classEndDate) {
        this.classEndDate = classEndDate;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Time getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(Time classStartTime) {
        this.classStartTime = classStartTime;
    }

    public Time getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(Time classEndTime) {
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

    public String getBuildingAbbreviation() {
        return buildingAbbreviation;
    }

    public void setBuildingAbbreviation(String buildingAbbreviation) {
        this.buildingAbbreviation = buildingAbbreviation;
    }

    public String getBuildingRoomNumber() {
        return buildingRoomNumber;
    }

    public void setBuildingRoomNumber(String buildingRoomNumber) {
        this.buildingRoomNumber = buildingRoomNumber;
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

    @Override
    public String toString() {
        return "Class{" +
                "term=" + term +
                ", classTitle='" + classTitle + '\'' +
                ", departmentAbbreviation='" + departmentAbbreviation + '\'' +
                ", departmentCourseNumber='" + departmentCourseNumber + '\'' +
                ", classStatus=" + classStatus +
                ", classNumber='" + classNumber + '\'' +
                ", seatsTaken=" + seatsTaken +
                ", seatsAvailable=" + seatsAvailable +
                ", seatsTotal=" + seatsTotal +
                ", classStartDate=" + classStartDate +
                ", classEndDate=" + classEndDate +
                ", attributes='" + attributes + '\'' +
                ", classStartTime=" + classStartTime +
                ", classEndTime=" + classEndTime +
                ", isMondayClass=" + isMondayClass +
                ", isTuesdayClass=" + isTuesdayClass +
                ", isWednesdayClass=" + isWednesdayClass +
                ", isThursdayClass=" + isThursdayClass +
                ", isFridayClass=" + isFridayClass +
                ", isSaturdayClass=" + isSaturdayClass +
                ", isSundayClass=" + isSundayClass +
                ", instructorName='" + instructorName + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", location='" + location + '\'' +
                ", buildingAbbreviation='" + buildingAbbreviation + '\'' +
                ", buildingRoomNumber='" + buildingRoomNumber + '\'' +
                ", format='" + format + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", session='" + session + '\'' +
                ", component='" + component + '\'' +
                ", syllabus='" + syllabus + '\'' +
                '}';
    }
}

