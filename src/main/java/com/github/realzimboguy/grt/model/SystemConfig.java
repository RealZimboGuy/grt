package com.github.realzimboguy.grt.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SystemConfig {


    private LinkedHashMap<Integer, DisplayField> displayFields = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Filter> filters= new LinkedHashMap<>();
    private LinkedHashMap<Integer, FilterGroup> filterGroups= new LinkedHashMap<>();

    private LinkedHashMap<Integer, Report> reports= new LinkedHashMap<>();
    private LinkedHashMap<Integer, ReportDirectory> reportDirectories= new LinkedHashMap<>();

    private LinkedHashMap<Integer, ReportDisplay> reportDisplays= new LinkedHashMap<>();

    private LinkedHashMap<String, String> properties= new LinkedHashMap<>();

    private LinkedHashMap<Integer, ReportJob> reportJobs= new LinkedHashMap<>();

    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(LinkedHashMap<String, String> properties) {
        this.properties = properties;
    }

    public LinkedHashMap<Integer, DisplayField> getDisplayFields() {
        return displayFields;
    }

    public void setDisplayFields(LinkedHashMap<Integer, DisplayField> displayFields) {
        this.displayFields = displayFields;
    }

    public LinkedHashMap<Integer, Filter> getFilters() {
        return filters;
    }

    public void setFilters(LinkedHashMap<Integer, Filter> filters) {
        this.filters = filters;
    }

    public LinkedHashMap<Integer, FilterGroup> getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(LinkedHashMap<Integer, FilterGroup> filterGroups) {
        this.filterGroups = filterGroups;
    }

    public LinkedHashMap<Integer, Report> getReports() {
        return reports;
    }

    public void setReports(LinkedHashMap<Integer, Report> reports) {
        this.reports = reports;
    }

    public LinkedHashMap<Integer, ReportDirectory> getReportDirectories() {
        return reportDirectories;
    }

    public void setReportDirectories(LinkedHashMap<Integer, ReportDirectory> reportDirectories) {
        this.reportDirectories = reportDirectories;
    }

    public LinkedHashMap<Integer, ReportDisplay> getReportDisplays() {
        return reportDisplays;
    }

    public void setReportDisplays(LinkedHashMap<Integer, ReportDisplay> reportDisplays) {
        this.reportDisplays = reportDisplays;
    }

    public LinkedHashMap<Integer, ReportJob> getReportJobs() {
        return reportJobs;
    }

    public void setReportJobs(LinkedHashMap<Integer, ReportJob> reportJobs) {
        this.reportJobs = reportJobs;
    }


}
