package com.pentaho.jobbuilder.util;

import org.pentaho.di.core.Condition;

public enum Operator {
    EQUAL("="),
    NOT_EQUAL("<>"),
    GREATER_THAN(">"),
    GREATER_OR_EQUAL(">="),
    LESS_THAN("<"),
    LESS_OR_EQUAL("<="),
    LIKE("LIKE");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public int toKettleCode() {
        return switch (this) {
            case EQUAL -> Condition.FUNC_EQUAL;
            case NOT_EQUAL -> Condition.FUNC_NOT_EQUAL;
            case LESS_THAN -> Condition.FUNC_SMALLER;
            case LESS_OR_EQUAL -> Condition.FUNC_SMALLER_EQUAL;
            case GREATER_THAN -> Condition.FUNC_LARGER;
            case GREATER_OR_EQUAL -> Condition.FUNC_LARGER_EQUAL;
            case LIKE -> Condition.FUNC_LIKE;
        };
    }
}
