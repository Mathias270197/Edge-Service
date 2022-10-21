package com.example.edgeservice.controller;


import com.example.edgeservice.model.FigureReview;
import com.example.edgeservice.model.FigureReviewsAndSteps;
import com.example.edgeservice.model.NumberOfStepReviews;
import com.example.edgeservice.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class FigureReviewStepsController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${figureReviewService.baseurl}")
    private String figureReviewServiceBaseUrl;

    @Value("${stepService.baseurl}")
    private String stepServiceBaseUrl;

    @GetMapping("/numberOfStepReviewsByFigure")
    public List<NumberOfStepReviews> getNumberOfStepReviewsByFigure(){

        List<NumberOfStepReviews> returnList= new ArrayList();

        ResponseEntity<List<FigureReview>> responseEntityFigureReviews =
                restTemplate.exchange("http://" + figureReviewServiceBaseUrl + "/figureReviews",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<FigureReview>>() {
                        });

        List<FigureReview> figures = responseEntityFigureReviews.getBody();

        List<String> figureNames = new ArrayList<>();
        for (FigureReview figure : figures) {
            if (!figureNames.contains(figure.getFigureName())) {
                figureNames.add(figure.getFigureName());
                ResponseEntity<List<Step>> responseEntitySteps =
                        restTemplate.exchange("http://" + stepServiceBaseUrl + "/steps/figure/" + figure.getFigureName(),
                                HttpMethod.GET, null, new ParameterizedTypeReference<List<Step>>() {
                                });

                returnList.add(new NumberOfStepReviews(figure.getFigureName(), responseEntitySteps.getBody().size()));
            }

        }
        return returnList;
    }

    @GetMapping("/reviewAndStepsOfFigure/{figureName}")
    public FigureReviewsAndSteps getReviewAndStepsOfFigure(@PathVariable String figureName){
        ResponseEntity<List<FigureReview>> responseEntityFigureReview =
                restTemplate.exchange("http://" + figureReviewServiceBaseUrl + "/figureReviewsByName/" + figureName,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<FigureReview>>() {});

        List<FigureReview> figures = responseEntityFigureReview.getBody();


        ResponseEntity<List<Step>> responseEntitySteps =
                restTemplate.exchange("http://" + stepServiceBaseUrl + "/steps/figure/" + figureName,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Step>>() {});

        List<Step> steps = responseEntitySteps.getBody();

        FigureReviewsAndSteps figureReviewsAndSteps = new FigureReviewsAndSteps(figures, steps);

        return figureReviewsAndSteps;
    }



    @PostMapping("/figureReview")
    public FigureReview addFigureReview(@RequestBody FigureReview figureReview){


        FigureReview newFigureReview =
                restTemplate.postForObject("http://" + figureReviewServiceBaseUrl + "/figureReview",
                        new FigureReview(figureReview.getId(), figureReview.getFigureName(), figureReview.getDate(), figureReview.getTextReview(), figureReview.getStars(), figureReview.getUser()),FigureReview.class);

        return newFigureReview;
    }



    @PutMapping("/figureReview")
    public FigureReview updateRanking(@RequestBody FigureReview figureReview){

        FigureReview newFigureReview =
                restTemplate.getForObject("http://" + figureReviewServiceBaseUrl + "/figureReviewByNameAndUser/" + figureReview.getFigureName() + "/" + figureReview.getUser(),
                        FigureReview.class);

        String newTextReview = figureReview.getTextReview();
        Integer newStars = figureReview.getStars();
        boolean adjusted = false;

        if (newTextReview != null) {
            newFigureReview.setTextReview(newTextReview);
            adjusted = true;
        }

        if (newStars != null) {
            newFigureReview.setStars(figureReview.getStars());
            adjusted = true;
        }
        if (adjusted) {
            newFigureReview.setDate(new Date());
        }


        ResponseEntity<FigureReview> responseEntityFigureReview =
                restTemplate.exchange("http://" + figureReviewServiceBaseUrl + "/figureReview",
                        HttpMethod.PUT, new HttpEntity<>(figureReview), FigureReview.class);

        FigureReview retrievedFigureReview = responseEntityFigureReview.getBody();

        return retrievedFigureReview;

    }


    @DeleteMapping("/figureReviewByNameAndUser/{figureName}/{user}")
    public ResponseEntity deleteRanking(@PathVariable String figureName, @PathVariable String user){

        restTemplate.delete("http://" + figureReviewServiceBaseUrl + "figureReviews/name/"+figureName+"/user/" + user);

        return ResponseEntity.ok().build();
    }

}
