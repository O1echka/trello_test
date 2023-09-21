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

public class GetBoardTest {

    private static final String BASE_URL = "https://api.trello.com";
    private static final String GET_A_BOARD_RELATIVE_URL = "/1/boards/{board_id}";
    private static final String DELETE_A_BOARD_RELATIVE_URL = "/1/boards/{board_id}";
    private static final String CREATE_A_BOARD_RELATIVE_URL = "/1/boards";
    private static final String USER_KEY = "c2c0d60d00a3cc11b418550d78e00f0a";
    private static final String USER_TOKEN = "ATTA91b4055d31efdbae21bf0305514f968fc5e31c8c2dca3ab4893d0d68c36e9429A17F1473";

    private String boardId;
    private String newBoardNAme = "My new board " + LocalDateTime.now();

    private RequestSpecification commonRequestWithKeyAndToken() {
        return RestAssured.given() //create base empty request
                .baseUri(BASE_URL)  //add base URL (https://api.trello.com) to request
                .queryParam("key", USER_KEY) //add 'key' query parameter
                .queryParam("token", USER_TOKEN) //add 'token' query parameter
                .log().all(); //print request to console
    }

    @BeforeEach //it will run this method BEFORE each method annotated with @Test in this class
    public void createBoard() {
        RequestSpecification createBoardRequest = commonRequestWithKeyAndToken()
                .queryParam("name", newBoardNAme) //add 'name' query parameter
                .contentType(ContentType.JSON); //Trello API needs it

        Response createBoardResponse = createBoardRequest.post(CREATE_A_BOARD_RELATIVE_URL);
        createBoardResponse.prettyPeek(); //print response from Trello console
        boardId = createBoardResponse.getBody() // Find response body
                .jsonPath() //Work with response body in JSON format
                .getString("id"); // Find in JSON body 'id' and save its value
    }

    @Test
    public void checkGetBoard() {
        RequestSpecification getBoardRequest = commonRequestWithKeyAndToken()
                .queryParam("fields", "id,name,desc") //add 'fields' query parameter
                .pathParam("board_id", boardId); //add 'board_id' path parameter

        Response getBoardResponse = getBoardRequest.get(GET_A_BOARD_RELATIVE_URL); //send request to relative URL
        getBoardResponse.prettyPrint();//print to console everything about RESPONSE
        getBoardResponse.then()
                .statusCode(200) //check response status code is 200
                .body("name", Matchers.is(newBoardNAme)); //check element with name 'name' in response is equal to 'Testing'
    }

    @AfterEach //it will run this method AFTER each method annotated with @Test in this class
    public void deleteBoard() {
        RequestSpecification deleteBoardRequest = commonRequestWithKeyAndToken()
                .pathParam("board_id", boardId); //add 'board_id' path parameter

        Response deleteBoardResponse = deleteBoardRequest.delete(DELETE_A_BOARD_RELATIVE_URL);//send request to relative URL
        deleteBoardResponse.prettyPrint();//print to console everything about RESPONSE
        deleteBoardResponse.then()
                .statusCode(200);//check response status code is 200
        String actualValue = deleteBoardResponse.getBody() //Find response body
                .jsonPath() //Work with response body in JSON format
                .getString("_value"); //Find element with name '_value' and save to variable
        Assertions.assertNull(actualValue); //check value is null
    }
}
