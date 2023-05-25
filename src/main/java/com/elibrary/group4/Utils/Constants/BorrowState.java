package com.elibrary.group4.Utils.Constants;

public enum BorrowState {
    TAKEN, DECLINE, CANBETAKE, LATE, RETURN;

    public static BorrowState fromString(String value) {
        for (BorrowState enumValue : BorrowState.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new RuntimeException("Invalid value for MyEnum: " + value);
    }
}
