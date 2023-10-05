package courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
public class CourierLoginTest {

    private Courier courier = new Courier();
    private final CourierClient client = new CourierClient();
    //  private final CourierGenerator generator = new CourierGenerator();
    private String id;

    @Before
    public void createCourier() {
        courier = CourierGenerator.getRandom();
        id = null;
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка успешной авторизации курьера с обязательными полями, код ответа 201, успешный запрос возаращает id")
    public void courierSuccessAuthorization() {
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Account.from(courier));
        id = loginResponse.extract().path("id").toString();
        response.assertThat().statusCode(HttpURLConnection.HTTP_CREATED).body("ok", is(true));
    }
    @Test
    @DisplayName("Проверка авторизации несуществующим пользователем")
    @Description("Проверка успешной авторизации курьера с несуществующим логином, код ответа 404, ответ: 'Учетная запись не найдена'")
    public void courierAuthorizationWithFailLogin() {
        courier.setLogin(RandomStringUtils.randomAlphabetic(7));
        ValidatableResponse response = client.login(Account.from(courier));
        response.statusCode(HttpURLConnection.HTTP_NOT_FOUND).assertThat().body("message", equalTo("Учетная запись найдена"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера без обязательного поля логин")
    @Description("Авторизация без логина, код ответа - 400, ответ: 'Недостаточно данных для входа'")
    public void courierAuthorizationWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = client.login(Account.from(courier));
        response.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка авторизации курьера без обязательного поля пароль")
    @Description("Авторизация без пароля, код ответа - 400, ответ: 'Недостаточно данных для входа'")
    public void courierAuthorizationWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = client.login(Account.from(courier));
        response.statusCode(HttpURLConnection.HTTP_BAD_REQUEST).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier() {
        if (id != null) {
            client.deleteCourier(id);
        }
    }
}
