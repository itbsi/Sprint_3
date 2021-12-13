package scooter;

import org.apache.commons.lang3.RandomStringUtils;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.response.ValidatableResponse;

public class TestCreateCourier {

    private Courier courier;
    private CourierData courierData;
    private int courierId;

    @Before
    public void setUp() {
        courierData = new CourierData();
        courier = Courier.getRandom();
    }
    @After
    public void dropData() {
        courierData.delete(courierId);
    }

    @Test
    @DisplayName("Курьера можно создать. Курьер может войти с валидными кредами")
    public void courierCanBeCreatedWithValidCredentials() {
        ValidatableResponse response = courierData.create(courier);
        courierId = courierData.login(CourierCredentials.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        boolean created = response.extract().path("ok");
        assertEquals(statusCode, 201);
        assertTrue(created);
        assertThat("Что-то пошло не так", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Нельзя создать курьера с одинаковыми кредами")
    public void courierCanNotBeCreatedWithExistingLogin()  {
        courierData.create(courier);
        courierId = courierData.login(CourierCredentials.from(courier)).extract().path("id");
        courier.setPassword(RandomStringUtils.randomAlphabetic(5));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(5));
        ValidatableResponse response = courierData.create(courier);
        int statusCode = response.extract().statusCode();
        String messageError = response.extract().path("message");
        assertEquals(statusCode, 409);
        assertEquals("Метод вернул некорректное значение", messageError, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Нельзя создать два одинаковых курьера")
    public void cannotCreateCourierBecauseCourierAlreadyExist()  {
        courierData.create(courier);
        courierId = courierData.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse response = courierData.create(courier);
        int statusCode = response.extract().statusCode();
        String messageError = response.extract().path("message");
        assertEquals(statusCode, 409);
        assertEquals("Метод вернул некорректное значение", messageError, "Учетная запись не найдена");
    }
}