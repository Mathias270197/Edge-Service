package com.example.edgeservice.model;

public class NumberOfStepReviews {
    private String name;
    private Integer numberOfStepReviews;

    public NumberOfStepReviews(String name, Integer numberOfStepReviews) {
        this.name = name;
        this.numberOfStepReviews = numberOfStepReviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfStepReviews() {
        return numberOfStepReviews;
    }

    public void setNumberOfStepReviews(Integer numberOfStepReviews) {
        this.numberOfStepReviews = numberOfStepReviews;
    }
}
