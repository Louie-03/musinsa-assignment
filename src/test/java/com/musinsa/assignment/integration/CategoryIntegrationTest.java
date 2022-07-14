package com.musinsa.assignment.integration;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
    void 모든_카테고리에_대한_특정_브랜드의_상품_중_최저가_조회_기능_구현() {
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
            .contentType(JSON)
            .accept(JSON)
            .filter(document("get-category-list",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))

            .when()
            .get("/categories")

            .then()
            .statusCode(HttpStatus.OK.value())
            .body("totalPrice", equalTo(34100))
            .body("details", hasSize(8));
    }
}
