import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.todo.model.CompanyDTO;
import ru.inno.todo.model.CreateCompanyDTO;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredContractTest {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://x-clients-be.onrender.com";
        RestAssured.basePath = "/company";
    }

    private static CompanyDTO getCompanyDTO(CreateCompanyDTO newCompany, String token) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newCompany)
                .header("x-client-token", token)
                .post().then()
                .log().all()
                .extract().body().as(CompanyDTO.class);
    }

    private static String getToken(String login, String pass) {
        return given()
                .basePath("/auth/login")
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + login + "\", \"password\": \"" + pass + "\"}")
                .post()
                .then().extract().path("userToken");
    }

    @Test
    public void shouldReceive200OnGetRequest() {
        given()
                .when().get()
                .then()
                .statusCode(200)
                .header("Content-Length", "511");
    }

    @Test
    @DisplayName("Проверяем поля объекта company")
    public void shouldReceive201OnPostRequest() throws IOException {
        given().get("/47")
                .then()
                .statusCode(200)
                .body("id", equalTo(47))
                .body("isActive", equalTo(true))
                .body("name", equalTo("Inno"))
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
                .log().all();
    }

    @Test
    @DisplayName("Логируем запрос и ответ")
    public void shouldR2eceive201OnPostRequest() throws IOException {
        given()
                .log().all()
                .get("/47")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
        ;
    }

    @Test
    public void createCompany() {
        String token = getToken("roxy", "animal-fairy");

        int newId = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"roxy\", \"description\": \"animal-fairy\"}")
                .header("x-client-token", token)
                .post().then().statusCode(201)
                .body("id", is(greaterThan(47))).extract().path("id");

        System.out.println("New id " + newId);
    }

    @Test
    public void noMapper() {
        given()
                .log().all()
                .get("/47")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
                .extract().body().jsonPath().getInt("id");

        CreateCompanyDTO newCompany = new CreateCompanyDTO("test1", "descr1");

        String token = getToken("roxy", "animal-fairy");

        CompanyDTO companyDTO = getCompanyDTO(newCompany, token);
        assertEquals(companyDTO.getId(), 1);

        System.out.println(companyDTO);
    }

    @Test
    public void activeFilter() {
        //https://x-clients-be.onrender.com/company?active=true'

        String city = "Moscow";
        String s = given()
                .log().all()
                .param("active", true)
                .pathParam("city", city)
                .when().get("/{city}")
                .then().extract().asPrettyString();
        System.out.println(s);

    }


}
