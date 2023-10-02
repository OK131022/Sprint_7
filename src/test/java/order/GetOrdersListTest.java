package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка что в тело ответа возвращается список заказов")
    public void orderListTest() {
        ValidatableResponse response = orderClient.orderList();
        response.statusCode(HttpURLConnection.HTTP_OK).and().body("orders", notNullValue());
    }
}
