package com.github.realzimboguy.grt.controllers;

import io.javalin.Javalin;

public class ApiController {

    private static Javalin javalin;

    public ApiController(int port) {
        javalin = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(port);
    }


}
