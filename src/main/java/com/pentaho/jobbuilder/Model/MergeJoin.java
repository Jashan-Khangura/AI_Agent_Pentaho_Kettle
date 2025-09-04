package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.pentaho.jobbuilder.util.JoinTypeEnum;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.mergejoin.MergeJoinMeta;

import java.util.List;

@Data
@JsonTypeName("MergeJoin")
public class MergeJoin extends Step{
    private List<String> sourceFields;
    private List<String> targetFields;
    private JoinTypeEnum joinType;

    @Override
    public StepMeta getStepMeta() {
        MergeJoinMeta meta = new MergeJoinMeta();
        meta.setDefault();
        meta.setJoinType(joinType.getJoinType());
        meta.setKeyFields1(sourceFields.toArray(new String[0]));
        meta.setKeyFields2(targetFields.toArray(new String[0]));

        return new StepMeta("MergeJoin", getStepName(), meta);
    }
}
