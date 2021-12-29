package stepDefs;

import dto.*;
import helpers.ApiHelper;
import helpers.BaseResponse;
import org.testng.asserts.SoftAssert;
import ru.sbtqa.tag.datajack.Stash;

import java.util.Map;

public class StepDefinitions {

    public BaseResponse<CreateItemRsDto> sendCreateItemRequest(CreateItemRqDto request) throws Exception {
        BaseResponse<CreateItemRsDto> response = null;
        try {
            response = ApiHelper.createItem(request);
        } catch (Exception e) {
            throw new Exception("Problem with REST api createItems()\n" + e);
        }
        return response;
    }

    public BaseResponse<String> sendUpdateItemRequest(UpdateItemRqDto request) throws Exception {
        BaseResponse<String> response = null;
        try {
            response = ApiHelper.updateItem(request);
        } catch (Exception e) {
            throw new Exception("Problem with REST api updateItems()\n" + e);
        }

        return response;
    }


    public BaseResponse<GetItemRsDto> sendGetItemRequest(GetItemRqDto request) throws Exception {
        BaseResponse<GetItemRsDto> response = null;
        try {
            response = ApiHelper.getItem(request);
        } catch (Exception e) {
            throw new Exception("Problem with REST api getItems()\n" + e);
        }
        return response;

    }


    public BaseResponse<String> sendDeleteItemRequest(DeleteItemRqDto request) throws Exception {
        BaseResponse<String> response = null;
        try {
            response = ApiHelper.deleteItem(request);
        } catch (Exception e) {
            throw new Exception("Problem with REST api deleteItems()\n" + e);
        }
        return response;
    }

    public void checkCreatedItem(CreateItemRqDto request, BaseResponse<CreateItemRsDto> response) {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getStatus().equals("ok"));

        softAssert.assertEquals(request.getName(), response.getResults().getName(),
                "Название товара из запроса не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(request.getDescription(), response.getResults().getDescription(),
                "Описание товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(request.getSection(), response.getResults().getSection(),
                "Категория товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(request.getColor(), response.getResults().getColor(),
                "Цвет товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(request.getSize(), response.getResults().getSize(),
                "Размер товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(request.getPrice(), response.getResults().getPrice(),
                "Цена товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertAll();
    }


    public void checkUpdateItem(BaseResponse<String> updateResponse) {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(updateResponse.getStatus().equals("ok"));
        softAssert.assertTrue(updateResponse.getResults().equals("Товар обновлен!"),
                "Товар не обновлен!");
        softAssert.assertAll();
    }


    public void checkGetItem(CreateItemRsDto createdItem, BaseResponse<GetItemRsDto> getResponse) {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getResponse.getStatus().equals("ok"));

        softAssert.assertEquals(createdItem.getName(), getResponse.getResults().getName(),
                "Название товара из запроса не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(createdItem.getDescription(), getResponse.getResults().getDescription(),
                "Описание товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(createdItem.getSection(), getResponse.getResults().getSection(),
                "Категория товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(createdItem.getColor(), getResponse.getResults().getColor(),
                "Цвет товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(createdItem.getSize(), getResponse.getResults().getSize(),
                "Размер товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertEquals(createdItem.getPrice(), getResponse.getResults().getPrice(),
                "Цена товара в запросе не совпадает с тем, что пришло в ответ");
        softAssert.assertAll();
    }


    public void checkDeleteItem(DeleteItemRqDto deleteRequest, BaseResponse<String> response) {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getStatus().equals("ok"));
        softAssert.assertTrue(response.getResults().contains("Товар с ID " + deleteRequest.getId() + " успешно удален"),
                "Товар с ID " + deleteRequest.getId() + " не удален!");
        softAssert.assertAll();
    }

}


