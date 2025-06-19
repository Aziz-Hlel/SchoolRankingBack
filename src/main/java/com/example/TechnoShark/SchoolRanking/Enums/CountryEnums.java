package com.example.TechnoShark.SchoolRanking.Enums;

public enum CountryEnums {
    USA("United States"),
    FRANCE("France"),
    MOROCCO("Maroc");

    private final String displayName;

    CountryEnums(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
