import dto.CreateItemRqDto;
import dto.CreateItemRsDto;
import dto.DeleteItemRqDto;
import helpers.BaseResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.sbtqa.tag.datajack.Stash;
import stepDefs.StepDefinitions;
import com.google.gson.Gson;

public class CreateItemTest {

    private static final String CREATE_ITEM_RESPONSE = "CreateItemTestCreateResponse";

    @Link(name = "Сайт с описанием магазина", url = "https://testbase.atlassian.net/wiki/spaces/SHOP/overview")

    @Test(description = "Тест - Создание нового товара")
    @Description("Проверка создания товара в онлайн магазине")
    public void createItems() throws Exception {
        Allure.step("формируем тело запроса");
        CreateItemRqDto request = new CreateItemRqDto();

        request.setName("Апельсиновое платье");
        request.setSection("Платья");
        request.setDescription("коллекция Весна-Лето 2021");
        request.setColor("ORANGE");
        request.setSize("46");
        request.setPrice(2250);


        StepDefinitions stepDefinitions = new StepDefinitions();

        Allure.step("получаем ответ на отправленный запрос");
        BaseResponse<CreateItemRsDto> response = stepDefinitions.sendCreateItemRequest(request);
        //сохраняем ответ
        Stash.put(CREATE_ITEM_RESPONSE, response);

        Allure.step("осуществляем множественную проверку параметров в запросе с ответом");
        stepDefinitions.checkCreatedItem(request, response);

        Allure.attachment("запрос на создание", new Gson().toJson(request));
        Allure.attachment("ответ на создание", new Gson().toJson(response));
    }

    @AfterMethod (description = "Удаление созданного для теста товара по ID")
    private void deleteItem() throws Exception {

        StepDefinitions stepDefinitions = new StepDefinitions();
        BaseResponse<CreateItemRsDto> createResponse = Stash.getValue(CREATE_ITEM_RESPONSE);

        DeleteItemRqDto deleteRequest = new DeleteItemRqDto();
        deleteRequest.setId(createResponse.getResults().getId());

        BaseResponse<String> deleteResponse = stepDefinitions.sendDeleteItemRequest(deleteRequest);

        Allure.attachment("запрос на удаление", new Gson().toJson(deleteRequest));
        Allure.attachment("ответ на удаление", new Gson().toJson(deleteResponse));

        //проверку удаления в данном тесте не производим
        //stepDefinitions.checkDeleteItem(deleteRequest, deleteResponse);
    }

}
