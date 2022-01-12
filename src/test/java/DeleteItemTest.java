import com.google.gson.Gson;
import dto.CreateItemRqDto;
import dto.CreateItemRsDto;
import dto.DeleteItemRqDto;
import helpers.BaseResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sbtqa.tag.datajack.Stash;
import stepDefs.StepDefinitions;

public class DeleteItemTest {

    private static final String CREATE_ITEM_RESPONSE = "DeleteItemTestCreatedItemResponse";

    @BeforeMethod(description = "Создаем товар для осуществления проверки возможности удаления")
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

        Stash.put(CREATE_ITEM_RESPONSE, response);
        // проверку созданного товара не производим
        //stepDefinitions.checkCreatedItem(request, response);

        Allure.attachment("запрос на создание", new Gson().toJson(request));
        Allure.attachment("ответ на создание", new Gson().toJson(response));
    }

    @Test(description = "Тест - Удаление ранее созданного товара")
    @Description("Проверка удаления товара в онлайн магазине")
    @Link(name = "Сайт с описанием магазина", url = "https://testbase.atlassian.net/wiki/spaces/SHOP/overview")
    public void deleteItem() throws Exception {

        Allure.step("получаем параметры ранее созданного товара для уаления");
        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> createResponse = Stash.getValue(CREATE_ITEM_RESPONSE);

        DeleteItemRqDto deleteRequest = new DeleteItemRqDto();
        deleteRequest.setId(createResponse.getResults().getId());

        Allure.step("отправляем запрос на удаление");
        BaseResponse<String> deleteResponse = stepDefinitions.sendDeleteItemRequest(deleteRequest);
        Allure.step("осуществляем множественную проверку параметров в запросе с ответом");
        stepDefinitions.checkDeleteItem(deleteRequest, deleteResponse);

        Allure.attachment("запрос на удаление", new Gson().toJson(deleteRequest));
        Allure.attachment("ответ на удаление", new Gson().toJson(deleteResponse));
    }
}
