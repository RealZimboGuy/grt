package com.github.realzimboguy.grt.model;

import java.util.LinkedList;

public class ReportDisplay {

    private int id;
    private String name;
    private int filterGroupId;
    private LinkedList<Integer> displayFieldIds;
    private ReportDisplayAggregates reportDisplayAggregates;

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

    public int getFilterGroupId() {
        return filterGroupId;
    }

    public void setFilterGroupId(int filterGroupId) {
        this.filterGroupId = filterGroupId;
    }

    public LinkedList<Integer> getDisplayFieldIds() {
        return displayFieldIds;
    }

    public void setDisplayFieldIds(LinkedList<Integer> displayFieldIds) {
        this.displayFieldIds = displayFieldIds;
    }

    public ReportDisplayAggregates getReportDisplayAggregates() {
        return reportDisplayAggregates;
    }

    public void setReportDisplayAggregates(ReportDisplayAggregates reportDisplayAggregates) {
        this.reportDisplayAggregates = reportDisplayAggregates;
    }

    public static enum ReportDisplayAggregates {
        SUM,
        COUNT,
        SUM_AND_COUNT
    }

}
