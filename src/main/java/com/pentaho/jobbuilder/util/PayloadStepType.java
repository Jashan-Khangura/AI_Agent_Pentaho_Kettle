package com.pentaho.jobbuilder.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayloadStepType {
    private static final Logger log = LoggerFactory.getLogger(PayloadStepType.class);
    private Map<String, String> map = new HashMap<>();
    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    private void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("StepMetaInfo.json");
            map = mapper.readValue(inputStream, new TypeReference<Map<String, String>>(){});
            log.info("StepMetaInfo.json loaded successfully");
        } catch (Exception e) {
            log.error("Error while loading StepMetaInfo document. {}", e.getMessage());
        }
    }

    public String getStepInfo(String stepType) {
        return map.get(stepType);
    }
}
