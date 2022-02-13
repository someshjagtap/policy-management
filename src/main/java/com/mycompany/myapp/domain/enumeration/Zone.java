package com.mycompany.myapp.domain.enumeration;

/**
 * The Zone enumeration.
 */
public enum Zone {
    A("a"),
    B("b"),
    C("c");

    private final String value;

    Zone(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
