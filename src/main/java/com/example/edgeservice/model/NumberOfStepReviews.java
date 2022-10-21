package com.example.edgeservice.model;

public class NumberOfStepReviews {
    private String name;

    private Integer numberOfSteps;

    public NumberOfStepReviews(String name, Integer numberOfSteps) {
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
