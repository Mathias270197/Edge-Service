package com.example.edgeservice.model;

public class NumberOfSteps {
    private String name;
    private Integer numberOfSteps;

    public NumberOfSteps(String name, Integer numberOfSteps) {
        this.name = name;
        this.numberOfSteps = numberOfSteps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(Integer numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }
}
