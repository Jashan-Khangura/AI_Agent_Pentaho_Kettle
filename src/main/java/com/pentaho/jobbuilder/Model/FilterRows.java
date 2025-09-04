package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.core.Condition;
import org.pentaho.di.core.row.ValueMetaAndData;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.filterrows.FilterRowsMeta;

import java.util.Objects;

@Data
@JsonTypeName("FilterRows")
public class FilterRows extends Step{
    private FilterCondition filterCondition;
    private Boolean isCompareWithField;
    private String trueStep;
    private String falseStep;

    @Override
    public StepMeta getStepMeta() {
        FilterRowsMeta meta = new FilterRowsMeta();
        meta.setDefault();
        Condition con = getCondition();
        meta.setCondition(con);
        if(trueStep != null && !trueStep.isEmpty())
            meta.setTrueStepname(trueStep);
        if(falseStep != null && !falseStep.isEmpty())
            meta.setFalseStepname(falseStep);
        return new StepMeta("FilterRows", getStepName(), meta);
    }

    private Condition getCondition() {
        Condition con = new Condition();
        con.setLeftValuename(filterCondition.getField());
        con.setFunction(filterCondition.operator.toKettleCode());
        if(Objects.nonNull(filterCondition.getValue())) {
            if(isCompareWithField)
                con.setRightValuename(filterCondition.getValue());
            else {
                ValueMetaAndData valueMetaAndData = new ValueMetaAndData();
                valueMetaAndData.setValueData(filterCondition.getValue());
                con.setRightExact(valueMetaAndData);
            }
        }
        return con;
    }
}
