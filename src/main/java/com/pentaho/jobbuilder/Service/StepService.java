package com.pentaho.jobbuilder.Service;

import com.pentaho.jobbuilder.Model.Step;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StepService {
    private static final Logger log = LoggerFactory.getLogger(StepService.class);
    private final Map<String, StepMeta> stepMap = new HashMap<>();
    private final List<TransHopMeta> hopMetaList = new ArrayList<>();

    public void createStep(Step stepRequest) {
        if (Objects.isNull(stepRequest.getStepName()) || Objects.isNull(stepRequest.getStepType())
                || stepRequest.getStepName().isEmpty() || stepRequest.getStepType().isEmpty()) {
            throw new IllegalArgumentException("Step name and type");
        }
        stepMap.put(stepRequest.getStepName(), stepRequest.getStepMeta());
        log.info("Created Step {} Type: {}", stepRequest.getStepName(), stepRequest.getStepType());
    }

    public void saveSteps(String transformationName) throws KettleXMLException {
        TransMeta transMeta = new TransMeta();
        transMeta.setName(transformationName);
        log.info("Creating Transformation file {}", transformationName);
        for(StepMeta stepMeta : stepMap.values()) {
            if(Objects.nonNull(stepMeta)) {
                transMeta.addStep(stepMeta);
            }
        }

        for(TransHopMeta hopMeta : hopMetaList) {
            if(Objects.nonNull(hopMeta)) {
                transMeta.addTransHop(hopMeta);
            }
        }

        String fileName = "Output/"+transformationName + ".ktr";
        transMeta.writeXML(fileName);
        log.info("{}.ktr Created", transformationName);

        stepMap.clear();
        hopMetaList.clear();
    }

    public void defineHop(String fromStep, String toStep) {
        StepMeta from = stepMap.get(fromStep);
        StepMeta to = stepMap.get(toStep);
        if (from != null && to != null) {
            hopMetaList.add(new TransHopMeta(from, to));
            log.info("Hop Defined between steps {} & {}", fromStep, toStep);
        } else {
            throw new IllegalArgumentException("fromStep and toStep cannot be null.");
        }
    }

    public void test(String fileName) throws Exception {
        log.info("Testing {}", fileName);
        KettleEnvironment.init();

        TransMeta transMeta = new TransMeta(fileName);

        List<CheckResultInterface> results = new ArrayList<>();
        transMeta.checkSteps(results, true, new ProgressNullMonitorListener());

        boolean hasError = false;
        for (CheckResultInterface result : results) {
            if (result.getType() == CheckResultInterface.TYPE_RESULT_ERROR) {
                log.error(result.getText());
                hasError = true;
            }
        }

        if (hasError) {
            throw new Exception("KTR has validation errors.");
        } else {
            log.info("KTR is valid");
        }
    }
}
