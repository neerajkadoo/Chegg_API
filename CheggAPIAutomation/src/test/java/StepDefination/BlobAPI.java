package StepDefination;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class BlobAPI {

    public static void setheader(){
        given().contentType(ContentType.JSON).accept("application/json");
    }

    public static String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static void postBlob() throws IOException {

        String jsonBody = generateStringFromResource("/Users/neerajkadoo/Documents/Code_learning/Automation/CheggAPIAutomation/src/test/Resources/blob.json");
        System.out.println(jsonBody);
        given().contentType(ContentType.JSON).
        given().body(jsonBody)
                .when().post("https://jsonblob.com/api/jsonBlob")
                .then().statusCode(201).log().all();
        Response response = null;
        String contentType = response.header("Location");
        System.out.println("Content-Type value: " + contentType);


    }

    public static void getblobID(){

    }
}
