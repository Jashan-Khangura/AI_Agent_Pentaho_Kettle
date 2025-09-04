package com.pentaho.jobbuilder.Model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonTypeName("UDJC")
public class UDJC extends Step {
    private String classType;
    private String className;
    private String sourceCode;

    @Override
    public StepMeta getStepMeta() {
        UserDefinedJavaClassMeta meta = new UserDefinedJavaClassMeta();
        meta.setDefault();
        List<UserDefinedJavaClassDef> def = new ArrayList<>();
        def.add(new UserDefinedJavaClassDef(classType.equalsIgnoreCase("transform") ?
                UserDefinedJavaClassDef.ClassType.TRANSFORM_CLASS:UserDefinedJavaClassDef.ClassType.NORMAL_CLASS,
                className, sourceCode));
        meta.replaceDefinitions(def);

        return new StepMeta("UserDefinedJavaClass", getStepName(), meta);
    }
}
