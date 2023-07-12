import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.todo.okhttp.MyInterceptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OkHttpContractTest {
    public static final String URL = "https://todo-app-sky.herokuapp.com";
    public static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=UTF-8");
    private OkHttpClient client;

    @BeforeEach
    public void setUp() {
        client = new OkHttpClient.Builder().addNetworkInterceptor(new MyInterceptor()).build();
    }

    @Test
    public void getAll() throws IOException {
        Request request = new Request.Builder().get().url(URL).build();
        Response response = client.newCall(request).execute();

        String body = response.body().string();

        assertEquals(200, response.code());
        assertEquals(1, response.headers("Content-Type").size());
        assertEquals("application/json; charset=utf-8", response.header("Content-Type"));
        assertTrue(body.startsWith("["));
        assertTrue(body.endsWith("]"));
    }

    @Test
    @DisplayName("Создание задачи. Проверяем статус-код, заголовок Content-Type и тело ответа содержит json")
    public void shouldReceive201OnPostRequest() throws IOException {
        // Запрос
        String strBody = "{\"title\" : \"test\"}";
        RequestBody jsonBody = RequestBody.create(strBody, APPLICATION_JSON);
        Request request = new Request.Builder().post(jsonBody).url(URL).build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();

        // Проверить поля ответа
        assertEquals(201, response.code());
        assertEquals(1, response.headers("Content-Type").size());
        assertEquals("application/json; charset=utf-8", response.header("Content-Type"));
        assertTrue(body.startsWith("{"));
        assertTrue(body.endsWith("}"));
    }

    @Test
    public void shouldDelete() throws IOException {
        Request request = new Request.Builder().delete().url(URL).build();
        Response response = client.newCall(request).execute();

        assertEquals(204, response.code());
        assertEquals(1, response.headers("Content-Length").size());
        assertEquals("0", response.header("Content-Length"));
    }
}
