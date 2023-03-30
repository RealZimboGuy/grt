package com.github.realzimboguy.grt.model;

public class ReportDirectory {

    private int id;
    private String name;
    private String dir;
    private DataType dataType;
    private String reportFilePattern;
    private ReportDirectoryType type;



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

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public ReportDirectoryType getType() {
        return type;
    }

    public void setType(ReportDirectoryType type) {
        this.type = type;
    }


    public String getReportFilePattern() {
        return reportFilePattern;
    }

    public void setReportFilePattern(String reportFilePattern) {
        this.reportFilePattern = reportFilePattern;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public static enum DataType {
        JSON,
        TEXT,
        CSV
    }

    public static enum ReportDirectoryType {
            UNSTRUCTURED,
        YEAR,
        MONTH,
        DAY
    }

}
