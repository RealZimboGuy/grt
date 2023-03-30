package com.github.realzimboguy.grt.model;

public class DisplayField {

    private int id;
    private String name;
    private String regex;

    //csv pipe separated options for selection
    private String fieldOptions;

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

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(String fieldOptions) {
        this.fieldOptions = fieldOptions;
    }
}
