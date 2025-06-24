package com.example.TechnoShark.SchoolRanking.Enums;

public enum CountryEnums {
    USA("United States"),
    France("France"),
    Morocco("Maroc");

    private final String displayName;

    CountryEnums(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
