package scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierData extends RestAssuredSpec {
        private static final String COURIER_PATH = "api/v1/courier/";

        @Step
        public ValidatableResponse create(Courier courier) {
            return given().spec(getBaseSpec()).body(courier).when().post(COURIER_PATH).then();
        }

        @Step
        public ValidatableResponse login(CourierCredentials credentials) {
            return given().spec(getBaseSpec()).body(credentials).when().post(COURIER_PATH + "login/").then();
        }

        @Step
        public ValidatableResponse delete(int courierId) {
            return given().spec(getBaseSpec()).when().delete(COURIER_PATH + courierId).then().assertThat().statusCode(200);
        }
}

