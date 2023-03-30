package com.github.realzimboguy.grt.model;

import java.util.LinkedList;

public class Report {

    private int id;
    private String name;
    private String description;
    private int reportDirectoryId;

    private LinkedList<Integer> reportDisplayIds;

    private ReportDisplayTotals reportDisplayTotals;
    private DateAggregation dateAggregation;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReportDirectoryId() {
        return reportDirectoryId;
    }

    public void setReportDirectoryId(int reportDirectoryId) {
        this.reportDirectoryId = reportDirectoryId;
    }

    public LinkedList<Integer> getReportDisplayIds() {
        return reportDisplayIds;
    }

    public void setReportDisplayIds(LinkedList<Integer> reportDisplayIds) {
        this.reportDisplayIds = reportDisplayIds;
    }

    public ReportDisplayTotals getReportDisplayTotals() {
        return reportDisplayTotals;
    }

    public void setReportDisplayTotals(ReportDisplayTotals reportDisplayTotals) {
        this.reportDisplayTotals = reportDisplayTotals;
    }

    public DateAggregation getDateAggregation() {
        return dateAggregation;
    }

    public void setDateAggregation(DateAggregation dateAggregation) {
        this.dateAggregation = dateAggregation;
    }

    public static enum DateAggregation{
        DAY,
        MONTH,
        YEAR,
        NONE
    }
    public static enum ReportDisplayTotals {
        SUM,
        COUNT,
        SUM_AND_COUNT
    }

}
