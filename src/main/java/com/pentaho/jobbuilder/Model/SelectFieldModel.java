package com.pentaho.jobbuilder.Model;

public class SelectFieldModel {
    private String fieldName;
    private String renameField;

    public SelectFieldModel(String fieldName, String renameField) {
        this.fieldName = fieldName;
        this.renameField = renameField;
    }

    public SelectFieldModel() {}

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRenameField() {
        return renameField;
    }

    public void setRenameField(String renameField) {
        this.renameField = renameField;
    }
}
