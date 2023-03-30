package com.github.realzimboguy.grt.model;

public class ReportJob {

    private int id;
    private int reportJobId;
    private int reportId;

    private String createdDate;

    private Status status;

    private String fromDate;
    private String toDate;
    private String params;

    private String statusMessage;
    private String lastUpdateDate;


    public int getReportJobId() {
        return reportJobId;
    }

    public void setReportJobId(int reportJobId) {
        this.reportJobId = reportJobId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getParams() {
        return params;
    }
    public String getParam(String key)
    {
        String[] params = this.params.split(",");
        for(String param : params)
        {
            String[] keyVal = param.split("=");
            if(keyVal[0].equals(key))
            {
                return keyVal[1];
            }
        }
        return null;
    }


    public void setParams(String params) {
        this.params = params;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReportJob{" +
                "id=" + id +
                ", reportJobId=" + reportJobId +
                ", reportId=" + reportId +
                ", createdDate='" + createdDate + '\'' +
                ", status=" + status +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", params='" + params + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                '}';
    }

    public static enum Status {
        PENDING,
        RUNNING,
        COMPLETED,
        FAILED
    }
}
