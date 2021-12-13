package scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class TestCreateOrderParameterized {

    private final Order order;
    private final int expectedCodeResult;

    public TestCreateOrderParameterized(Order order, int expectedCodeResult){
        this.order = order;
        this.expectedCodeResult = expectedCodeResult;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {Order.getOrderNoColor  (), 201},
                {Order.getOrderColor    (new String[]{"BLACK"}), 201},
                {Order.getOrderColor    (new String[]{"GREY"}), 201},
                {Order.getOrderColor    (new String[]{"BLACK","GREY"}), 201}
        };
    }

    @After
    public void dropData() {
        new OrderData().cancel(order);
    }

    @Test
    @DisplayName("Заказ может быть создан с корректными данными")
    public void createOrderWithValidData() {
        ValidatableResponse response = new OrderData().create(order);
        int actualCodeResult = response.extract().statusCode();
        int track = response.extract().path("track");
        assertEquals(actualCodeResult, expectedCodeResult);
        assertThat("Track пустой", track, is(not(0)));
    }
}