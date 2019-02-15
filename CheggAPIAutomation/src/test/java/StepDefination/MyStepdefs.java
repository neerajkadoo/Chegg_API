package StepDefination;

import StepDefination.BlobAPI;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.IOException;

public class MyStepdefs {
    @Given("^ContentType header is set to JSON$")
    public void contenttypeHeaderIsSetToJSON() {
            BlobAPI.setheader();
    }

    @When("^User post the blob payload$")
    public void userPostTheBlobPayload() throws IOException {

        BlobAPI.postBlob();
    }

    @Then("^New resource is created$")
    public void newResourceIsCreated() {
    }
}

