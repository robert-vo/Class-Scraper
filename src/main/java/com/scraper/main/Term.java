package com.scraper.main;

/**
 * The Term Enum represents a semester and year.
 * Terms are incremented by 10 from the previous term, starting from FALL_2015 -> 1970.
 *
 * Term includes Spring, Summer, and Fall semesters.
 *
 * @author Robert Vo
 */

public enum Term {
    FALL_2015       ("1970", Semester.Fall),
    SPRING_2016     ("1980", Semester.Spring),
    SUMMER_2016     ("1990", Semester.Summer),
    FALL_2016       ("2000", Semester.Fall),
    SPRING_2017     ("2010", Semester.Spring),
    SUMMER_2017     ("2020", Semester.Summer),
    FALL_2017       ("2030", Semester.Fall),
    SPRING_2018     ("2040", Semester.Spring),
    SUMMER_2018     ("2030", Semester.Summer),
    FALL_2018       ("2040", Semester.Fall),
    SPRING_2019     ("2050", Semester.Spring),
    SUMMER_2019     ("2060", Semester.Summer),
    FALL_2019       ("2070", Semester.Fall),
    SPRING_2020     ("2080", Semester.Spring),
    SUMMER_2020     ("2090", Semester.Summer),
    FALL_2020       ("2100", Semester.Fall),
    SPRING_2021     ("2110", Semester.Spring),
    SUMMER_2021     ("2120", Semester.Summer),
    FALL_2021       ("2130", Semester.Fall),
    SPRING_2022     ("2140", Semester.Spring),
    SUMMER_2022     ("2150", Semester.Summer),
    FALL_2022       ("2160", Semester.Fall),
    SPRING_2023     ("2170", Semester.Spring),
    SUMMER_2023     ("2180", Semester.Summer),
    FALL_2023       ("2190", Semester.Fall),
    SPRING_2024     ("2200", Semester.Spring);

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

    /**
     * Returns a Term Enum from a string representation of the termID.
     *
     * @param text The String representation of termID.
     * @return A Term Enum that corresponds to the String representation parameter.
     */
    public static Term returnTermFromString(String text) {
        return returnTermFromBinarySearch(Term.values(), text, 0, Term.values().length);
    }

    /**
     * Performs a binary search on the {@code Term.values()} to find a particular enum from a string.
     *
     * @param allTerms All of the term values available.
     * @param termToFind The term to be found.
     * @param low The lower bound of the array to be searched through.
     * @param high The higher bound of the array to be searched through.
     * @return The corresponding Term, if found, or null, if not found.
     */
    private static Term returnTermFromBinarySearch(Term[] allTerms, String termToFind, int low, int high) {
        if(high < low) {
            return null;
        }

        int mid = low + ((high - low) / 2);

        if(allTerms[mid].getTermID().compareTo(termToFind) > 0) {
            return returnTermFromBinarySearch(allTerms, termToFind, low, mid - 1);
        }
        else if (allTerms[mid].getTermID().compareTo(termToFind) < 0) {
            return returnTermFromBinarySearch(allTerms, termToFind, mid + 1, high);
        }
        else {
            return allTerms[mid];
        }
    }
}
