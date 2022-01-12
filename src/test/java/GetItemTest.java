import com.google.gson.Gson;
import dto.*;
import helpers.BaseResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import org.testng.annotations.*;
import ru.sbtqa.tag.datajack.Stash;
import stepDefs.StepDefinitions;

public class GetItemTest {

    private static final String CREATE_ITEM_RESPONSE = "GetItemTestCreatedItemResponse";

    @BeforeMethod(description = "Создаем товар для осуществления проверки возможности запроса")
    private void createItems1() throws Exception {

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

    @Test(description = "Тест - запрос отображения ранее созданного товара")
    @Description("Проверка запроса товара в онлайн магазине")
    @Link(name = "Сайт с описанием магазина", url = "https://testbase.atlassian.net/wiki/spaces/SHOP/overview")
    public void getItem() throws Exception {
        Allure.step("формируем тело запроса");
        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> createResponse = Stash.getValue(CREATE_ITEM_RESPONSE);

        GetItemRqDto getRequest = new GetItemRqDto();
        getRequest.setId(createResponse.getResults().getId());

        Allure.step("получаем ответ на отправленный запрос");
        BaseResponse<GetItemRsDto> getResponse = stepDefinitions.sendGetItemRequest(getRequest);
        Allure.step("осуществляем множественную проверку параметров в запросе с ответом");
        stepDefinitions.checkGetItem(createResponse.getResults(), getResponse);

        Allure.attachment("запрос на отображение", new Gson().toJson(getRequest));
        Allure.attachment("ответ на отображение", new Gson().toJson(getResponse));

    }

    @AfterMethod(description = "Удаление ранее созданного товара")
    private void deleteItem() throws Exception {
        Allure.step("получаем параметры ранее созданного товара для уаления");
        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> createResponse = Stash.getValue(CREATE_ITEM_RESPONSE);

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
