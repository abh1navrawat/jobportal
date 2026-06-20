package com.JobFindingPlatform.DTO;

public class ApplicationStatusDTO {

    private int totalApplications;
    private int totalShortlisted;
    private int totalRejected;
    private int totalPending;

    
    

    // =========================
    // Setter methods (method-style)
    // =========================
    public void totalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }

    public void totalShortlisted(int totalShortlisted) {
        this.totalShortlisted = totalShortlisted;
    }

    public void totalRejected(int totalRejected) {
        this.totalRejected = totalRejected;
    }

    public void totalPending(int totalPending) {
        this.totalPending = totalPending;
    }

    // =========================
    // Getter methods (optional)
    // =========================
    public int getTotalApplications() { return totalApplications; }
    public int getTotalShortlisted() { return totalShortlisted; }
    public int getTotalRejected() { return totalRejected; }
    public int getTotalPending() { return totalPending; }
}
