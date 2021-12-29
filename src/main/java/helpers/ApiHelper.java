package helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    private static final String BASE_URL = "http://shop.bugred.ru/api/items/";
    private static final String CREATE_ITEM = "create/";
    private static final String UPDATE_ITEM = "update/";
    private static final String GET_ITEM = "get/";
    private static final String DELETE_ITEM = "delete/";

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }


    @SneakyThrows
    //метод для отправки пост запроса
    private static Response sendPost(String endPoint,  Object body) {
        RequestSpecification requestSpecification = given().body(body);
        Response response = requestSpecification.when().post(endPoint).thenReturn();
        response.prettyPrint();
        return response;
    }

    public static BaseResponse<CreateItemRsDto> createItem(CreateItemRqDto body) {
        Response response = sendPost(CREATE_ITEM, body);
        BaseResponse<CreateItemRsDto> createItemResponse = null;
        try {
            // переводим наш ответ из формата json в джава объект
            createItemResponse = mapper.readValue(response.asString(), new TypeReference<BaseResponse<CreateItemRsDto>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createItemResponse;
    }

    public static BaseResponse<String> updateItem (UpdateItemRqDto body) {
        Response response = sendPost(UPDATE_ITEM, body);
        BaseResponse<String> updateItemResponse = null;
        try {
            updateItemResponse = mapper.readValue(response.asString(), new TypeReference<BaseResponse<String>>(){
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateItemResponse;
    }


    public static BaseResponse<GetItemRsDto> getItem(GetItemRqDto body) {
        Response response = sendPost(GET_ITEM, body);
        BaseResponse<GetItemRsDto> getItemResponse = null;
        try {
            // переводим наш ответ из формата json в джава объект
            getItemResponse = mapper.readValue(response.asString(), new TypeReference<BaseResponse<GetItemRsDto>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getItemResponse;
    }

    public static BaseResponse<String> deleteItem(DeleteItemRqDto body) {
        Response response = sendPost(DELETE_ITEM, body);
        BaseResponse<String> deleteItemsResponse = null;
        try {
            deleteItemsResponse = mapper.readValue(response.asString(), new TypeReference<BaseResponse<String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteItemsResponse;
    }

}

