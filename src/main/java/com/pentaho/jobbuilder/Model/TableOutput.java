package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

@Data
@JsonTypeName("TableOutput")
public class TableOutput extends Step{
    private String targetTable;
    private Integer batchSize;
    private Boolean truncateTable = false;

    @Override
    public StepMeta getStepMeta() {
        TableOutputMeta meta = new TableOutputMeta();
        meta.setDefault();
        meta.setTableName(targetTable);
        meta.setTruncateTable(truncateTable);
        if(batchSize != null)
            meta.setCommitSize(batchSize);
        return new StepMeta("TableOutput", getStepName(), meta);
    }
}
