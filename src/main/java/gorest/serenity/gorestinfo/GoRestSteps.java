package gorest.serenity.gorestinfo;

import gorest.serenity.constants.EndPoints;
import gorest.serenity.model.GoRestPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import javax.validation.Valid;
import java.util.HashMap;

public class GoRestSteps {

    @Step("Creating student with name : {0}, email : {1}, gender : {2}, status : {3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        GoRestPojo goRestPojo = new GoRestPojo();
        goRestPojo.setName(name);
        goRestPojo.setEmail(email);
        goRestPojo.setGender(gender);
        goRestPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 50edea31afe89011532d4680cedb11a3860444028939e910e2a0013e9b0ce0a1")
                .body(goRestPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting user information from the email : {0}")
    public HashMap<String, Object> getInfoByEmail(String email) {
        String p1 = "findAll{it.email == '";
        String p2 = "'].get(0)";
        return SerenityRest.given().log().all()
                .when()
                .header("Authorization", "Bearer 50edea31afe89011532d4680cedb11a3860444028939e910e2a0013e9b0ce0a1")
                .get()
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("findAll{it.email == '" + email + "'}.get(0)");
    }

    @Step("Updating user with user_id:{0}, name: {1}, email:{2},gender: {3},status:[4}")
    public ValidatableResponse updateUser(int userId, String name, String email, String gender, String status) {
        GoRestPojo goRestPojo = new GoRestPojo();
        goRestPojo.setName(name);
        goRestPojo.setEmail(email);
        goRestPojo.setGender(gender);
        goRestPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 50edea31afe89011532d4680cedb11a3860444028939e910e2a0013e9b0ce0a1")
                .header("Content-Type", "application/json")
                .pathParam("user_id", userId)
                .body(goRestPojo)
                .when()
                .put(EndPoints.UPDATE_A_USER_BY_ID)
                .then();
    }

    @Step("Deleting user information with id:{0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given().log().all()
                .header("Autorization", "Bearer 50edea31afe89011532d4680cedb11a3860444028939e910e2a0013e9b0ce0a1")
                .pathParam("user_id", userId)
                .when()
                .delete(EndPoints.DELETE_A_USER_BY_ID)
                .then();
    }

    @Step("Getting user information with studentId:{0}")
    public ValidatableResponse getUserById(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 50edea31afe89011532d4680cedb11a3860444028939e910e2a0013e9b0ce0a1")
                .pathParam("user_id", userId)
                .when()
                .get(EndPoints.GET_USER_BY_ID)
                .then();
    }

}