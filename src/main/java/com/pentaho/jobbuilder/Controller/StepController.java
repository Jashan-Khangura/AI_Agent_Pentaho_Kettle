package com.pentaho.jobbuilder.Controller;

import com.pentaho.jobbuilder.Model.Step;
import com.pentaho.jobbuilder.Service.StepService;
import com.pentaho.jobbuilder.util.PayloadStepType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/steps")
public class StepController {
    private final StepService stepService;
    private final PayloadStepType payloadStepType;

    public StepController(StepService stepService, PayloadStepType payloadStepType) {
        this.stepService = stepService;
        this.payloadStepType = payloadStepType;
    }

    @PostMapping("/create")
    public String createStep(@RequestBody Step stepRequest) {
        try{
            stepService.createStep(stepRequest);
            return "Step created successfully: " + stepRequest.getStepName();
        } catch (Exception e) {
            return "Error while creating step " + stepRequest.getStepName() + " Error message: " + e.getMessage();
        }
    }

    @GetMapping("/stepType")
    public List<String> getAllStepTypes() {
        return Arrays.asList("CSVInput", "FilterRows", "SortRows", "TableInput", "TableOutput", "MergeJoin", "Select", "UDJC");
    }

    @GetMapping("/payload")
    public String getStepTypePayload(@RequestParam String stepType) {
        return payloadStepType.getStepInfo(stepType);
    }

    @GetMapping("/save")
    public String saveSteps(@RequestParam String transformationName) {
        try {
            stepService.saveSteps(transformationName);
            return "Transformation saved successfully to " + transformationName;
        } catch (Exception e) {
            return "Error while saving all steps to transformation: " + transformationName + " Error message: " + e.getMessage();
        }
    }

    @GetMapping("/hop")
    public String hopSteps(@RequestParam String fromStep, @RequestParam String toStep) {
        try {
            stepService.defineHop(fromStep, toStep);
            return "Hop defined successfully between steps " + fromStep + " & " + toStep;
        } catch (Exception e) {
            return "Error while defining hop between steps: " + fromStep + " & " + toStep + " Error message: " + e.getMessage();
        }
    }

    @GetMapping("/test")
    public String testGenFiles(@RequestParam String fileName) {
        try {
            stepService.test(fileName);
            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
