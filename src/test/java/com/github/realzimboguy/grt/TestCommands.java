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
    public void testAwkCommandOne_fields_only(){

        String expectedCommand = "awk -M -v PREC=100 'match($0,/Country\":\"([^\"]{1,})\"/,a) {printf a[1] \"\\t\"} match($0,/Total Revenue\":\"([^\"]{1,})\"/,b) {printf b[1] \"\\t\"} ; {print \"\"} '";

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

    /**
     * testing group by with sums
     */
    @Test
    public void testAwkCommandTwo_fields_and_sum(){

        String expectedCommand = "awk -M -v PREC=100 'match($0,/Country\":\"([^\"]{1,})\"/,a)match($0,/Region\":\"([^\"]{1,})\"/,b)match($0,/Item Type\":\"([^\"]{1,})\"/,c){agg[a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\"]=a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\" ;var=a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\"} ; {match($0,/Total Revenue\":\"([^\"]{1,})\"/,d); agg_sum[var\"Total Revenue\"]+=d[1]} END { for  (var in agg) printf(\"%s%.2f\\n\", agg[var], agg_sum[var\"Total Revenue\"])}'";


        Report report = new Report();
        report.setReportDisplayType(Report.ReportDisplayType.FIELDS_AND_SUM);

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
        displayField2.setName("Region");
        displayField2.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField2.setRegex("Region\":\"([^\"]{1,})\"");
        displayFields.add(displayField2);

        DisplayField displayField3 = new DisplayField();
        displayField3.setId(2);
        displayField3.setName("Item Type");
        displayField3.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField3.setRegex("Item Type\":\"([^\"]{1,})\"");
        displayFields.add(displayField3);

        DisplayField displayField4 = new DisplayField();
        displayField4.setId(3);
        displayField4.setName("Total Revenue");
        displayField4.setFieldOptions("SUM");
        //Country":"([^"]{1,})"
        displayField4.setRegex("Total Revenue\":\"([^\"]{1,})\"");
        displayFields.add(displayField4);

        String command = CommandHelper.getAwkCommand(report,displayFields);

        Assertions.assertEquals(expectedCommand,command);


    }


    /**
     * testing group by with count
     */
    @Test
    public void testAwkCommandThree_fields_and_count(){

        String expectedCommand = "awk -M -v PREC=100 'match($0,/Country\":\"([^\"]{1,})\"/,a)match($0,/Region\":\"([^\"]{1,})\"/,b)match($0,/Item Type\":\"([^\"]{1,})\"/,c){agg[a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\"]=a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\" ;var=a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\"} ; { agg_count[var\"counter\"]++} END { for  (var in agg) printf(\"%s%.0f\\n\", agg[var], agg_count[var\"counter\"])}'";


        Report report = new Report();
        report.setReportDisplayType(Report.ReportDisplayType.FIELDS_AND_COUNT);

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
        displayField2.setName("Region");
        displayField2.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField2.setRegex("Region\":\"([^\"]{1,})\"");
        displayFields.add(displayField2);

        DisplayField displayField3 = new DisplayField();
        displayField3.setId(2);
        displayField3.setName("Item Type");
        displayField3.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField3.setRegex("Item Type\":\"([^\"]{1,})\"");
        displayFields.add(displayField3);

        DisplayField displayField4 = new DisplayField();
        displayField4.setId(3);
        displayField4.setName("Count");
        displayField4.setFieldOptions("COUNT");
        //Country":"([^"]{1,})"
        displayField4.setRegex("Total Revenue\":\"([^\"]{1,})\"");
        displayFields.add(displayField4);

        String command = CommandHelper.getAwkCommand(report,displayFields);

        Assertions.assertEquals(expectedCommand,command);


    }


    /**
     * testing group by with sum and count
     */
    @Test
    public void testAwkCommandFour_fields_and_sum_and_count(){

        String expectedCommand = "awk -M -v PREC=100 'match($0,/Country\":\"([^\"]{1,})\"/,a)match($0,/Region\":\"([^\"]{1,})\"/,b)match($0,/Item Type\":\"([^\"]{1,})\"/,c){agg[a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\"]=a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\" ;var=a[1]\"\\t\"b[1]\"\\t\"c[1]\"\\t\"} ; {match($0,/Total Revenue\":\"([^\"]{1,})\"/,d); agg_sum[var\"Total Revenue\"]+=d[1] agg_count[var\"counter\"]++} END { for  (var in agg) printf(\"%s%.2f\\t%s\\n\", agg[var], agg_sum[var\"Total Revenue\"], agg_count[var\"counter\"])}'";


        Report report = new Report();
        report.setReportDisplayType(Report.ReportDisplayType.FIELDS_AND_SUM_AND_COUNT);

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
        displayField2.setName("Region");
        displayField2.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField2.setRegex("Region\":\"([^\"]{1,})\"");
        displayFields.add(displayField2);

        DisplayField displayField3 = new DisplayField();
        displayField3.setId(2);
        displayField3.setName("Item Type");
        displayField3.setFieldOptions("JSON_STRING");
        //Country":"([^"]{1,})"
        displayField3.setRegex("Item Type\":\"([^\"]{1,})\"");
        displayFields.add(displayField3);


        DisplayField displayField4 = new DisplayField();
        displayField4.setId(3);
        displayField4.setName("Total Revenue");
        displayField4.setFieldOptions("SUM");
        displayField4.setRegex("Total Revenue\":\"([^\"]{1,})\"");
        displayFields.add(displayField4);

        DisplayField displayField5 = new DisplayField();
        displayField5.setId(3);
        displayField5.setName("Count");
        displayField5.setFieldOptions("COUNT");
        displayField5.setRegex("Total Revenue\":\"([^\"]{1,})\"");
        displayFields.add(displayField5);

        String command = CommandHelper.getAwkCommand(report,displayFields);

        Assertions.assertEquals(expectedCommand,command);


    }

}
