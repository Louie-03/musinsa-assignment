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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

@DisplayName("Category 통합 테스트")
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Nested
    @DisplayName("모든 카테고리에 대한 특정 브랜드의 상품 중 최저가 조회 API는")
    class get_lowest_product_in_category_and_brand_list {

        @Nested
        @DisplayName("categoryId와 brandId가 유효하다면")
        class category_id_and_brand_id_is_valid {

            @Test
            @DisplayName("API 응답에 성공한다.")
            void success_api_response() {
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
        }

        @Nested
        @DisplayName("categoryId 또는 brandId가 유효하지 않다면")
        class category_id_or_brand_id_is_not_valid {

            @Test
            @DisplayName("API 응답에 실패한다.")
            void fail_api_response() {
                List<CategoryListRequest> body = List.of(new CategoryListRequest(-1L, 3L));

                given(documentationSpec)
                    .body(body)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE)
                    .filter(document("get-category-list-fail",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))

                    .when()
                    .get("/categories")

                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("errorCode", equalTo("PRODUCT001"))
                    .body("message", equalTo("상품을 찾을 수 없습니다."));
            }
        }
    }

    @Nested
    @DisplayName("카테고리 이름으로 검색한 카테고리에 포함된 상품의 최소 최대 가격과 브랜드 조회 API는")
    class get_category_lowest_and_highest_price_find_by_category_name {

        @Nested
        @DisplayName("카테고리 이름이 유효하다면")
        class category_name_is_valid {

            @Test
            @DisplayName("API 응답에 성공한다.")
            void success_api_response() {
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

        @Nested
        @DisplayName("카테고리 이름이 유효하지 않다면")
        class category_name_is_not_valid {

            @Test
            @DisplayName("API 응답에 실패한다.")
            void fail_api_response() {
                given(documentationSpec)
                    .urlEncodingEnabled(false)
                    .accept(APPLICATION_JSON_VALUE)
                    .filter(document("get-category-name-contains-product-lowest-and-highest-price-fail",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))

                    .when()
                    .get("/categories/min-and-max-price?category-name=상의123")

                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("errorCode", equalTo("PRODUCT001"))
                    .body("message", equalTo("상품을 찾을 수 없습니다."));
            }
        }
    }

}
