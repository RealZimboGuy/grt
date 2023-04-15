package com.github.realzimboguy.grt.repo;

import com.github.realzimboguy.grt.model.*;
import com.github.realzimboguy.grt.util.StaticVariables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

public class GrtRepo {

    Gson gson =  new GsonBuilder().setPrettyPrinting().create();
    private SystemConfig systemConfig;
    private String propertyFile;

    private Logger log = LoggerFactory.getLogger(GrtRepo.class);

    public GrtRepo(String propertyFile) throws IOException {

//        GrepHelper.searchFileForContentHead("","",4);

        this.propertyFile = propertyFile;

        File f = new File(propertyFile);
        if (f.exists()){
            log.info("System Config Exists");
            systemConfig = gson.fromJson(Files.readString(Paths.get(propertyFile)), SystemConfig.class);
        }else {
            log.info("System Config Does Not Exist");
            systemConfig = new SystemConfig();
            systemConfig.getProperties().put(StaticVariables.Properties.USERNAME, "grtadmin");
            systemConfig.getProperties().put(StaticVariables.Properties.PASSWORD, "grtadmin");
            systemConfig.getProperties().put(StaticVariables.Properties.BASE_PATH, "/grt/");
            systemConfig.getProperties().put(StaticVariables.Properties.JOB_HISTORY_RETENTION_DAYS, "100");


            //this is for getting a feel for it all
            populateSampleData(systemConfig);

            saveSystemConfig();
        }
    }

    private void populateSampleData(SystemConfig systemConfig) throws IOException {

        log.info("Populate Sample Data");

        String basePath = systemConfig.getProperties().get(StaticVariables.Properties.BASE_PATH);

        checkDirectory(basePath );
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_DATA);
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_REPORTS);
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_LOGS);
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_SETTINGS);



        String sampleDirName = "sample";
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_REPORTS  + "/" + sampleDirName);
        checkDirectory(basePath + StaticVariables.Properties.DIRECTORY_LOGS  + "/" + sampleDirName);



        ReportDirectory reportDirectory = new ReportDirectory();
        reportDirectory.setId(1);
        reportDirectory.setDir(sampleDirName);
        reportDirectory.setName(sampleDirName);
        reportDirectory.setDataType(ReportDirectory.DataType.JSON);
        reportDirectory.setReportFilePattern("yyyy.txt.gz");
        reportDirectory.setType(ReportDirectory.ReportDirectoryType.YEAR);
        systemConfig.getReportDirectories().put(reportDirectory.getId(), reportDirectory);

        writeResourceToDir("sample_data","2010.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2011.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2012.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2013.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2014.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2015.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2016.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);
        writeResourceToDir("sample_data","2017.txt.gz",basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + sampleDirName);

        Report report = new Report();
        report.setId(1);
        report.setName("Country Count Report in Europe");
        report.setDescription("This report shows the number of times a country appears in the log file");
        report.setReportDirectoryId(1);
        report.setDateAggregation(Report.DateAggregation.YEAR);
        report.setReportDisplayType(Report.ReportDisplayType.COUNT);
        report.setReportDisplayIds(new LinkedList<>());
        report.getReportDisplayIds().add(1);

        systemConfig.getReports().put(report.getId(), report);


        Filter filter = new Filter();
        filter.setId(1);
        filter.setName("Europe");
        filter.setRegex("\"\\\"Region\\\":\\\"Europe\"");
        systemConfig.getFilters().put(filter.getId(), filter);


        Filter filter2 = new Filter();
        filter2.setId(2);
        filter2.setName("Andorra");
        filter2.setRegex("\"\\\"Country\\\":\\\"Andorra\"");
        systemConfig.getFilters().put(filter2.getId(), filter2);


        FilterGroup filterGroup = new FilterGroup();
        filterGroup.setId(1);
        filterGroup.setName("Europe");
        filterGroup.setFilterGroupType(FilterGroup.FilterGroupType.INCLUDE);
        filterGroup.setFilterIds(new LinkedList<>());
        filterGroup.getFilterIds().add(1);
        filterGroup.getFilterIds().add(2);
        systemConfig.getFilterGroups().put(filterGroup.getId(), filterGroup);

        ReportDisplay reportDisplay = new ReportDisplay();
        reportDisplay.setId(1);
        reportDisplay.setName("Country");
        reportDisplay.setReportDisplayAggregates(ReportDisplay.ReportDisplayAggregates.COUNT);
        reportDisplay.setFilterGroupId(1);
        systemConfig.getReportDisplays().put(reportDisplay.getId(), reportDisplay);

        ReportJob reportJob = new ReportJob();
        reportJob.setReportJobId(1);
        reportJob.setReportId(1);
        reportJob.setParams("start=2012,end=2017");
        reportJob.setStatus(ReportJob.Status.PENDING);
        reportJob.setAggregateBy(ReportJob.AggregateBy.ALL);

        systemConfig.getReportJobs().put(reportJob.getId(), reportJob);

    }

    public void writeResourceToDir(String sourceDir,String name,String dir) throws IOException {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream(sourceDir + "/" + name)) {
            File targetFile = new File(dir + "/" + name);
            java.nio.file.Files.copy(
                    is,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

        }
    }


    private void checkDirectory(String dir){
        File f = new File(dir);
        if (!f.exists()){
            f.mkdir();
        }
    }


    public synchronized void saveSystemConfig() {
        try{
        log.info("saving system config");
        Files.write(Paths.get(propertyFile), gson.toJson(systemConfig).getBytes());
        }catch (Exception e){
            log.error("Error saving system config",e);
        }
    }

    public void updateReportJob(int id, ReportJob.Status status, String statusMessage) {
        systemConfig.getReportJobs().get(id).setStatus(status);
        systemConfig.getReportJobs().get(id).setStatusMessage(statusMessage);
        saveSystemConfig();
    }

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public Report getReport(int reportId) {
        return systemConfig.getReports().get(reportId);
    }

    public ReportDirectory getReportDirectory(int reportDirectoryId) {
        return systemConfig.getReportDirectories().get(reportDirectoryId);
    }

    public List<ReportDisplay> getReportDisplays(Report report) {

        List<ReportDisplay> result = new LinkedList<>();
        for (Integer id : report.getReportDisplayIds()){
            result.add(systemConfig.getReportDisplays().get(id));
        }
        return result;
    }
}
