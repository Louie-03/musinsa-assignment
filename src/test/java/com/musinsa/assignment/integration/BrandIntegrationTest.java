package com.musinsa.assignment.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.CONNECTION;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.HttpHeaders.HOST;
import static org.springframework.http.HttpHeaders.TRANSFER_ENCODING;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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

@DisplayName("Brand 통합 테스트")
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BrandIntegrationTest {

    @LocalServerPort
    int port;

    RequestSpecification documentationSpec;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        documentationSpec = new RequestSpecBuilder()
            .addFilter(
                documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(
                        prettyPrint(),
                        removeHeaders(HOST, CONTENT_LENGTH))
                    .withResponseDefaults(
                        prettyPrint(),
                        removeHeaders(CONTENT_LENGTH, CONNECTION, DATE, TRANSFER_ENCODING))
            )
            .build();
    }

    @Nested
    @DisplayName("특정 브랜드의 모든 카테고리에 대한 최저가 상품의 총합과 브랜드 조회 API는")
    class get_brand_detail_product_min_price {

        @Nested
        @DisplayName("brandId가 유효하다면")
        class brand_id_is_valid {

            @Test
            @DisplayName("API 응답에 성공한다.")
            void success_api_response() {
                given(documentationSpec)
                    .accept(APPLICATION_JSON_VALUE)
                    .filter(document("get-brand-min-prices-success"))

                    .when()
                    .get("/brands/4/min-prices")

                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", equalTo("D"))
                    .body("totalPrice", equalTo(36100));
            }
        }

        @Nested
        @DisplayName("brandId가 유효하지 않다면")
        class brand_id_is_not_valid {

            @Test
            @DisplayName("API 응답에 실패한다.")
            void fail_api_response() {
                given(documentationSpec)
                    .accept(APPLICATION_JSON_VALUE)
                    .filter(document("get-brand-min-prices-fail"))

                    .when()
                    .get("/brands/-10/min-prices")

                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("errorCode", equalTo("PRODUCT001"))
                    .body("message", equalTo("상품을 찾을 수 없습니다."));
            }

        }
    }
}
