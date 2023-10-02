package order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {
    private final static String ORDERS_API = "/orders";
    private final static String CANCEL_ORDER_API = "/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return  spec()
                .body(order)
                .when()
                .post(ORDERS_API)
                .then().log().all();
    }

    @Step("Список заказов")
    public ValidatableResponse orderList() {
        return  spec()
                .when()
                .get(ORDERS_API)
                .then();
    }

    @Step("Удаление заказа")
    public ValidatableResponse deleteOrder(Integer track) {
        return  spec()
                .body(track)
                .when()
                .put(ORDERS_API + CANCEL_ORDER_API)
                .then();
    }

}
