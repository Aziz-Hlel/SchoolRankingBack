package com.example.TechnoShark.SchoolRanking.Enums;

public enum Country {
    USA("United States"),
    FRANCE("France"),
    MOROCCO("Maroc");

    private final String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
