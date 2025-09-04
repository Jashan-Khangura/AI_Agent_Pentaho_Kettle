package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.pentaho.di.trans.step.StepMeta;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "stepType",
        visible = true,
        defaultImpl = UnknownStep.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TableInput.class, name = "TableInput"),
        @JsonSubTypes.Type(value = TableOutput.class, name = "TableOutput"),
        @JsonSubTypes.Type(value = FilterRows.class, name = "FilterRows"),
        @JsonSubTypes.Type(value = CSVInput.class, name = "CSVInput"),
        @JsonSubTypes.Type(value = SortRows.class, name = "SortRows"),
        @JsonSubTypes.Type(value = Select.class, name = "Select"),
        @JsonSubTypes.Type(value = MergeJoin.class, name = "MergeJoin"),
        @JsonSubTypes.Type(value = UDJC.class, name = "UDJC")
})
public abstract class Step {
    private String stepName;
    private String stepType;

    public abstract StepMeta getStepMeta();

    public Step() {}

    public Step(String stepName, String stepType) {
        this.stepName = stepName;
        this.stepType = stepType;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }
}
