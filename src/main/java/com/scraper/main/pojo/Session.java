package com.scraper.main.pojo;

import org.apache.log4j.Logger;

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

    private final String sessionName;
    private static Logger log = Logger.getLogger(Session.class);

    Session(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public static Session returnSessionFromString(String text) {
        return returnSessionFromBinarySearch(Session.values(), text, 0, Session.values().length);
    }

    /**
     * Performs a binary search on the {@code Session.values()} to find a particular enum from a string.
     *
     * @param allSessions All of the session values available.
     * @param sessionToFind The session to be found.
     * @param low The lower bound of the array to be searched through.
     * @param high The higher bound of the array to be searched through.
     * @return The corresponding {@code Session}, if found, or null, if not found.
     */
    private static Session returnSessionFromBinarySearch(Session[] allSessions, String sessionToFind, int low, int high) {
        if(high < low) {
            log.warn("Attempted to search for " + sessionToFind + " session, but it did not exist in the list of sessions. " +
                    "Returning null for the session.");
            return null;
        }

        int mid = low + ((high - low) / 2);

        if(allSessions[mid].getSessionName().compareTo(sessionToFind) > 0) {
            return returnSessionFromBinarySearch(allSessions, sessionToFind, low, mid - 1);
        }
        else if (allSessions[mid].getSessionName().compareTo(sessionToFind) < 0) {
            return returnSessionFromBinarySearch(allSessions, sessionToFind, mid + 1, high);
        }
        else {
            return allSessions[mid];
        }
    }
}

