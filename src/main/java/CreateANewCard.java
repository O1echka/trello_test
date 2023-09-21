import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class CreateANewCard {
    // https://api.trello.com/1/cards?idList=5abbe4b7ddc1b351ef961414&key=APIKey&token=APIToken
    private static final String BASE_URL = "https://api.trello.com";
    private static final String CREATE_A_NEW_CARD_RELATIVE_URL = "/1/cards?idList={listId}";
    private static final String KEY = "c2c0d60d00a3cc11b418550d78e00f0a";
    private static final String TOKEN = "ATTA91b4055d31efdbae21bf0305514f968fc5e31c8c2dca3ab4893d0d68c36e9429A17F1473";


    public static void main(String[] args) {
        String listId = "60fd3fee358d427c395e7fdd";

        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URL)
                .queryParam(listId)
                .queryParam(KEY)
                .queryParam(TOKEN)
                .log().all();

        Response response = request.get(CREATE_A_NEW_CARD_RELATIVE_URL);
        response.prettyPrint();

        response.then()
                .statusCode(200)
                .body("name", Matchers.is("new card test"));

    }

}
