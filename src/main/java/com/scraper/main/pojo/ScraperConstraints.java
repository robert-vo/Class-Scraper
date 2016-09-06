package com.scraper.main.pojo;

public class ScraperConstraints {
    protected Session sessionConstraint;
    protected Subject subjectConstraint;
    protected int pageLimitConstraint = Integer.MAX_VALUE;
    protected int initialPageNumberConstraint = 0;

    public ScraperConstraints() {
    }

    public ScraperConstraints(Session sessionConstraint, Subject subjectConstraint, int pageLimitConstraint, int initialPageNumberConstraint) {
        this.sessionConstraint = sessionConstraint;
        this.subjectConstraint = subjectConstraint;
        this.pageLimitConstraint = pageLimitConstraint;
        this.initialPageNumberConstraint = initialPageNumberConstraint;
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

    public int getPageLimitConstraint() {
        return pageLimitConstraint;
    }

    public void setPageLimitConstraint(int pageLimitConstraint) {
        this.pageLimitConstraint = pageLimitConstraint;
    }

    public int getInitialPageNumberConstraint() {
        return initialPageNumberConstraint;
    }

    public void setInitialPageNumberConstraint(int initialPageNumberConstraint) {
        this.initialPageNumberConstraint = initialPageNumberConstraint;
    }
}