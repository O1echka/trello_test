import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CreateNewCard {

    private static final String BASE_URL = "https://api.trello.com";
 //   private static final String GET_LIST = "/1/boards/vas1rAUW/lists";
    private static final String CREATE_A_CARD_RELATIVE_URL = "/1/cards?idList=60fd3fee358d427c395e7fdd";
    private static final String GET_A_CARD_RELATIVE_URL = "/1/cards/{id_card}";
    private static final String DELETE_A_CARD_RELATIVE_URL = "/1/cards/{id_card}";
    private static final String USER_KEY = "c2c0d60d00a3cc11b418550d78e00f0a";
    private static final String USER_TOKEN = "ATTA91b4055d31efdbae21bf0305514f968fc5e31c8c2dca3ab4893d0d68c36e9429A17F1473";

    private String cardId;
    private String cardName = "new card test " + LocalDateTime.now();

    private RequestSpecification commonRequestWithKeyAndToken() {
        return RestAssured.given() //create base empty request
                .baseUri(BASE_URL)  //add base URL (https://api.trello.com) to request
                .queryParam("key", USER_KEY) //add 'key' query parameter
                .queryParam("token", USER_TOKEN) //add 'token' query parameter
                .log().all(); //print request to console
    }

    @BeforeEach

    public void createCard() {
        //    RequestSpecification request = RestAssured.given()
        //           .baseUri(BASE_URL)
        //           .queryParam("key", USER_KEY)
        //          .queryParam("token", USER_TOKEN)
        //           .log().all();

        RequestSpecification createCardRequest = commonRequestWithKeyAndToken()
                .queryParam("name", cardName)
                .contentType(ContentType.JSON);

        Response createCardResponse = createCardRequest.post(CREATE_A_CARD_RELATIVE_URL);
        createCardResponse.prettyPeek();
        cardId = createCardResponse.getBody()
                .jsonPath()
                .getString("id");
    }

    @Test
    public void checkGetCard() {
        RequestSpecification getCardRequest = commonRequestWithKeyAndToken()
                .pathParam("id_card", cardId);
        Response getCardResponse = getCardRequest.get(GET_A_CARD_RELATIVE_URL);
        getCardResponse.prettyPrint();
        getCardResponse.then()
                .statusCode(200)
                .body("name", Matchers.is(cardName));
    }

    @AfterEach
    public void deleteBoard() {
        RequestSpecification deleteCardRequest = commonRequestWithKeyAndToken()
                .pathParam("id_card", cardId);

        Response deleteCardResponse = deleteCardRequest.delete(DELETE_A_CARD_RELATIVE_URL);
        deleteCardResponse.prettyPrint();
        deleteCardResponse.then()
                .statusCode(200);
        String actualValue = deleteCardResponse.getBody()
                .jsonPath()
                .getString("_value");
        Assertions.assertNull(actualValue);
    }
}

