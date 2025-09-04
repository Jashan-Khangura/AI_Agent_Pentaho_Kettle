package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;

@Data
@JsonTypeName("CSVInput")
public class CSVInput extends Step {
    private String filePath;
    private String delimitter = ",";
    private Boolean headerRowPresent = true;

    @Override
    public StepMeta getStepMeta() {
        CsvInputMeta meta = new CsvInputMeta();
        meta.setDefault();
        meta.setDelimiter(delimitter);
        meta.setFilename(filePath);
        meta.setHeaderPresent(headerRowPresent);
        return new StepMeta("CsvInput", getStepName(), meta);
    }
}
