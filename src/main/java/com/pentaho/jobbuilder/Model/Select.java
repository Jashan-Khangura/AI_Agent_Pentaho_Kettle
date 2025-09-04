package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.selectvalues.SelectValuesMeta;

import java.util.List;
import java.util.Objects;

@Data
@JsonTypeName("Select")
public class Select extends Step{
    private List<SelectFieldModel> selectFields;

    @Override
    public StepMeta getStepMeta() {
        SelectValuesMeta meta = new SelectValuesMeta();
        meta.setDefault();
        SelectValuesMeta.SelectField[] fields = new SelectValuesMeta.SelectField[selectFields.size()];

        int i = 0;
        for (SelectFieldModel field : selectFields) {
            SelectValuesMeta.SelectField addField = new SelectValuesMeta.SelectField();

            addField.setName(field.getFieldName());
            if(Objects.nonNull(field.getRenameField()) && !field.getRenameField().isEmpty())
                addField.setRename(field.getRenameField());

            fields[i++] = addField;
        }

        meta.setSelectFields(fields);

        return new StepMeta("SelectValues", getStepName(), meta);
    }
}
