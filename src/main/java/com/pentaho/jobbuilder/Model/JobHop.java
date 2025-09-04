package com.pentaho.jobbuilder.Model;

public class JobHop {
    private String fromJob;
    private String toJob;

    public JobHop() {}

    public JobHop(String fromJob, String toJob) {
        this.fromJob = fromJob;
        this.toJob = toJob;
    }

    public String getFromJob() {
        return fromJob;
    }

    public void setFromJob(String fromJob) {
        this.fromJob = fromJob;
    }

    public String getToJob() {
        return toJob;
    }

    public void setToJob(String toJob) {
        this.toJob = toJob;
    }
}
