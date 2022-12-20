package apiTest;

import org.junit.Assert;
import org.junit.Test;
import pojo.get.ColorsData;
import pojo.get.UserData;
import pojo.get.UserTimeData;
import pojo.get.UserTimeResponse;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresOtherTest {

    private static final String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndEmailTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

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
                .delete("api/users/2")
                .then().log().all();
    }

    @Test
    public void checkUpdateUserTimeTest(){

        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());

        UserTimeData user = new UserTimeData("morpheus", "zion resident");
        UserTimeResponse response = given()
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String regex = "(..:.*)";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");

        Assert.assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex, ""));
    }
}
