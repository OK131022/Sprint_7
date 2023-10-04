package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest { private Courier courier = new Courier();
    private final CourierClient client = new CourierClient();
    //private final CourierGenerator generator = new CourierGenerator();
    private String id;

    @Before
    public void createCourier() {
        courier = CourierGenerator.getRandom();
        id = null;
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверка успешного создания курьера, код ответа - 201, запрос возвращает ok:true")
    public void courierCreateSuccess() {
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Account.from(courier));
        id = loginResponse.extract().path("id").toString();
        response.assertThat().statusCode(HttpURLConnection.HTTP_CREATED).body("ok", is(true));
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Description("При запросе с повторяющимся логином, код ответа - 409, ответ: 'Этот логин уже используется. Попробуйте другой'")
    public void courierCreateWithExistLogin() {
        client.createCourier(courier);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(HttpURLConnection.HTTP_CONFLICT).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля логин")
    @Description("При запросе без логина курьера, код ответа - 400, ответ: 'Недостаточно данных для создания учетной записи'")
    public void courierCreateWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(HttpURLConnection.HTTP_BAD_REQUEST).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля пароль")
    @Description("При запросе без пароля курьера, код ответа - 400, ответ: 'Недостаточно данных для создания учетной записи'")
    public void courierCreateWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(HttpURLConnection.HTTP_BAD_REQUEST).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        if (id != null) {
            client.deleteCourier(id);
        }
    }
}

