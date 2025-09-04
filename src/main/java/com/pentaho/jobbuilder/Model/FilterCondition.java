package com.pentaho.jobbuilder.Model;

import com.pentaho.jobbuilder.util.Operator;


public class FilterCondition {
    private String field;
    public Operator operator;
    private String value;

    public FilterCondition() {}

    public FilterCondition(String field, Operator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
