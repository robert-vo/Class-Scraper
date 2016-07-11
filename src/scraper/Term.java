package scraper;

/**
 * Term are incremented by 10 from the previous term.
 * Term includes Spring, Summer, and Fall semesters.
 */

public enum Term {
    FALL_2015       ("1970", Semester.Fall),
    SPRING_2016     ("1980", Semester.Spring),
    SUMMER_2016     ("1990", Semester.Summer),
    FALL_2016       ("2000", Semester.Fall),
    SPRING_2017     ("2010", Semester.Spring),
    SUMMER_2017     ("2020", Semester.Summer),
    FALL_2017       ("2030", Semester.Fall),
    SPRING_2018     ("2040", Semester.Spring);

    private final String    termID;
    private final Semester  semester;

    Term(String termID, Semester semester) {
        this.termID = termID;
        this.semester = semester;
    }

    public String getTermID() {
        return termID;
    }

    public Semester getSemester() {
        return semester;
    }
}
