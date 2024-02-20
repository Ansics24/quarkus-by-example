package de.schulte.smartbar.backoffice.categories;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
class CategoriesResourceTest {

    @InjectMock
    CategoriesService categoriesServiceMock;

    @BeforeEach
    void setUp() {
        final Category category = new Category();
        category.setName("Mock");
        Mockito.when(categoriesServiceMock.listAll()).thenReturn(List.of(category));
    }

    @Test
    void getsListOfCategories() {
        final Response response = given()
                .when().get("/categories")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals("Mock", jsonPath.getString("[0].name"));
    }

}
