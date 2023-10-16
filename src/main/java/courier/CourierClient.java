package courier;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends Client {
    private final static String COURIER_API = "/courier";
    private final static String LOGIN_API = "/login";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_API)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(Account account) {
        return spec()
                .body(account)
                .when()
                .post(COURIER_API + LOGIN_API)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {
        return spec()
                .when()
                .delete(COURIER_API + LOGIN_API + id)
                .then();
    }
}
