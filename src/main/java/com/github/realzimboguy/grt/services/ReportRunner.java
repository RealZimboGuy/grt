package com.github.realzimboguy.grt.services;

import com.github.realzimboguy.grt.model.*;
import com.github.realzimboguy.grt.repo.GrtRepo;
import com.github.realzimboguy.grt.util.StaticVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportRunner implements Runnable{

    private final GrtRepo grtRepo;

    private Logger log = LoggerFactory.getLogger(ReportRunner.class);

    public ReportRunner( GrtRepo grtRepo) {
        this.grtRepo = grtRepo;
    }


    @Override
    public void run() {


        while (true) {
            try {
                Thread.sleep(5000);
                //get all pending jobs
                grtRepo.getSystemConfig().getReportJobs().entrySet().stream().filter(entry -> entry.getValue().getStatus() == ReportJob.Status.PENDING).forEach(entry -> {
                    //run job
                    runReportJob(entry.getValue());
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private void runReportJob(ReportJob reportJob) {

        log.info("running Job:{}",reportJob);
        grtRepo.updateReportJob(reportJob.getId(), ReportJob.Status.RUNNING, "Running Job");

        Report report = grtRepo.getReport(reportJob.getReportId());
        ReportDirectory reportDirectory = grtRepo.getReportDirectory(report.getReportDirectoryId());
        List<ReportDisplay> reportDisplays = grtRepo.getReportDisplays(report);

        try {
            switch (reportDirectory.getType()) {
                case UNSTRUCTURED:
                    runUnstructuredReport(reportJob, report, reportDirectory, reportDisplays);
                    break;
                case YEAR:
                    runYearReport(reportJob, report, reportDirectory, reportDisplays);
                    break;
                case MONTH:
                    runMonthReport(reportJob, report, reportDirectory, reportDisplays);
                    break;
                case DAY:
                    runDayReport(reportJob, report, reportDirectory, reportDisplays);
                    break;
            }
        }catch (IOException e){
            log.error("Error running report",e);
            grtRepo.updateReportJob(reportJob.getId(), ReportJob.Status.FAILED, "Error running report:"+ e.getMessage());
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Completed Job:{}",reportJob);
        grtRepo.updateReportJob(reportJob.getId(), ReportJob.Status.COMPLETED, "Completed Job");

    }

    private void runDayReport(ReportJob reportJob, Report report, ReportDirectory reportDirectory,List<ReportDisplay> reportDisplays) {
        log.info("running day report");
    }

    private void runMonthReport(ReportJob reportJob, Report report, ReportDirectory reportDirectory,List<ReportDisplay> reportDisplays) {
        log.info("running month report");
    }

    private void runYearReport(ReportJob reportJob, Report report, ReportDirectory reportDirectory,List<ReportDisplay> reportDisplays) throws IOException {
        String basePath = grtRepo.getSystemConfig().getProperties().get(StaticVariables.Properties.BASE_PATH);
        String  fullDir = basePath + StaticVariables.Properties.DIRECTORY_DATA  + "/" + reportDirectory.getDir();
        log.info("running year report");
        List<Path> filesMatching = getFilesInDirectoryMatchingFilterYear(fullDir, reportDirectory.getReportFilePattern(), reportJob);
        log.info("filesMatching:{}",filesMatching);
    }

    private void runUnstructuredReport(ReportJob reportJob, Report report, ReportDirectory reportDirectory,List<ReportDisplay> reportDisplays) {
        log.info("running unstructured report");
    }

    public List<Path> getFilesInDirectoryMatchingFilterYear(String directory, String pattern, ReportJob reportJob) throws IOException {

        List<String> validFileNames = new ArrayList<>();
        int start = Integer.parseInt(reportJob.getParam("start"));
        int end = Integer.parseInt(reportJob.getParam("end"));

        if (pattern.contains("yyyy")){
            for (int i = start; i <= end; i++) {
                validFileNames.add(pattern.replace("yyyy", String.valueOf(i)));
            }
        }else if (pattern.contains("yy")){
            for (int i = start; i <= end; i++) {
                validFileNames.add(pattern.replace("yy", String.valueOf(i).substring(2)));
            }
        }else {
            throw new RuntimeException("pattern must contain yyyy or yy");
        }

        List<Path> result;
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            result = walk.filter(Files::isRegularFile)
                    .filter(path -> validFileNames.contains(path.getFileName().toString()))
                    .collect(Collectors.toList());
        }
        //order results by validFileNames
        result.sort((o1, o2) -> {
            int index1 = validFileNames.indexOf(o1.getFileName().toString());
            int index2 = validFileNames.indexOf(o2.getFileName().toString());
            return index1 - index2;
        });

        return result;
    }
}
