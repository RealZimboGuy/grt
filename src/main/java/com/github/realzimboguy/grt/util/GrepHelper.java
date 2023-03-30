package com.github.realzimboguy.grt.util;

import java.io.*;
import java.util.Scanner;

public class GrepHelper {

    public static String searchFileForContentHead(String path,String file,int headSize){
        ProcessBuilder processBuilder = new ProcessBuilder();

        // -- Linux --

        // Run a shell command
        processBuilder.command("/bin/bash", "-c", "ls / | head -n" + headSize + " | grep oo");

        // Run a shell script
        //processBuilder.command("path/to/hello.sh");

        // -- Windows --

        // Run a command
        //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

        // Run a bat file
        //processBuilder.command("C:\\Users\\mkyong\\hello.bat");

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            inheritIO(process.getInputStream(), System.out);
            inheritIO(process.getErrorStream(), System.err);

//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//
//            BufferedReader readerErr = new BufferedReader(
//                    new InputStreamReader(process.getErrorStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line + "\n");
//            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                System.out.println("ERROR!");
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    return null;
    }
    private static void inheritIO(final InputStream src, final PrintStream dest) {
        new Thread(new Runnable() {
            public void run() {
                Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    dest.println(sc.nextLine());
                }
            }
        }).start();
    }
}
