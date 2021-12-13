package scooter;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {
    public final String login;
    public final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getCourierCredentialsInvalidLogin(Courier courier){
        courier.setLogin(RandomStringUtils.randomAlphabetic(5));
        return new CourierCredentials(courier.login, courier.password);
    }
    public static CourierCredentials getCourierCredentialsInvalidPassword(Courier courier){
        courier.setPassword(RandomStringUtils.randomAlphabetic(5));
        return new CourierCredentials(courier.login, courier.password);
    }
}