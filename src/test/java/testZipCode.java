
import com.sun.xml.internal.ws.policy.AssertionSet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class testZipCode {
    public static RequestSpecification resquestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createRequestSpecification(){
        resquestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").build();
        responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
                .expectStatusCode(200).build();
            }

    @Test
    public void karnatakaZipCodeStatusCode(){
        given().
                spec(resquestSpec)
                .log().all()
                .when().get("IN/560001").
                then()
                .spec(responseSpec)
                .log().body()
                .and()
                .assertThat().
                statusCode(200);
    }

    @Test
    public void karnatakaZipCodeContentType(){
        given().
                log().all()
                .when().get("http://api.zippopotam.us/IN/560001").
                then().
                log().body()
                .assertThat().
                contentType(ContentType.JSON);
    }

    @Test
    public void karnatakaZipCodeResponseBody(){
        given().
                spec(resquestSpec)
                .log().all()
                .when().get("IN/560001").
                then()
                .spec(responseSpec)
                .log().body()
                .and()
                .assertThat()
                .body("places[0].'place name'", equalTo("Rajbhavan"));
    }

    @Test
    public void karnatakaZipCodeResponseBody2(){
        String placeName = given().
                spec(resquestSpec)
                .log().all()
                .when().get("IN/560001").
                then()
                .spec(responseSpec)
                .log().body()
                .and()
                .extract()
                .path("places[0].'place name'");

        Assert.assertEquals(placeName, "Rajbhavan");
    }

    @Test
    public void karnatakaZipCodeHasItem(){
        given().
                log().all()
                .when().get("http://api.zippopotam.us/IN/560001").
                then().
                log().body()
                .assertThat()
                .body("places.'place name'", hasItem("Mahatma Gandhi Road"));
    }

}
