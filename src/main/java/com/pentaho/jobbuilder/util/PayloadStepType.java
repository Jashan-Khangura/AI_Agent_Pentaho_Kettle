package com.pentaho.jobbuilder.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayloadStepType {
    private Map<String, String> map = new HashMap<>();

    @PostConstruct
    private void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(new File("C:/Projects/jobbuilder/src/main/resources/StepMetaInfo.json"),
                    new TypeReference<>() {
                    });
            System.out.println(map.toString());
        } catch (Exception e) {
            System.out.println("Error while loading StepMetaInfo document. " + e.getMessage());
        }
    }

    public String getStepInfo(String stepType) {
        return map.get(stepType);
    }
}
