package apiTestWithoutPojo;

import apiTest.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReqresRegistrationNoPojoTest {

    private static final String URL = "https://reqres.in/";

    @Test
    public void successRegNoPojoTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");

        Response response = given()
                .when()
                .body(user)
                .post("api/register")
                .then().log().all()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        int id = jsonPath.get("id");
        String token = jsonPath.get("token");

        Assert.assertEquals(4, id);
        Assert.assertEquals("QpwL5tke4Pnpja7X4", token);
    }

    @Test
    public void unSuccessRegNoPojoTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecError400());

        Map<String, String> user = new HashMap<>();
        user.put("email", "sydney@fife");

        Response response = given()
                .when()
                .body(user)
                .post("api/register")
                .then().log().all()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String error = jsonPath.get("error");

        Assert.assertEquals("Missing password", error);
    }
}
