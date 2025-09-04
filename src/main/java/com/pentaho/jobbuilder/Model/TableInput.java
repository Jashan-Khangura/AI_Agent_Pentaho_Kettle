package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;

@Data
@JsonTypeName("TableInput")
public class TableInput extends Step{
    private String query;
    private String rowLimit;

    @Override
    public StepMeta getStepMeta() {
        TableInputMeta meta = new TableInputMeta();
        meta.setDefault();
        meta.setSQL(query);
        if(rowLimit != null)
            meta.setRowLimit(rowLimit);
        return new StepMeta("TableInput", getStepName(), meta);
    }
}
