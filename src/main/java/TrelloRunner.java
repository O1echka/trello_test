import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class TrelloRunner {

    private static final String BASE_URL = "https://api.trello.com";
    private static final String GET_A_BOARD_RELATIVE_URL = "/1/boards/{board_id}";
    private static final String USER_KEY = "c2c0d60d00a3cc11b418550d78e00f0a";
    private static final String USER_TOKEN = "ATTA91b4055d31efdbae21bf0305514f968fc5e31c8c2dca3ab4893d0d68c36e9429A17F1473";

    public static void main(String[] args) {
        //TODO: get this ID before test starts
        String boardId = "vas1rAUW";

        RequestSpecification request = RestAssured.given() //create base empty request
                .baseUri(BASE_URL) //add base URL (https://api.trello.com) to request
                .queryParam("fields", "id,name,desc") //add 'fields' query parameter
                .queryParam("key", USER_KEY) //add 'key' query parameter
                .queryParam("token", USER_TOKEN) //add 'token' query parameter
                .pathParam("board_id", boardId) //add 'board_id' path parameter
                .log().all(); //print to console everything about REQUEST

        //send request to relative URL
        //save response to separate variable
        Response response = request.get(GET_A_BOARD_RELATIVE_URL);
        response.prettyPrint();//print to console everything about RESPONSE

        response.then()
                .statusCode(200) //check response status code is 200
                .body("name", Matchers.is("Testing")); //check element with name 'name' in response is equal to 'Testing'
    }
}
