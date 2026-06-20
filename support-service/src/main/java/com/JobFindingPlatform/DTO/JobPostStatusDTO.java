package com.JobFindingPlatform.DTO;

public class JobPostStatusDTO {

    private int totalJobs;
    private int totalInternships;
    private int totalFullTimeJobs;
    private int totalPartTimeJobs;
    private int totalContractualJobs;
    private int totalFreelanceJobs;

   

    // =========================
    // Setter methods (so you can call jobs.totalInternships(70))
    // =========================
    public void totalJobs(int totalJobs) {
        this.totalJobs = totalJobs;
    }

    public void totalInternships(int totalInternships) {
        this.totalInternships = totalInternships;
    }

    public void totalFullTimeJobs(int totalFullTimeJobs) {
        this.totalFullTimeJobs = totalFullTimeJobs;
    }

    public void totalPartTimeJobs(int totalPartTimeJobs) {
        this.totalPartTimeJobs = totalPartTimeJobs;
    }

    public void totalContractualJobs(int totalContractualJobs) {
        this.totalContractualJobs = totalContractualJobs;
    }

    public void totalFreelanceJobs(int totalFreelanceJobs) {
        this.totalFreelanceJobs = totalFreelanceJobs;
    }

    // =========================
    // Getter methods (optional, if you need them)
    // =========================
    public int getTotalJobs() { return totalJobs; }
    public int getTotalInternships() { return totalInternships; }
    public int getTotalFullTimeJobs() { return totalFullTimeJobs; }
    public int getTotalPartTimeJobs() { return totalPartTimeJobs; }
    public int getTotalContractualJobs() { return totalContractualJobs; }
    public int getTotalFreelanceJobs() { return totalFreelanceJobs; }
}

