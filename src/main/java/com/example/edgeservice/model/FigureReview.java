package com.example.edgeservice.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FigureReview {

    private String id;
    private String figureName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date = new Date();
    private String textReview;
    private Integer stars;

    private String user;

    public FigureReview() {
    }

//    public FigureReview(String id, String figureName, Date date, String textReview, Integer stars, String user) {
//        this.id = id;
//        this.figureName = figureName;
//        this.date = date;
//        this.textReview = textReview;
//        this.stars = stars;
//        this.user = user;
//    }

    public FigureReview(String figureName, String textReview, Integer stars, String user) {
        this.figureName = figureName;
        this.textReview = textReview;
        this.stars = stars;
        this.user = user;
    }

    public FigureReview(String id, String figureName, Date date, String textReview, Integer stars, String user) {
        this.id = id;
        this.figureName = figureName;
        this.date = date;
        this.textReview = textReview;
        this.stars = stars;
        this.user = user;
    }

    public String getFigureName() {
        return figureName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;

    }

    public void setFigureName(String figureName) {
        this.figureName = figureName;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
