package apiTest;

import org.junit.Assert;
import org.junit.Test;
import pojo.post.register.Register;
import pojo.post.register.SuccessReg;
import pojo.post.register.UnsuccessfulReg;

import static io.restassured.RestAssured.given;

public class ReqresRegistrationTest {

    private static final String URL = "https://reqres.in/";

    @Test
    public void successRegTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        Integer expectedId = 4;
        String expectedToken = "QpwL5tke4Pnpja7X4";

        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);

        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(expectedId, successReg.getId());
        Assert.assertEquals(expectedToken, successReg.getToken());
    }

    @Test
    public void unSuccessReg(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecError400());

        Register user = new Register("sydney@fife", "");
        UnsuccessfulReg errorReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnsuccessfulReg.class);

        Assert.assertEquals("Missing password", errorReg.getError());
    }
}
