package apiTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.post.login.Login;
import pojo.post.login.SuccessLogin;
import pojo.post.login.UnsuccessfulLogin;

import static io.restassured.RestAssured.given;

public class ReqresLoginTest {

    private static final String URL = "https://reqres.in/";

    @Test
    public void successLogin(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        String expectedToken = "QpwL5tke4Pnpja7X4";

        Login user = new Login("eve.holt@reqres.in", "cityslicka");
        SuccessLogin login = given()
                .body(user)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().as(SuccessLogin.class);

        Assert.assertEquals(expectedToken, login.getToken());
    }

    @Test
    public void unSuccessLogin(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecError400());
        Login user = new Login("peter@klaven", "");
        UnsuccessfulLogin login = given()
                .body(user)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().as(UnsuccessfulLogin.class);

        Assert.assertEquals("Missing password", login.getError());
    }
}
