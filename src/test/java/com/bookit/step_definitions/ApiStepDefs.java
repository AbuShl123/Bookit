package com.bookit.step_definitions;

import com.bookit.utilities.BookitUtils;
import com.bookit.utilities.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiStepDefs {
    String token;
    Response response;

    @Given("I logged Bookit api as a {string}")
    public void i_logged_bookit_api_as_a(String role) {
        token = BookitUtils.generateTokenByRole(role);
        System.out.println("token = " + token);
    }

    @When("I sent get request to {string} endpoint")
    public void i_sent_get_request_to_endpoint(String endpoint) {
        response = given().accept(ContentType.JSON)
                .header("Authorization", token)
                .when().get(Environment.BASE_URL + endpoint)
                .then().extract().response();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Then("content type is {string}")
    public void content_type_is(String expectedContentType) {
        System.out.println("response.contentType() = " + response.contentType());
        assertEquals(expectedContentType, response.contentType());
    }

    @Then("role is {string}")
    public void role_is(String expectedRole) {
        System.out.println("response.path(\"role\") = " + response.path("role"));
        System.out.println("response.jsonPath().getString(\"role\") = " + response.jsonPath().getString("role"));
        assertEquals(expectedRole, response.path("role"));
    }

    @Then("UI, API, and DataBase user information must be match")
    public void uiAPIAndDataBaseUserInformationMustBeMatch() {

    }

    @When("I sent POST request {string} endpoint with following information")
    public void i_sent_post_request_endpoint_with_following_information(String endpoint, Map<String, String> userInfo) {
        response = given().accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParams(userInfo)
                .when().post(Environment.BASE_URL + endpoint);

    }

    @Then("I delete previously added student")
    public void i_delete_previously_added_student() {

        int idToDelete = response.path("entryiId");

        System.out.println("entryId = " + idToDelete);

        given().header("Authorization", token)
                .pathParam("id", idToDelete)
                .when().delete(Environment.BASE_URL + "/api/student/{id}");
    }

    @When("users send POST request to {string} with following info:")
    public void usersSendPOSTRequestToWithFollowingInfo(String endpoint, Map<String, String> teamInfo) {
        response = given().header("Authorization", token)
                .queryParams(teamInfo)
                .when().post(Environment.BASE_URL + endpoint)
                .then().extract().response();
    }

    @And("DataBase should persist same team info")
    public void databaseShouldPersistSameTeamInfo() {
        System.out.println("like correct");
    }

    @And("User deletes previously added team")
    public void userDeletesPreviouslyAddedTeam() {
        response.prettyPeek();
        int idToDelete = response.path("entryiId");
        System.out.println("idToDelete = " + idToDelete);

        given().header("Authorization", token)
                .pathParam("id", idToDelete)
                .when().delete(Environment.BASE_URL + "/api/teams/{id}");
    }
}
