package com.pentaho.jobbuilder.util;

import org.pentaho.metadata.model.concept.types.JoinType;

public enum JoinTypeEnum {
    INNER("Inner"),
    LEFT_OUTER("Left outer"),
    RIGHT_OUTER("Right outer"),
    FULL_OUTER("Full outer");

    private final String type;

    JoinTypeEnum(String type) {this.type = type;}

    public String getJoinType() {
        return switch (this) {
            case INNER -> JoinType.INNER.getType();
            case LEFT_OUTER -> JoinType.LEFT_OUTER.getType();
            case RIGHT_OUTER -> JoinType.RIGHT_OUTER.getType();
            case FULL_OUTER -> JoinType.FULL_OUTER.getType();
        };
    }
}
