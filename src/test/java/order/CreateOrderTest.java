package order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final OrderClient orderClient = new OrderClient();
    private Integer track;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters(name = "Проверка выбора цвета самоката при заказе. Тестовые данные: {0}, {1}, {2}, {3}, {4}")
    public static Object[][] getParams() {
        return new Object[][]{
                {"Роман", "Викторович", "ул. Кировоградская, 33", "Аннино", "+79267661718", 1, "2023-09-27", "Позвонить за 30 минут", new String[]{"GREY"}},
                {"Илья", "Евгеньевич", "ул. Наметкина, 12", "Южная", "+79267889988", 2, "2023-09-27", "Позвонить за 30 минут", new String[]{"BLACK"}},
                {"Анна", "Дмитриевна", "ул. Пушкина, 105", "Сокол", "+79261112365", 3, "2023-09-27", "Позвонить за 30 минут", new String[]{"BLACK", "GREY"}},
                {"Екатерина", "Ивановна", "бул. Зеленый, 7", "Белорусская", "+79261115443", 4, "2023-09-27", "Позвонить за 30 минут", new String[]{}},
                {"Ивонна", "Романовна", "ул. Футбольная, 99", "Бауманская", "+79268990010", 5, "2023-09-27", "Позвонить за 30 минут", null}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами указания цвета")
    @Description("Проверка успешного создания заказа самокатов двух цветов, одного цвета и без цвета. Ответ содержит  track")
    public void createOrderWithDiffColoureTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = orderClient.createOrder(order);
        response.assertThat().log().all().statusCode(HttpURLConnection.HTTP_CREATED).body("track", is(notNullValue()));
        track = response.extract().path("track");
    }

    @After
    public void deleteOrder() {
        orderClient.deleteOrder(track);
    }
}
