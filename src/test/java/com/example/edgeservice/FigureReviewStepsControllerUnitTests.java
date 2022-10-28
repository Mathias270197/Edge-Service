package com.example.edgeservice;

import com.example.edgeservice.model.FigureReview;
import com.example.edgeservice.model.Step;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
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


    private List<FigureReview> duckReviews = Arrays.asList(reviewStijnDuck,reviewMathiasDuck );


    private List<FigureReview> allFigureReviews = Arrays.asList(reviewStijnDuck,reviewMathiasDuck,reviewStijnChicken,reviewToBeDeleted );


    @Test
    public void whenGetNumberOfStepReviewsByFigure_thenReturnJson() throws Exception {


        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviews")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allFigureReviews))
                );


        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Duck")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromDuck))
                );

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Chicken")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromChicken))

                );

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Car")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromCar))
                );


        mockMvc.perform(get("/numberOfStepReviewsByFigure"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Duck")))
                .andExpect(jsonPath("$[0].numberOfSteps", is(2)))
                .andExpect(jsonPath("$[1].name", is("Chicken")))
                .andExpect(jsonPath("$[1].numberOfSteps", is(2)))
                .andExpect(jsonPath("$[2].name", is("Car")))
                .andExpect(jsonPath("$[2].numberOfSteps", is(0)));
    }

    @Test
    public void whenGetReviewAndStepsOfFigureDuck_thenReturnJson() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviewsByName/Duck")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(duckReviews))
                );

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + stepServiceBaseUrl + "/steps/figure/Duck")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStepsFromDuck))
                );


        mockMvc.perform(get("/reviewAndStepsOfFigure/{figureName}", "Duck"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureReviews", hasSize(2)))
                .andExpect(jsonPath("$.steps", hasSize(2)))
                .andExpect(jsonPath("$.figureReviews[0].figureName", is("Duck")))
                .andExpect(jsonPath("$.figureReviews[0].textReview", is("Stukken passen niet goed")))
                .andExpect(jsonPath("$.figureReviews[0].stars", is(2)))
                .andExpect(jsonPath("$.figureReviews[0].user", is("Stijn")))
                .andExpect(jsonPath("$.figureReviews[1].figureName", is("Duck")))
                .andExpect(jsonPath("$.figureReviews[1].textReview", is("Moeilijke stappen")))
                .andExpect(jsonPath("$.figureReviews[1].stars", is(3)))
                .andExpect(jsonPath("$.figureReviews[1].user", is("Mathias")))
                .andExpect(jsonPath("$.steps[0].stepNumber", is(1)))
                .andExpect(jsonPath("$.steps[0].figure", is("Duck")))
                .andExpect(jsonPath("$.steps[0].stepIsClear", is(true)))
                .andExpect(jsonPath("$.steps[1].stepNumber", is(2)))
                .andExpect(jsonPath("$.steps[1].figure", is("Duck")))
                .andExpect(jsonPath("$.steps[1].stepIsClear", is(false)));

    }



    @Test
    public void whenGetFigureReviewByDuckAndStijn_thenReturnJson() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviewByNameAndUser/Duck/Stijn")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewStijnDuck))
                );


        mockMvc.perform(get("/figureReviewByNameAndUser/{figureName}/{user}", "Duck", "Stijn"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is("Stukken passen niet goed")))
                .andExpect(jsonPath("$.stars", is(2)))
                .andExpect(jsonPath("$.user", is("Stijn")));

    }


    @Test
    public void whenGetAverageStarRatingOfFigure_thenReturnInt() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviewsByName/Duck")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(duckReviews))
                );


        mockMvc.perform(get("/averageStarRatingOfFigure/{figureName}", "Duck"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    public void whenAddFigureReview_thenReturnJson() throws Exception {

        FigureReview reviewMathiasChicken = new FigureReview("Chicken", textReview3, 3, "Mathias");

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReview")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewMathiasChicken))
                );


        mockMvc.perform(post("/figureReview")
                        .content(mapper.writeValueAsString(reviewMathiasChicken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Chicken")))
                .andExpect(jsonPath("$.textReview", is(textReview3)))
                .andExpect(jsonPath("$.user", is("Mathias")))
                .andExpect(jsonPath("$.stars", is(3)));
    }

    @Test
    public void whenUpdateFigureReview_thenReturnJson() throws Exception {

        FigureReview updatedReviewStijnDuck = new FigureReview("Duck" ,textReview1, 2, "Stijn");

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviewByNameAndUser/" + updatedReviewStijnDuck.getFigureName() + "/" + updatedReviewStijnDuck.getUser())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewStijnDuck))
                );

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReview")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedReviewStijnDuck))
                );


        mockMvc.perform(put("/figureReview")
                        .content(mapper.writeValueAsString(updatedReviewStijnDuck))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is(textReview1)))
                .andExpect(jsonPath("$.user", is("Stijn")))
                .andExpect(jsonPath("$.stars", is(2)));

    }

    @Test
    public void whenDeleteFigureReview_thenReturnStatusOk() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + figureReviewServiceBaseUrl + "/figureReviews/name/Car/user/Stijn")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/figureReviewByNameAndUser/{figureName}/{user}", "Car", "Stijn"))
                .andExpect(status().isOk());
    }

}
