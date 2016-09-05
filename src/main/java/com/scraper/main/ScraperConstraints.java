package com.scraper.main;

public class ScraperConstraints {
    protected Session sessionConstraint;
    protected Subject subjectConstraint;
    protected int     pageConstraint = Integer.MAX_VALUE;

    public ScraperConstraints() {
    }

    public ScraperConstraints(Session sessionConstraint, Subject subjectConstraint, int pageConstraint) {
        this.sessionConstraint = sessionConstraint;
        this.subjectConstraint = subjectConstraint;
        this.pageConstraint = pageConstraint;
    }

    public Session getSessionConstraint() {
        return sessionConstraint;
    }

    public void setSessionConstraint(Session sessionConstraint) {
        this.sessionConstraint = sessionConstraint;
    }

    public Subject getSubjectConstraint() {
        return subjectConstraint;
    }

    public void setSubjectConstraint(Subject subjectConstraint) {
        this.subjectConstraint = subjectConstraint;
    }

    public int getPageConstraint() {
        return pageConstraint;
    }

    public void setPageConstraint(int pageConstraint) {
        this.pageConstraint = pageConstraint;
    }
}
