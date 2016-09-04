package com.scraper.main;

/**
 * Session details which part of the semester a class falls under.
 *
 * @author Robert Vo
 */
public enum Session {

    REGULAR_ACADEMIC_SESSION("1"),
    SESSION_2("2"),
    SESSION_3("3"),
    SESSION_4("4"),
    SESSION_5("5"),
    SESSION_6("6"),
    MINI_SESSION("MIN");

    protected final String sessionName;

    Session(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionName() {
        return sessionName;
    }

}

