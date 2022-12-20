package apiTest;

import org.junit.Assert;
import org.junit.Test;
import pojo.get.ColorsData;
import pojo.get.UserData;
import pojo.get.UserTimeData;
import pojo.get.UserTimeResponse;
import pojo.post.Register;
import pojo.post.SuccessReg;
import pojo.post.UnsuccessfulReg;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {

    private static final String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        List<UserData> users = given()
                .when()
                .get( "api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        //verify if allMatch avatar data contains userId
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        //verify if allMatch email data endsWith -> "@reqres.in"
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

//        List<String> emails = users.stream().map(UserData::getEmail).collect(Collectors.toList());
//        for(String email : emails){
//
//            Assert.assertTrue(email.endsWith("@reqres.in"));
//        }
    }

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

    @Test
    public void sortedYearsTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);
    }

    @Test
    public void deleteUserTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecUnique(204));

        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }

    @Test
    public void updatedTimeTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        UserTimeData user = new UserTimeData("morpheus", "zion resident");
        UserTimeResponse userResponse = given()
                .when()
                .put("/api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String regex = "(..:.*$)";

        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        System.out.println(currentTime);
        Assert.assertEquals(currentTime, userResponse.getUpdatedAt().replaceAll(regex, ""));
        System.out.println(userResponse.getUpdatedAt());
    }
}
