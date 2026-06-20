package com.JobFindingPlatform.DTO;

public class UserStatusDTO {

    private int totalJobseekers;
    private int totalRecruiters;
    private int totalBlockUsers;
    private int totalPaidUsers;

    
    // =========================
    // Method-style setters
    // =========================
    public void totalJobseekers(int totalJobseekers) {
        this.totalJobseekers = totalJobseekers;
    }

    public void totalRecruiters(int totalRecruiters) {
        this.totalRecruiters = totalRecruiters;
    }

    public void totalBlockUsers(int totalBlockUsers) {
        this.totalBlockUsers = totalBlockUsers;
    }

    public void totalPaidUsers(int totalPaidUsers) {
        this.totalPaidUsers = totalPaidUsers;
    }

    // =========================
    // Optional getters
    // =========================
    public int getTotalJobseekers() { return totalJobseekers; }
    public int getTotalRecruiters() { return totalRecruiters; }
    public int getTotalBlockUsers() { return totalBlockUsers; }
    public int getTotalPaidUsers() { return totalPaidUsers; }
}
