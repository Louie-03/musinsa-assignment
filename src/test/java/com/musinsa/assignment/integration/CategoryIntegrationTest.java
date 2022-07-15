package com.musinsa.assignment.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.musinsa.assignment.web.dto.category.CategoryListRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CategoryIntegrationTest {

    @LocalServerPort
    int port;

    RequestSpecification documentationSpec;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        documentationSpec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build();
    }

    @Test
    void 모든_카테고리에_대한_특정_브랜드의_상품_중_최저가를_조회한다() {
        List<CategoryListRequest> body = List.of(
            new CategoryListRequest(1L, 3L),
            new CategoryListRequest(2L, 5L),
            new CategoryListRequest(3L, 4L),
            new CategoryListRequest(4L, 7L),
            new CategoryListRequest(5L, 1L),
            new CategoryListRequest(6L, 4L),
            new CategoryListRequest(7L, 9L),
            new CategoryListRequest(8L, 6L)
        );

        given(documentationSpec)
            .body(body)
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .filter(document("get-category-list",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))

            .when()
            .get("/categories")

            .then()
            .statusCode(HttpStatus.OK.value())
            .body("totalPrice", equalTo(34100))
            .body("details", hasSize(8));
    }

    @Test
    void 카테고리_이름으로_검색한_카테고리에_포함된_상품의_최소_최대_가격과_브랜드를_조회한다() {
        given(documentationSpec)
            .urlEncodingEnabled(false)
            .accept(APPLICATION_JSON_VALUE)
            .filter(document("get-category-name-contains-product-lowest-and-highest-price",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))

            .when()
            .get("/categories/min-and-max-price?category-name=상의")

            .then()
            .statusCode(HttpStatus.OK.value())
            .body("min.brandName", equalTo("C"))
            .body("min.price", equalTo(10000))
            .body("max.brandName", equalTo("I"))
            .body("max.price", equalTo(11400));
    }
}
