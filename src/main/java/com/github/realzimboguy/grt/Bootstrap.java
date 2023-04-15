package com.github.realzimboguy.grt;

import com.github.realzimboguy.grt.controllers.ApiController;
import com.github.realzimboguy.grt.repo.GrtRepo;
import com.github.realzimboguy.grt.services.ReportRunner;
import io.javalin.Javalin;

import java.io.IOException;
import java.util.Date;

public class Bootstrap {

    private static ApiController apiController;
    private static GrtRepo grtRepo;

    private static Thread reportRunner;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
//        System.out.println("start"+  new Date().getTime());

        apiController = new ApiController(7021);
        grtRepo = new GrtRepo("/grt/grt.properties");
        reportRunner = new Thread(new ReportRunner(grtRepo));
        reportRunner.setName("ReportRunner");
        reportRunner.start();

        System.out.println("started"+  new Date().getTime());
    }
}
