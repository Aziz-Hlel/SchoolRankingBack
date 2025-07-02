package com.example.TechnoShark.SchoolRanking.Utils;

import java.util.List;


public interface interfacePage {

    // Use constants if you want default values
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;
    public static final List<String> DEFAULT_SORT = List.of();

    // Or define abstract getter methods for interface properties
    int getPage();
    int getSize();
    List<String> getSort();
    String getRole();
    String getSearch();
}
