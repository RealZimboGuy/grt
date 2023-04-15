package com.github.realzimboguy.grt;

import com.github.realzimboguy.grt.model.DisplayField;
import com.github.realzimboguy.grt.model.Report;
import com.github.realzimboguy.grt.util.CommandHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCommands {


    @Test
    public void testAwkCommandOne(){

        String expectedCommand = "awk 'match($0,/Country\":\"([^\"]{1,})\"/,a) {printf a[1]};match($0,/Total Revenue\":\"([^\"]{1,})\"/,b) {printf \"\\t\" b[1]}; {print \"\"} '";

        //awk 'match($0,/Country":"([^"]{1,})"/,a) {printf a[1]};match($0,/Total Revenue":"([^"]{1,})"/,b) {printf "\t" b[1]}; {print ""} '


        Report report = new Report();
        report.setReportDisplayType(Report.ReportDisplayType.FIELDS_ONLY);

        List<DisplayField> displayFields = new ArrayList<>();

        DisplayField displayField1 = new DisplayField();
        displayField1.setId(1);
        displayField1.setName("Country");
        displayField1.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField1.setRegex("Country\":\"([^\"]{1,})\"");
        displayFields.add(displayField1);

        DisplayField displayField2 = new DisplayField();
        displayField2.setId(2);
        displayField2.setName("Total Revenue");
        displayField2.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField2.setRegex("Total Revenue\":\"([^\"]{1,})\"");
        displayFields.add(displayField2);

        String command = CommandHelper.getAwkCommand(report,displayFields);

        Assertions.assertEquals(expectedCommand,command);


    }

}
