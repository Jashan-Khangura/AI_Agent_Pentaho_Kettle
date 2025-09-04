package com.pentaho.jobbuilder.Model;

import lombok.Data;

import java.util.List;

public class Job {
    private String jobName;
    private List<String> transformationFiles;
    private List<String> jobFiles;
    private List<JobHop> hops;

    public Job() {}

    public Job(String jobName, List<String> transformationFiles, List<String> jobFiles, List<JobHop> hops) {
        this.jobName = jobName;
        this.transformationFiles = transformationFiles;
        this.jobFiles = jobFiles;
        this.hops = hops;
    }

    public List<JobHop> getHops() {
        return hops;
    }

    public void setHops(List<JobHop> hops) {
        this.hops = hops;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<String> getTransformationFiles() {
        return transformationFiles;
    }

    public void setTransformationFiles(List<String> transformationFiles) {
        this.transformationFiles = transformationFiles;
    }

    public List<String> getJobFiles() {
        return jobFiles;
    }

    public void setJobFiles(List<String> jobFiles) {
        this.jobFiles = jobFiles;
    }
}
