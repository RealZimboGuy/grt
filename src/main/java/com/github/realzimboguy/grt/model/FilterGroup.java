package com.github.realzimboguy.grt.model;

import java.util.LinkedList;

public class FilterGroup {

    private int id;
    private String name;
    private LinkedList<Integer> filterIds;

    private FilterGroupType filterGroupType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Integer> getFilterIds() {
        return filterIds;
    }

    public void setFilterIds(LinkedList<Integer> filterIds) {
        this.filterIds = filterIds;
    }


    public FilterGroupType getFilterGroupType() {
        return filterGroupType;
    }

    public void setFilterGroupType(FilterGroupType filterGroupType) {
        this.filterGroupType = filterGroupType;
    }

    public enum FilterGroupType {
        INCLUDE,
        EXCLUDE
    }
}
