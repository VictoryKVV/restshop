import com.google.gson.Gson;
import dto.CreateItemRqDto;
import dto.CreateItemRsDto;
import dto.DeleteItemRqDto;
import dto.UpdateItemRqDto;
import helpers.BaseResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.annotations.*;
import ru.sbtqa.tag.datajack.Stash;
import stepDefs.StepDefinitions;

public class UpdateItemTest {

    private static final String CRATE_ITEM_RESPONSE = "UpdateItemTestCreateItemResponse";

    @BeforeMethod(description = "Создаем товар для осуществления проверки возможности изменения")
    public void createItems() throws Exception {

        Allure.step("формируем запрос для создания товара");
        CreateItemRqDto request = new CreateItemRqDto();

        request.setName("Апельсиновое платье");
        request.setSection("Платья");
        request.setDescription("коллекция Весна-Лето 2021");
        request.setColor("ORANGE");
        request.setSize("46");
        request.setPrice(2250);

        Allure.step("получаем ответ на отправленный запрос");
        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> response = stepDefinitions.sendCreateItemRequest(request);

        Stash.put(CRATE_ITEM_RESPONSE, response);
        // проверку созданного товара не производим
        //stepDefinitions.checkCreatedItem(request, response);

        Allure.attachment("запрос на создание", new Gson().toJson(request));
        Allure.attachment("ответ на создание", new Gson().toJson(response));

    }

    @Test(description = "Тест - изменение ранее созданного товара")
    @Description("Проверка изменения параметров товара в онлайн магазине")
    public void updateItem() throws Exception {
        Allure.step("формируем тело запроса");
        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> createResponse = Stash.getValue(CRATE_ITEM_RESPONSE);

        UpdateItemRqDto updateRequest = new UpdateItemRqDto();
        updateRequest.setId(createResponse.getResults().getId());
        updateRequest.setName("Снежное платье");
        updateRequest.setSection("Платья");
        updateRequest.setDescription("коллекция Зима 2022");
        updateRequest.setColor("BLUE");
        updateRequest.setSize("44");
        updateRequest.setPrice(2000);

        Allure.step("получаем ответ на отправленный запрос");
        BaseResponse<String> updateResponse = stepDefinitions.sendUpdateItemRequest(updateRequest);
        Allure.step("осуществляем множественную проверку параметров в запросе с ответом");
        stepDefinitions.checkUpdateItem(updateResponse);

        Allure.attachment("запрос на изменение", new Gson().toJson(updateRequest));
        Allure.attachment("ответ на изменение", new Gson().toJson(updateResponse));

    }

    @AfterMethod(description = "Удаление ранее созданного товара")
    public void deleteItem() throws Exception {
        Allure.step("получаем параметры ранее созданного товара для уаления");
        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> createResponse = Stash.getValue(CRATE_ITEM_RESPONSE);

        DeleteItemRqDto deleteRequest = new DeleteItemRqDto();
        deleteRequest.setId(createResponse.getResults().getId());

        Allure.step("отправляем запрос на удаление");
        BaseResponse<String> deleteResponse = stepDefinitions.sendDeleteItemRequest(deleteRequest);
        //проверку удаления в данном тесте не производим
        //stepDefinitions.checkDeleteItem(deleteRequest, deleteResponse)

        Allure.attachment("запрос на удаление", new Gson().toJson(deleteRequest));
        Allure.attachment("ответ на удаление", new Gson().toJson(deleteResponse));
    }
}
