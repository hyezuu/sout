package com.sout.common.utils.testmodel;

public class TestBean {
    private String name;
    private int age;
    private String note;

    public TestBean() {
    }

    public TestBean(String name, int age, String note) {
        this.name = name;
        this.age = age;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}