package scooter;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestCreateCourierParameterized {

    private final Courier courier;
    private final int expectedCodeResult;
    private final String expectedMessageResult;

    public TestCreateCourierParameterized(Courier courier, int expectedCodeResult, String expectedMessageResult){
        this.courier = courier;
        this.expectedCodeResult = expectedCodeResult;
        this.expectedMessageResult = expectedMessageResult;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {Courier.getCourierFullCredential(),
                        400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierFirstname(),
                        400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierLogin(),
                        400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierPassword(),
                        400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Test
    @DisplayName("Курьер не будет создан с не валидными кредами")
    public void createCourierWithInvalidData() {
        ValidatableResponse response = new CourierData().create(courier);
        int actualCodeResult = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        assertEquals("Метод вернул некорректное значение", actualCodeResult, expectedCodeResult);
        assertEquals("Метод вернул некорректное значение", actualMessage, expectedMessageResult);
    }
}