package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.sort.SortRowsMeta;

import java.util.List;

@Data
@JsonTypeName("SortRows")
public class SortRows extends Step{
    private List<String> fields;
    private List<Boolean> ascending;
    private List<Boolean> caseSensitive;

    @Override
    public StepMeta getStepMeta() {
        SortRowsMeta meta = new SortRowsMeta();
        meta.setDefault();
        meta.setFieldName(fields.toArray(new String[0]));
        boolean[] arr = new boolean[ascending.size()];
        for(int n = 0; n < arr.length; n++)
        {
            arr[n] = ascending.get(n);
        }
        meta.setAscending(arr);

        boolean[] caseArr = new boolean[caseSensitive.size()];
        for(int n = 0; n < arr.length; n++)
        {
            caseArr[n] = caseSensitive.get(n);
        }
        boolean[] collatorArr = new boolean[caseSensitive.size()];
        for(int n = 0; n < arr.length; n++)
        {
            collatorArr[n] = false;
        }
        int[] defaultStrength = new int[caseSensitive.size()];
        meta.setCaseSensitive(caseArr);
        meta.setCollatorEnabled(collatorArr);
        meta.setCollatorStrength(defaultStrength);
        meta.setPreSortedField(arr);
        return new StepMeta("SortRows", getStepName(), meta);
    }
}
