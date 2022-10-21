package com.example.edgeservice;


import com.example.edgeservice.model.FigureReview;
import com.example.edgeservice.model.FigureReviewsAndSteps;
import com.example.edgeservice.model.NumberOfStepReviews;
import com.example.edgeservice.model.Step;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FigureReviewStepsControllerUnitTests {

    @Value("${figureReviewService.baseurl}")
    private String figureReviewServiceBaseUrl;

    @Value("${stepService.baseurl}")
    private String stepServiceBaseUrl;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private String textReview1 = "Stukken passen niet goed";
    private String textReview2 = "Moeilijke stappen";
    private String textReview3 = "Eenvoudige stappen";
    private String textReview4 = "Eenvoudige stappen!";


    private FigureReview reviewStijnDuck = new FigureReview("Duck" ,textReview1, 2, "Stijn");
    private FigureReview reviewMathiasDuck = new FigureReview("Duck" ,textReview2, 3, "Mathias");
    private FigureReview reviewStijnChicken = new FigureReview("Chicken" ,textReview3, 4, "Stijn");
    private FigureReview reviewToBeDeleted = new FigureReview("Car" ,textReview4, 3, "Stijn");

    private Step stepDuck1 = new Step(1, "Duck", true);
    private Step stepDuck2 = new Step(2, "Duck", false);
    private Step stepChicken1 = new Step(1, "Chicken", false);
    private Step stepChicken2 = new Step(2, "Chicken", true);

    @BeforeEach
    public void initializeMockserver() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    private List<Step> allStepsFromDuck = Arrays.asList(stepDuck1,stepDuck2 );
    private List<Step> allStepsFromChicken = Arrays.asList(stepChicken1,stepChicken2 );
    private Step[] allStepsFromCar = new Step[0];



//    public List<NumberOfStepReviews> getNumberOfStepReviewsByFigure(){
//
//        List<NumberOfStepReviews> returnList= new ArrayList();
//
//        ResponseEntity<List<FigureReview>> responseEntityFigureReviews =
//                restTemplate.exchange("http://" + figureReviewServiceBaseUrl + "/figureReviews",
//                        HttpMethod.GET, null, new ParameterizedTypeReference<List<FigureReview>>() {});
//
//        List<FigureReview> figures = responseEntityFigureReviews.getBody();
//
//        List<String> figureNames = new ArrayList<>();
//        for (FigureReview figure : figures) {
//            if(!figureNames.contains(figure.getFigureName())){
//                figureNames.add(figure.getFigureName());
//                ResponseEntity<List<Step>> responseEntitySteps =
//                        restTemplate.exchange("http://" + stepServiceBaseUrl + "/steps/figure/" + figure.getFigureName(),
//                                HttpMethod.GET, null, new ParameterizedTypeReference<List<Step>>() {});
//
//                returnList.add(new NumberOfStepReviews(figure.getFigureName(), responseEntitySteps.getBody().size()));
//
//            }
//
//
//        }
//
//        return returnList;
//    }

    private List<FigureReview> allFigureReviews = Arrays.asList(reviewStijnDuck,reviewMathiasDuck,reviewStijnChicken,reviewToBeDeleted );


    @Test
    public void whenGetNumberOfStepReviewsByFigure_thenReturnJson() throws Exception {

        // GET all figureReviews
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviews")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allFigureReviews))
                );

        // GET Duck steps
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Duck")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromDuck))
                );
        // GET Chicken steps
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Chicken")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromDuck))
                );
        // GET Car steps
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Car")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromDuck))
                );


        mockMvc.perform(get("/figureReviews"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].bookTitle", is("Book2")))
                .andExpect(jsonPath("$[0].isbn", is("ISBN2")))
                .andExpect(jsonPath("$[0].userScores[0].userId", is(1)))
                .andExpect(jsonPath("$[0].userScores[0].scoreNumber", is(2)))
                .andExpect(jsonPath("$[1].bookTitle", is("Book1")))
                .andExpect(jsonPath("$[1].isbn", is("ISBN1")))
                .andExpect(jsonPath("$[1].userScores[0].userId", is(1)))
                .andExpect(jsonPath("$[1].userScores[0].scoreNumber", is(1)));
    }





//
//    @GetMapping("/reviewAndStepsOfFigure/{figureName}")
//    public FigureReviewsAndSteps getReviewAndStepsOfFigure(@PathVariable String figureName){
//        ResponseEntity<List<FigureReview>> responseEntityFigureReview =
//                restTemplate.exchange("http://" + figureReviewServiceBaseUrl + "/figureReviewsByName/" + figureName,
//                        HttpMethod.GET, null, new ParameterizedTypeReference<List<FigureReview>>() {});
//
//        List<FigureReview> figures = responseEntityFigureReview.getBody();
//
//
//        ResponseEntity<List<Step>> responseEntitySteps =
//                restTemplate.exchange("http://" + stepServiceBaseUrl + "/steps/figure/" + figureName,
//                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Step>>() {});
//
//        List<Step> steps = responseEntitySteps.getBody();
//
//        FigureReviewsAndSteps figureReviewsAndSteps = new FigureReviewsAndSteps(figures, steps);
//
//        return figureReviewsAndSteps;
//    }
//
//
//
//    @PostMapping("/rankings")
//    public FigureReview addFigureReview(@RequestParam FigureReview figureReview){
//
//        FigureReview newFigureReview =
//                restTemplate.postForObject("http://" + figureReviewServiceBaseUrl + "/figureReview",
//                        new FigureReview(figureReview.getId(), figureReview.getFigureName(), figureReview.getDate(), figureReview.getTextReview(), figureReview.getStars(), figureReview.getUser()),FigureReview.class);
//
//        return newFigureReview;
//    }





}
