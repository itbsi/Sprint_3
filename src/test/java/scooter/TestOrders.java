package scooter;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TestOrders {

    private OrderData orderData;

    @Before
    public void setUp() {
        orderData = new OrderData();
    }

    @Test
    @DisplayName("Получить все заказы")
    public void getListOrders () {
        ValidatableResponse response = orderData.getAllOrders();
        int statusCode = response.extract().statusCode();
        List<Object> orders = response.extract().jsonPath().getList("orders");
        int sizeListOrders = orders.size();
        List<Object> listOfIdOrders = response.extract().jsonPath().getJsonObject("orders.id");
        int sizeListOfIdOrders = listOfIdOrders.size();
        assertEquals(statusCode, 200);
        assertFalse(orders.isEmpty());
        assertEquals(sizeListOfIdOrders, sizeListOrders);
    }
}