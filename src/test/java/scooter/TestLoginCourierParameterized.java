package scooter;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestLoginCourierParameterized {

    private final CourierCredentials courierCredentials;
    private final int expectedCodeResult;
    private final String expectedMessageError;

    public TestLoginCourierParameterized(CourierCredentials courierCredentials, int expectedCodeResult, String expectedMessageError){
        this.courierCredentials = courierCredentials;
        this.expectedCodeResult = expectedCodeResult;
        this.expectedMessageError = expectedMessageError;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {CourierCredentials.from(Courier.getCourierFullCredential()),
                        404, "Учетная запись не найдена"},
                {CourierCredentials.getCourierCredentialsInvalidLogin(Courier.getCourierFullCredential()),
                        404, "Учетная запись не найдена"},
                {CourierCredentials.getCourierCredentialsInvalidPassword(Courier.getCourierFullCredential()),
                        404, "Учетная запись не найдена"},
                {CourierCredentials.from(Courier.getCourierLogin()),
                        400, "Недостаточно данных для входа"},
                {CourierCredentials.from(Courier.getCourierPassword()),
                        400, "Недостаточно данных для входа"}
        };
    }

    @Test
    @DisplayName("Проверка авторизации")
    public void courierLoginWithInvalidData() {
        ValidatableResponse response = new CourierData().login(courierCredentials);
        int actualCodeResult = response.extract().statusCode();
        assertEquals(actualCodeResult, expectedCodeResult);
        String actualMessageError = response.extract().path("message");
        assertEquals(actualMessageError, expectedMessageError);
    }
}
