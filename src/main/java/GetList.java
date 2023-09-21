import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class GetList {

    private static final String BASE_URL = "https://api.trello.com";
    private static final String GET_A_LIST_RELATIVE_URL = "/1/boards/vas1rAUW/lists";
    private static final String USER_KEY = "c2c0d60d00a3cc11b418550d78e00f0a";
    private static final String USER_TOKEN = "ATTA91b4055d31efdbae21bf0305514f968fc5e31c8c2dca3ab4893d0d68c36e9429A17F1473";

    public static void main(String[] args) {

        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URL)
                .queryParam("key", USER_KEY)
                .queryParam("token", USER_TOKEN)
                .log().all();


        Response response = request.get(GET_A_LIST_RELATIVE_URL);
        response.prettyPrint();

        response.then()
                .statusCode(200);

    }
}
