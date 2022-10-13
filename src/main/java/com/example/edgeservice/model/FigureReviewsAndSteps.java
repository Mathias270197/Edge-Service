package com.example.edgeservice.model;

import java.util.List;

public class FigureReviewsAndSteps {
    private List<FigureReview> figureReviews;
    private List<Step> steps;

    public FigureReviewsAndSteps(List<FigureReview> figureReviews, List<Step> steps) {
        this.figureReviews = figureReviews;
        this.steps = steps;
    }

    public List<FigureReview> getFigureReviews() {
        return figureReviews;
    }

    public void setFigureReviews(List<FigureReview> figureReviews) {
        this.figureReviews = figureReviews;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
