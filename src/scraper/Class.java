package scraper;

/**
 * Created by Robert on 6/29/16.
 */
public class Class {
    private String name;
    private String className;
    private boolean classStatus;
    private int seatsTaken;
    private int seatsAvailable;
    private String classDates;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isClassStatus() {
        return classStatus;
    }

    public void setClassStatus(boolean classStatus) {
        this.classStatus = classStatus;
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

    public String getClassDates() {
        return classDates;
    }

    public void setClassDates(String classDates) {
        this.classDates = classDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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