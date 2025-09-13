package com.pentaho.jobbuilder.Controller;

import com.pentaho.jobbuilder.Model.Job;
import com.pentaho.jobbuilder.Service.JobService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create")
    public String createJob(@RequestBody Job job) {
        try{
            jobService.createJob(job);
            return "Job created successfully: " + job.getJobName();
        } catch (Exception e) {
            return "Error while creating job: " + job.getJobName() + " Error message: " + e.getMessage();
        }
    }

    @GetMapping("/complete")
    public void jobComplete(HttpServletResponse response) throws IOException {
        Path source = Paths.get("Output");
        String fileName = "Output.zip";

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (ZipOutputStream zs = new ZipOutputStream(response.getOutputStream())) {
            Files.walk(source)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(source.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        jobService.deleteFolderRecursively(source);
    }

    @GetMapping("payload")
    public String jobPayload() {
        return "Sample Payload: {\"jobName\":\"testJob\",\"transformationFiles\":[\"EmployeeSalaryTransformation.ktr\"],\"jobFiles\":[\"EmployeeJob.kjb\"],\"hops\":[{\"fromJob\":\"EmployeeSalaryTransformation\",\"toJob\":\"EmployeeJob\"}]}\n";
    }
}
