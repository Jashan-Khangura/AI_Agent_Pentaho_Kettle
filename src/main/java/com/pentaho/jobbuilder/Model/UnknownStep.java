package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.pentaho.di.trans.step.StepMeta;

@JsonTypeName("UnknownStep")
public class UnknownStep extends Step {
    @Override
    public StepMeta getStepMeta() {
        throw new UnsupportedOperationException("Unknown step type: " + getStepType());
    }
}
