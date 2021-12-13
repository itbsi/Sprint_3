package scooter;
//
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class TestLoginCourier {

    private Courier courier;
    private CourierData courierData;
    private int courierId;

    @Before
    public void setUp() {
        courierData = new CourierData();
        courier = Courier.getRandom();
        courierData.create(courier);
    }
    @After
    public void dropData() {
        courierData.delete(courierId);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void courierCanLoginValidCredentials() {
        ValidatableResponse response = courierData.login(CourierCredentials.from(courier));
        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");
        assertEquals(statusCode, 200);
        assertThat("Что-то пошло нет так", courierId, is(not(0)));
    }
}