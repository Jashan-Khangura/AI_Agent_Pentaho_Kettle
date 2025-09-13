package com.pentaho.jobbuilder.Service;

import com.pentaho.jobbuilder.Model.Job;
import com.pentaho.jobbuilder.Model.JobHop;
import jakarta.annotation.PostConstruct;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.job.JobEntryJob;
import org.pentaho.di.job.entries.trans.JobEntryTrans;
import org.pentaho.di.job.entry.JobEntryCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class JobService {
    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private final Map<String, JobEntryCopy> jobEntryMap = new HashMap<>();

    @PostConstruct
    public void init() throws KettleException {
        KettleEnvironment.init();
        log.info("Kettle Environment init completed");
    }

    public void createJob(Job job) {
        if(Objects.isNull(job.getJobName()) || job.getJobName().isEmpty()
                || (Objects.isNull(job.getTransformationFiles()) && Objects.isNull(job.getJobFiles()))
                || (job.getTransformationFiles().isEmpty() && job.getJobFiles().isEmpty())) {
            throw new IllegalArgumentException("JobName is required. Request should have at least one TransformationFile, JobFile or Hop defined.");
        }

        int x = 50;
        int y = 50;
        int stepGap = 50;

        log.info("Creating Job {}", job.getJobName());

        JobMeta jobMeta = new JobMeta();
        jobMeta.setName(job.getJobName());

        if(Objects.nonNull(job.getTransformationFiles()) && !job.getTransformationFiles().isEmpty()) {
            for(String fileName : job.getTransformationFiles()) {
                JobEntryTrans entry = new JobEntryTrans();
                String name = fileName.replace(".ktr", "");
                entry.setName(name);
                entry.setFileName(fileName);

                JobEntryCopy copy = new JobEntryCopy(entry);
                copy.setLocation(x, y+=stepGap);
                jobMeta.addJobEntry(copy);

                jobEntryMap.put(name, copy);
                log.info("Added {} to {} Job Scope", fileName, job.getJobName());
            }
        }

        if(Objects.nonNull(job.getJobFiles()) && !job.getJobFiles().isEmpty()) {
            for(String fileName : job.getJobFiles()) {
                JobEntryJob entry = new JobEntryJob();
                String name = fileName.replace(".kjb", "");
                entry.setName(name);
                entry.setFileName(fileName);

                JobEntryCopy copy = new JobEntryCopy(entry);
                copy.setLocation(x+=stepGap, y+=stepGap);
                jobMeta.addJobEntry(copy);

                jobEntryMap.put(name, copy);
                log.info("Added {} to {} Job Scope", fileName, job.getJobName());
            }
        }

        if(Objects.nonNull(job.getHops()) && !job.getHops().isEmpty()) {
            for(JobHop hop : job.getHops()) {
                JobHopMeta hopMeta = defineHop(hop);
                jobMeta.addJobHop(hopMeta);
            }
        }

        List<CheckResultInterface> remarks = new ArrayList<>();
        jobMeta.checkJobEntries(remarks, true, new ProgressNullMonitorListener());

        if (remarks.isEmpty()) {
            log.info("{} Job validation passed: No errors found.", job.getJobName());

            String file = "Output/"+job.getJobName() + ".kjb";
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jobMeta.getXML());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            log.info("{} Job validation Failed", job.getJobName());
            StringBuilder validationRes = new StringBuilder();
            for (CheckResultInterface remark : remarks) {
                validationRes.append(remark.getType()).append(" - ").append(remark.getText()).append("\n");
            }
            log.info("Validation Results: \n{}", validationRes);
        }

        jobEntryMap.clear();
    }

    public void deleteFolderRecursively(Path folder) throws IOException {
        if (Files.exists(folder)) {
            Files.walk(folder)
                    .filter(path -> !path.equals(folder))
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            log.info("Cleared Output Folder");
        }
    }

    public JobHopMeta defineHop(JobHop hop) {
        JobEntryCopy fromJob = jobEntryMap.get(hop.getFromJob());
        JobEntryCopy toJob = jobEntryMap.get(hop.getToJob());
        log.info("Hop Defined between {} & {}", fromJob, toJob);

        return new JobHopMeta(fromJob, toJob);
    }
}
