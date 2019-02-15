package JSONBlobTest;

import com.sun.org.apache.xpath.internal.SourceTree;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class JsonBlob {

    static Logger logger = Logger.getLogger(JsonBlob.class);

    final Header header1 = new Header("Content-type", "application/json");
    final Header header2 = new Header("Accept", "application/json");
    private Headers headers = new Headers(header1, header2);

    //Method to create new blob resource and returning the location header as String
    public String getLocationHeader(String path){

        logger.info("Running test " + Thread.currentThread().getStackTrace()[1].getMethodName());
        //Get the file path to upload to the blob
        File file = new File(path);

        //Get Response object after posting blob
        Response resp = given().when().headers(headers).contentType(ContentType.JSON).body(file).post("https://jsonblob.com/api/jsonBlob");

        //Store status code for create new blob
        int statuscode = resp.statusCode();
        logger.info("Status code of post call is " + statuscode);

        //Assert that the status code after post method is equal to 201 (Resourse created)
        Assert.assertEquals(statuscode, 201);

        //Return location header as string
        String location = resp.getHeader("Location");
        logger.info("Resourse header location " + location);
        return location;
    }


    @Test(priority=1)
    //Method to post new blob
    public void createBlob(){

        logger.info("Running test " + Thread.currentThread().getStackTrace()[1].getMethodName());
        //Upload JSON file from this location
        File file = new File("src/test/Resources/blob.json");

        //POST call to create new blob and get response object
        Response resp = given().when().headers(headers).contentType(ContentType.JSON).body(file).post("https://jsonblob.com/api/jsonBlob");

        //Store status code for create new blob
        int statusCode = resp.statusCode();
        logger.info("Status code for post call is " + statusCode + " and Location is " + resp.getHeader("Location"));

        //Assert that the status code after post method is equal to 201 (Resourse created)
        Assert.assertEquals(statusCode, 201);
        logger.info("Create blob test executed");
    }


    @Test(priority=2)
    public void getBlob(){

        logger.info("Running test " + Thread.currentThread().getStackTrace()[1].getMethodName());
        //Get the location header after creating new blob
        String location = getLocationHeader("src/test/Resources/blob.json");
        logger.info("Location header after new blob created is " + location);

        //Run GET call on the newly created blob to check status code as 200 and content type as Application/json
        given().get(location).then().statusCode(200).and().contentType(ContentType.JSON);
        logger.info("Get blob test executed");
    }

    @Test(priority=3)
    public void updateBlob(){
        logger.info("Running test " + Thread.currentThread().getStackTrace()[1].getMethodName());
        File file = new File("src/test/Resources/blob.json");

        //POST call to create new blob and get response object
        Response resp = given().when().headers(headers).contentType(ContentType.JSON).body(file).post("https://jsonblob.com/api/jsonBlob");

        //Store status code for create new blob
        int statusCode = resp.statusCode();
        logger.info("Status code of post call is " + statusCode);

        //Assert that the status code is 201 after new blob is created
        Assert.assertEquals(statusCode, 201);

        //Store location header in the String location.
        String location = resp.getHeader("Location");
        logger.info("Location header is " +location);

        //Upload a new file to PUT new JSON for existing resourse.
        File file1 = new File("src/test/Resources/updateblob.json");
        Response resp1 = given().when().headers(headers).body(file1).put(location);

        logger.info("New resourse body created is " +resp1.getBody());

        //Assert that the existing body is updated with new body
        Assert.assertNotEquals(resp.getBody(), resp1.getBody());

    }

    @Test(priority=4)
    public void deleteBlob(){
        logger.info("Running test " + Thread.currentThread().getStackTrace()[1].getMethodName());
        //Get the location header after creating new blob
        String location = getLocationHeader("src/test/Resources/blob.json");
        logger.info("Location header after new blob created is " + location);

        //Get response object after  DELETE operation is executed
        Response resp1 = given().delete(location);

        //Get the status code of the DELETE operation
        int statusCode1 = resp1.statusCode();
        logger.info("After resoiurse is deleted the status code is " +statusCode1);

        //Assert that the the Delete status code is 200
        Assert.assertEquals(statusCode1, 200);

        //Try to get the Deleted resouerse
        Response resp2 = given().get(location);

        //Get the status code for deleted resourse
        int statusCode2 = resp2.statusCode();
        logger.info("GET call Status code of Deleted resourse is " +statusCode2);

        //Assert that the GET call gives status code as 404 for deleted resourse.
        Assert.assertEquals(statusCode2, 404);
    }
}
